package me.drkapdor.mineskinsapi.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.util.GameProfile;
import lombok.AllArgsConstructor;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;
import me.drkapdor.mineskinsapi.shared.packet.impl.ChangeProfileRequestPacket;
import me.drkapdor.mineskinsapi.shared.packet.impl.ChangeProfileResponsePacket;
import me.drkapdor.mineskinsapi.velocity.MineSkinVelocityPlugin;

import java.util.Collections;

@AllArgsConstructor
public class PluginMessageListener {

    private static final ChannelIdentifier REQUEST_IDENTIFIER = () -> "msa:request";
    private static final ChannelIdentifier RESPONSE_IDENTIFIER = () -> "msa:response";

    private final MineSkinVelocityPlugin plugin;

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getIdentifier().getId().equals(REQUEST_IDENTIFIER.getId())) {
            String requesterServer = event.getSource().toString().split("> ")[1];
            ChangeProfileRequestPacket requestPacket = new ChangeProfileRequestPacket(event.getData());
            plugin.getServer().getPlayer(requestPacket.getPlayer()).ifPresentOrElse(player -> {
                GameProfile.Property skinProperty = new GameProfile.Property("textures", requestPacket.getValue(), requestPacket.getSignature());
                player.setGameProfileProperties(Collections.emptyList());
                player.setGameProfileProperties(Collections.singletonList(skinProperty));
                plugin.getServer().getServer(requesterServer).ifPresent(server -> {
                    ChangeProfileResponsePacket responsePacket = new ChangeProfileResponsePacket(
                            PacketType.RESPONSE,
                            requestPacket.getPlayer(),
                            new GeneratedSkin(requestPacket.getValue(), requestPacket.getSignature()),
                            true);
                    server.sendPluginMessage(RESPONSE_IDENTIFIER, responsePacket.toByteArray());
                });
            }, () -> {
                plugin.getServer().getServer(requesterServer).ifPresent(server -> {
                    ChangeProfileResponsePacket responsePacket = new ChangeProfileResponsePacket(
                            PacketType.RESPONSE,
                            requestPacket.getPlayer(),
                            new GeneratedSkin(requestPacket.getValue(), requestPacket.getSignature()),
                            false
                    );
                    server.sendPluginMessage(RESPONSE_IDENTIFIER, responsePacket.toByteArray());
                });
            });
        }
    }
}
