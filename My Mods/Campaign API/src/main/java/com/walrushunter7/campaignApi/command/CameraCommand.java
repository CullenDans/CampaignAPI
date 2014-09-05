package com.walrushunter7.campaignApi.command;

import com.walrushunter7.campaignApi.CampaignAPI;
import com.walrushunter7.campaignApi.network.CameraPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;

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

            if (astring[0].equals("start"))
            {
                CampaignAPI.network.sendTo(new CameraPacket((byte)1, 0), entityplayermp);
            }
            else if (astring[0].equals("stop")) {
                CampaignAPI.network.sendTo(new CameraPacket((byte)2, 0), entityplayermp);
            }
            else if (astring.length > 1) {
                int camId = 0;
                try {
                    camId = Integer.parseInt(astring[1]);
                } catch (Exception exception) {
                    throw new WrongUsageException("you need an int", new Object[0]);
                }

                if (astring[0].equals("new")) {
                    CampaignAPI.network.sendTo(new CameraPacket((byte)3, camId), entityplayermp);
                }
                else if (astring[0].equals("set")) {
                    CampaignAPI.network.sendTo(new CameraPacket((byte)4, camId), entityplayermp);
                }
                else if (astring[0].equals("delete")) {
                    CampaignAPI.network.sendTo(new CameraPacket((byte)5, camId), entityplayermp);
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
