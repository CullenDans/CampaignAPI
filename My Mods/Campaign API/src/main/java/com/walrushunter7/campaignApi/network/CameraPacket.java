package com.walrushunter7.campaignApi.network;

import com.walrushunter7.campaignApi.camera.CameraHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class CameraPacket implements IMessage, IMessageHandler<CameraPacket,IMessage> {

    private byte packetId;
    private int cameraId;
    private int entityId;

    public CameraPacket() {

    }

    public CameraPacket(byte packetId, int cameraId, int entityId) {
        this.packetId = packetId;
        this.cameraId = cameraId;
        this.entityId = entityId;
    }

    /* IDs
     *
     * 1 = start
     * 2 = stop
     * 3 = new camera
     * 4 = set camera
     * 5 = delete camera
     */


    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(packetId);
        buf.writeInt(cameraId);
        buf.writeInt(entityId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.packetId = buf.readByte();
        this.cameraId = buf.readInt();
        this.entityId = buf.readInt();
    }

    @Override
    public IMessage onMessage(CameraPacket message, MessageContext ctx) {

        Minecraft mc = Minecraft.getMinecraft();

        switch (message.packetId) {
            case 1:

                break;
            case 2:
                CameraHandler.normalPlayerCameraClient();
                break;
            case 3:
                //CameraHandler.newCamera(message.cameraId);
                break;
            case 4:
                CameraHandler.setPlayerCameraClient(message.entityId);
                //CameraHandler.setPlayerCamera(message.cameraId);
                break;
            case 5:
                //CameraHandler.removeCamera(message.cameraId);
                break;
        }


        return null;
    }

}
