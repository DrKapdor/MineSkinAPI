package me.drkapdor.mineskinsapi.paper.util;

import lombok.Builder;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;
import me.drkapdor.mineskinsapi.shared.packet.impl.ChangeProfileRequestPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Builder
public class SkinApplicationRequest {

    private final Player player;
    private final String value;
    private final String signature;

    public void send(Plugin plugin) {
        ChangeProfileRequestPacket requestPacket = new ChangeProfileRequestPacket(PacketType.REQUEST, player.getName(), value, signature);
        Bukkit.getServer().sendPluginMessage(plugin, "msa:request", requestPacket.toByteArray());
    }

}
