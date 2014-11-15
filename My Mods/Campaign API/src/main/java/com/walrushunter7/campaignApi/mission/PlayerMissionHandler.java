package com.walrushunter7.campaignApi.mission;

import com.mojang.authlib.GameProfile;
import com.walrushunter7.campaignApi.nbt.NBTtoFile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;

import java.util.HashSet;
import java.util.Set;

public class PlayerMissionHandler {

    public Set<Mission> activeMissions = new HashSet<Mission>();
    public GameProfile gameProfile;

    public PlayerMissionHandler(GameProfile player) {
        this.gameProfile = player;
    }

    public void addMission(Mission mission) {
        activeMissions.add(mission);
    }

    public void WriteToNBT(NBTTagCompound tagCompound) {
        NBTTagList missionsList = new NBTTagList();
        for (Mission mission: activeMissions) {
            NBTTagCompound missionTag = new NBTTagCompound();
            mission.WriteToNBT(missionTag);
            missionsList.appendTag(missionTag);
        }
        tagCompound.setTag("MissionsList", missionsList);

        NBTTagCompound gameProfileTag = new NBTTagCompound();
        NBTUtil.func_152460_a(gameProfileTag, gameProfile);
        tagCompound.setTag("GameProfile", gameProfileTag);
    }

    public void Save() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.WriteToNBT(tagCompound);
        NBTtoFile.writeTag(NBTtoFile.playerDataDir, gameProfile.getId().toString(), tagCompound);
    }

}
