package com.walrushunter7.campaignApi.command;

import com.walrushunter7.campaignApi.camera.CameraHandler;
import com.walrushunter7.campaignApi.camera.CameraWorldHandler;
import com.walrushunter7.campaignApi.entity.EntityCamera;
import com.walrushunter7.campaignApi.util.Log;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class CameraCommand extends CommandBase{

    public String getCommandName() {
        return "camera";
    }

    public String getCommandUsage(ICommandSender icommandsender) {
        return "/camera <something>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring) {

        if (astring.length > 0)
        {
            EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(icommandsender);
            CameraHandler cameraHandler = CameraWorldHandler.gerWorldCameraHandler(entityplayermp.worldObj);

            if (astring[0].equals("start"))
            {
                Log.info(cameraHandler.getCamera(1).getPosition(1.0F));
                //CampaignAPI.network.sendTo(new CameraPacket((byte)1, 0), entityplayermp);
            }
            else if (astring[0].equals("stop")) {
                //CampaignAPI.network.sendTo(new CameraPacket((byte)2, 0), entityplayermp);
                cameraHandler.normalPlayerCamera(entityplayermp);
            }
            else if (astring[0].equals("deleteall")) {
                World world = entityplayermp.worldObj;
                for(Object entity: world.getLoadedEntityList()) {
                    if (entity instanceof EntityCamera) {
                        EntityCamera entityCamera = (EntityCamera) entity;
                        Log.info("Deleting entity :" + entityCamera.getEntityId());
                        world.removeEntity(entityCamera);
                    }
                }
                cameraHandler.clearEntityIdMap();
            }
            else if (astring.length > 1) {
                int camId = 0;
                try {
                    camId = Integer.parseInt(astring[1]);
                } catch (Exception exception) {
                    throw new WrongUsageException("you need an int", new Object[0]);
                }

                if (astring[0].equals("new")) {
                    cameraHandler.newCamera(camId, entityplayermp);
                }
                else if (astring[0].equals("set")) {
                    cameraHandler.setPlayerCamera(camId, entityplayermp);
                }
                else if (astring[0].equals("delete")) {
                    cameraHandler.removeCamera(camId);

                }
            }
            else {
                throw new WrongUsageException("/camera <start:stop:new:set:delete> <camera id>", new Object[0]);
            }
        }
        else {
            throw new WrongUsageException("you messed up", new Object[0]);
        }
    }

}
