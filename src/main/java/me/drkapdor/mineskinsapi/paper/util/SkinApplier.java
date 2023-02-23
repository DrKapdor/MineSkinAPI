package me.drkapdor.mineskinsapi.paper.util;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import lombok.AllArgsConstructor;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import me.drkapdor.mineskinsapi.paper.MineSkinPaperPlugin;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashSet;

@AllArgsConstructor
public class SkinApplier {

    private final Player player;
    private final GeneratedSkin skin;

    public void apply() {
        Location location = player.getLocation();
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PropertyMap propertyMap = entityPlayer.getProfile().getProperties();
        propertyMap.removeAll("textures");
        propertyMap.put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
        World world = entityPlayer.getWorld();
        PlayerConnection playerConnection = entityPlayer.playerConnection;
        DataWatcher dataWatcher = entityPlayer.getDataWatcher();
        dataWatcher.set(DataWatcherRegistry.a.a(16), (byte) 126);
        //---------------------------------------------------------------------------------------------------------------------
        PacketPlayOutPlayerInfo removeInfoPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
        PacketPlayOutPlayerInfo addInfoPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(entityPlayer.getId(), dataWatcher, false);
        PacketPlayOutRespawn respawnEntityPacket = new PacketPlayOutRespawn(world.getDimensionManager(), world.getDimensionKey(), player.getWorld().getSeed(), entityPlayer.playerInteractManager.getGameMode(), entityPlayer.playerInteractManager.getGameMode(), false, false, false);
        PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), new HashSet<>(), 0);
        PacketPlayOutHeldItemSlot heldItemPacket = new PacketPlayOutHeldItemSlot(player.getInventory().getHeldItemSlot());
        PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus(entityPlayer, (byte) 28);
        //---------------------------------------------------------------------------------------------------------------------

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(MineSkinPaperPlugin.getInstance(), player);
            onlinePlayer.showPlayer(MineSkinPaperPlugin.getInstance(), player);
        }
        //---------------------------------------------------------------------------------------------------------------------
        playerConnection.sendPacket(removeInfoPacket);
        playerConnection.sendPacket(addInfoPacket);
        playerConnection.sendPacket(metadataPacket);
        playerConnection.sendPacket(respawnEntityPacket);
        playerConnection.sendPacket(positionPacket);
        playerConnection.sendPacket(statusPacket);
        playerConnection.sendPacket(heldItemPacket);
        //---------------------------------------------------------------------------------------------------------------------
        entityPlayer.updateInventory(entityPlayer.activeContainer);
        craftPlayer.updateScaledHealth();
        entityPlayer.triggerHealthUpdate();
    }
}
