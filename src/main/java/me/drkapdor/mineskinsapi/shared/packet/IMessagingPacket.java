package me.drkapdor.mineskinsapi.shared.packet;

import java.io.IOException;

public interface IMessagingPacket {

    PacketType getType();
    byte[] toByteArray() throws IOException;

}
