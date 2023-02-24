package me.drkapdor.mineskinsapi.paper;

import lombok.Getter;
import me.drkapdor.mineskinsapi.IMineSkinPlugin;
import me.drkapdor.mineskinsapi.api.MineSkinApi;
import me.drkapdor.mineskinsapi.api.MineSkinClient;
import me.drkapdor.mineskinsapi.api.skin.SkinRegistry;
import me.drkapdor.mineskinsapi.paper.listener.PluginMessagesListener;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;
import me.drkapdor.mineskinsapi.shared.packet.impl.ChangeProfileRequestPacket;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MineSkinPaperPlugin extends JavaPlugin implements IMineSkinPlugin {

    @Getter
    private static MineSkinPaperPlugin instance;
    private MineSkinConfig mineSkinConfig;
    private MineSkinApi mineSkinApi;

    @Override
    public void onEnable() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws IOException {
        instance = this;
        File configFile = new File(getDataFolder() + File.separator + "configuration.yml");
        if (configFile.exists()) {
            mineSkinConfig = new MineSkinConfig(YamlConfiguration.loadConfiguration(configFile));
        } else {
            if (!getDataFolder().exists()) {
                if (getDataFolder().mkdir()) {
                    if (configFile.createNewFile()) {
                        YamlConfiguration configuration = new YamlConfiguration();
                        configuration.set("apiKey", "<insert_key_here>");
                        mineSkinConfig = new MineSkinConfig(configuration);
                        configuration.save(configFile);
                    }
                }
            }
        }
        mineSkinApi = new MineSkinApi(new MineSkinClient(mineSkinConfig), new SkinRegistry());
        Bukkit.getServicesManager().register(MineSkinApi.class, mineSkinApi, this, ServicePriority.Normal);

        Messenger messenger = Bukkit.getMessenger();
        messenger.registerIncomingPluginChannel(this, "msa:response", new PluginMessagesListener());
        messenger.registerOutgoingPluginChannel(this, "msa:request");
    }
}
