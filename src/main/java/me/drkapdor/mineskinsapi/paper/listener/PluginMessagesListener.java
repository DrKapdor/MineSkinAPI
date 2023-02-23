package me.drkapdor.mineskinsapi.paper.listener;

import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import me.drkapdor.mineskinsapi.paper.util.SkinApplier;
import me.drkapdor.mineskinsapi.shared.packet.impl.ChangeProfileResponsePacket;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
public class PluginMessagesListener implements PluginMessageListener {

    private static final String RESPONSE_IDENTIFIER = "msa:response";

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player receiver, byte @NotNull [] byteArray) {
        if (channel.equalsIgnoreCase(RESPONSE_IDENTIFIER)) {
            ChangeProfileResponsePacket responsePacket = new ChangeProfileResponsePacket(byteArray);
            if (responsePacket.isSuccess()) {
                Player target = Bukkit.getPlayerExact(responsePacket.getPlayer());
                if (target != null && target.isOnline()) {
                    GeneratedSkin skin = responsePacket.getSkin();
                    if (!skin.isEmpty()) {
                        SkinApplier applier = new SkinApplier(target, responsePacket.getSkin());
                        applier.apply();
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Скин успешно установлен!"));
                    } else target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Возникла ошибка при загрузке скина!"));
                }
            }
        }
    }
}
