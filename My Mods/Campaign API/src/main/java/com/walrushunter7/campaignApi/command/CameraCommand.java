package com.walrushunter7.campaignApi.command;

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

        if (astring.length > 1)
        {
            EntityPlayerMP entityplayermp = getPlayer(icommandsender, astring[2]);

            if (astring[0].equals("set"))
            {



            }
            else if (astring[0].equals("normal")) {

            }
            else {
                throw new WrongUsageException("you messed up", new Object[0]);
            }
        }
        else {
            throw new WrongUsageException("you messed up", new Object[0]);
        }
    }

}
