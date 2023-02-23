package me.drkapdor.mineskinsapi.paper;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

@Getter
public class MineSkinConfig {

    private final String apiKey;

    public MineSkinConfig(YamlConfiguration configuration) {
        apiKey = configuration.getString("apiKey");
    }
}
