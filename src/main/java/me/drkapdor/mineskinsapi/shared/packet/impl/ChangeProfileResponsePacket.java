package me.drkapdor.mineskinsapi.shared.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import me.drkapdor.mineskinsapi.shared.packet.IMessagingPacket;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;

import java.io.IOException;
import java.util.Optional;

@Getter
public class ChangeProfileResponsePacket implements IMessagingPacket {

    private final PacketType type;
    private final String player;
    private final GeneratedSkin skin;
    private final boolean success;

    public ChangeProfileResponsePacket(PacketType type, String player, GeneratedSkin skin, boolean success) {
        this.type = type;
        this.player = player;
        this.skin = skin;
        this.success = success;
    }

    public ChangeProfileResponsePacket(byte[] byteArray) {
        ByteArrayDataInput input = ByteStreams.newDataInput(byteArray);
        type = PacketType.valueOf(input.readUTF());
        player = input.readUTF();
        skin = new GeneratedSkin(input.readUTF(), input.readUTF());
        success = input.readBoolean();
    }

    @Override
    public PacketType getType() {
        return type;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(PacketType.RESPONSE.toString());
        output.writeUTF(player);
        output.writeUTF(skin.getValue());
        output.writeUTF(skin.getSignature());
        output.writeBoolean(success);
        return output.toByteArray();
    }
}
