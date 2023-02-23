package me.drkapdor.mineskinsapi.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import lombok.Getter;
import me.drkapdor.mineskinsapi.IMineSkinPlugin;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;
import me.drkapdor.mineskinsapi.velocity.listener.PluginMessageListener;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Plugin(
        id = "mineskinapi",
        name = "MineSkinAPI",
        version = "1.0.0-SNAPSHOT",
        url = "https://dev.funbaze.ru",
        description = "Кроссерверная система скинов",
        authors = {
                "DrKapdor"
        }
)
public class MineSkinVelocityPlugin implements IMineSkinPlugin {

    @Getter
    private static MineSkinVelocityPlugin instance;
    @Getter
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public MineSkinVelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event)  {
        init();
    }

    @Override
    public void init() {
        instance = this;
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("msa", "request"));
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("msa", "response"));
        server.getEventManager().register(this, new PluginMessageListener(this));
    }
}
