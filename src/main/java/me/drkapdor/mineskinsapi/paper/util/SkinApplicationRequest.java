package me.drkapdor.mineskinsapi.paper.util;

import lombok.Builder;
import me.drkapdor.mineskinsapi.paper.MineSkinPaperPlugin;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;
import me.drkapdor.mineskinsapi.shared.packet.impl.ChangeProfileRequestPacket;
import org.bukkit.Bukkit;

@Builder
public class SkinApplicationRequest {

    private final String requester;
    private final String value;
    private final String signature;

    public void send() {
        ChangeProfileRequestPacket requestPacket = new ChangeProfileRequestPacket(PacketType.REQUEST, requester, value, signature);
        Bukkit.getServer().sendPluginMessage(MineSkinPaperPlugin.getInstance(), "msa:request", requestPacket.toByteArray());
    }
}
