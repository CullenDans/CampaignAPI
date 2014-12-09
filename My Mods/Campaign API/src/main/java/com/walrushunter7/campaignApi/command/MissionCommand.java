package com.walrushunter7.campaignApi.command;

import com.walrushunter7.campaignApi.team.TeamHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;

public class MissionCommand extends CommandBase {

    public String getCommandName() {
        return "mission";
    }

    public String getCommandUsage(ICommandSender icommandsender) {
        return "/mission <create>";
    }

    public void processCommand(ICommandSender icommandsender, String[] args) {

        EntityPlayer entityPlayer = icommandsender.getEntityWorld().getPlayerEntityByName(icommandsender.getCommandSenderName());

        if (args.length > 0) {
            if (args[0] == "create") {
                /*
                *Temp
                */
                TeamHandler.addPlayerToTeam(entityPlayer, "test");

            }
        } else {
            throw new WrongUsageException("/mission <create>");
        }
    }



}
