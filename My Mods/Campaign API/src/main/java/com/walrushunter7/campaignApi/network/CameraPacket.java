package com.walrushunter7.campaignApi.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class CameraPacket implements IMessage, IMessageHandler<CameraPacket,IMessage> {

    public CameraPacket() {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(CameraPacket message, MessageContext ctx) {
        return null;
    }

}
