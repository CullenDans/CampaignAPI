package com.walrushunter7.campaignApi.team;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.WorldSavedData;

import java.util.Iterator;

public class TeamSaveData extends WorldSavedData{

    public TeamSaveData() {
        super("teams");
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        Iterator<TeamAI> teamIterator = TeamHandler.getTeamIterator();
        System.out.println("reading teams");

    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        System.out.println("Saving teams");
        Iterator<TeamAI> teamIterator = TeamHandler.getTeamIterator();

        NBTTagList teamsList = new NBTTagList();
        while (teamIterator.hasNext()) {
            TeamAI team = teamIterator.next();
            NBTTagCompound teamTagCompound = new NBTTagCompound();

            teamTagCompound.setString("teamName", team.getTeamName());
            teamTagCompound.setString("teamId", team.getTeamId());

            NBTTagList playerList = new NBTTagList();
            Iterator<GameProfile> playerIterator = team.getPlayersIterator();
            while (playerIterator.hasNext()) {
                NBTTagCompound playerTagCompound = new NBTTagCompound();
                NBTUtil.func_152460_a(playerTagCompound, playerIterator.next());
                playerList.appendTag(playerTagCompound);
            }
            teamTagCompound.setTag("playerList", playerList);
        }
        nbtTagCompound.setTag("teamsList", teamsList);
    }
}
