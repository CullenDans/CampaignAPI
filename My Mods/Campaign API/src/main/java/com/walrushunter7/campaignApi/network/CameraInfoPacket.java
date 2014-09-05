package com.walrushunter7.campaignApi.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class CameraInfoPacket implements IMessage, IMessageHandler<CameraInfoPacket,IMessage> {

    private byte packetId;
    private int cameraId;

    public CameraInfoPacket() {

    }

    public CameraInfoPacket(byte packetId) {
        this.packetId = packetId;
    }

    /* IDs
     *
     * 1 = start
     * 2 = stop
     * 3 = new camera
     */


    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(packetId);
        switch (packetId) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.packetId = buf.readByte();
        switch (packetId) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    @Override
    public IMessage onMessage(CameraInfoPacket message, MessageContext ctx) {


        switch (message.packetId) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }

        return null;
    }

}

