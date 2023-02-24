package me.drkapdor.mineskinsapi.shared.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import me.drkapdor.mineskinsapi.shared.packet.IMessagingPacket;
import me.drkapdor.mineskinsapi.shared.packet.PacketType;

import java.io.*;
import java.util.Optional;

@Getter
public class ChangeProfileRequestPacket implements IMessagingPacket {

    private final PacketType type;
    private final String player;
    private final String value;
    private final String signature;

    public ChangeProfileRequestPacket(PacketType type, String player, String value, String signature) {
        this.type = type;
        this.player = player;
        this.value = value;
        this.signature = signature;
    }

    public ChangeProfileRequestPacket(byte[] byteArray) {
        ByteArrayDataInput input = ByteStreams.newDataInput(byteArray);
        type = PacketType.valueOf(input.readUTF());
        player = input.readUTF();
        value = input.readUTF();
        signature = input.readUTF();
    }

    @Override
    public PacketType getType() {
        return type;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(type.toString());
        output.writeUTF(player);
        output.writeUTF(value);
        output.writeUTF(signature);
        return output.toByteArray();
    }
}
