package me.drkapdor.mineskinsapi.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import me.drkapdor.mineskinsapi.paper.MineSkinConfig;
import me.drkapdor.mineskinsapi.paper.MineSkinPaperPlugin;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MineSkinClient {

    private static final String GENERATE_ENDPOINT = "https://api.mineskin.org/generate/";
    private final MineSkinConfig config;
    private final JsonParser jsonParser;
    private final Executor executorService;

    public MineSkinClient(MineSkinConfig config) {
        this.config = config;
        jsonParser = new JsonParser();
        executorService = Executors.newFixedThreadPool(8);
    }

    public CompletableFuture<GeneratedSkin> uploadSkin(String url) {
        return CompletableFuture.supplyAsync(() -> {
            HttpPost uploadRequest = new HttpPost(GENERATE_ENDPOINT + "url");
            uploadRequest.setHeader("User-Agent", "Mozilla/5.0");
            uploadRequest.setHeader("Authorization", "Bearer " + config.getApiKey());
            uploadRequest.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            uploadRequest.setHeader("Pragma", "no-cache");
            String value = "";
            String signature = "";
            try {
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addTextBody("name", "skin")
                        .addTextBody("visibility", "1")
                        .addTextBody("url", url).build();
                uploadRequest.setEntity(entity);
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectionRequestTimeout(3000)
                        .setConnectTimeout(3000)
                        .setSocketTimeout(3000)
                        .build();
                uploadRequest.setConfig(requestConfig);
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(uploadRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = reader.readLine();
                JsonObject object = jsonParser.parse(line).getAsJsonObject();
                if (response.getStatusLine().getStatusCode() == 200) {
                    JsonObject data = object.getAsJsonObject("data");
                    JsonObject texture = data.getAsJsonObject("texture");
                    value = texture.get("value").getAsString();
                    signature = texture.get("signature").getAsString();
                } else {
                    MineSkinPaperPlugin.getInstance().getLogger().warning(
                            "Не удалось подгрузить скин: " +
                                    response.getStatusLine().getStatusCode() + " (" +
                                    response.getStatusLine().getReasonPhrase() + ")");
                }
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GeneratedSkin(value, signature);
        }, executorService);
    }
}
