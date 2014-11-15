package com.walrushunter7.campaignApi.mission;

import com.mojang.authlib.GameProfile;
import com.walrushunter7.campaignApi.mission.stage.Stage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Mission {

    public Map<Integer, Stage> stageMap = new HashMap<Integer, Stage>();
    public Map<GameProfile, Integer> playerStageNumber = new HashMap<GameProfile, Integer>();
    public String missionID;
    public String missionName;

    public Mission(String missionID, String missionName) {
        this.missionID = missionID;
        this.missionName = missionName;
    }

    public void WriteToNBT(NBTTagCompound tagCompound) {
        NBTTagList stageTagList = new NBTTagList();
        Iterator<Stage> stageIterator = stageMap.values().iterator();
        while (stageIterator.hasNext()) {
            Stage stage = stageIterator.next();
            NBTTagCompound stageTag = new NBTTagCompound();
            stage.WriteToNBT(stageTag);
            stageTagList.appendTag(stageTag);
        }

        NBTTagList gameProfileTagList = new NBTTagList();
        Iterator<Map.Entry<GameProfile, Integer>> playerStageIterator = playerStageNumber.entrySet().iterator();
        while (playerStageIterator.hasNext()) {
            Map.Entry<GameProfile, Integer> playerStageEntry = playerStageIterator.next();
            NBTTagCompound playerStageTag = new NBTTagCompound();
            NBTTagCompound gameProfileTag = new NBTTagCompound();
            NBTUtil.func_152460_a(gameProfileTag, playerStageEntry.getKey());
            playerStageTag.setTag("GameProfile", gameProfileTag);
            playerStageTag.setInteger("StageNumber", playerStageEntry.getValue());
            gameProfileTagList.appendTag(playerStageTag);
        }

        tagCompound.setString("MissionName", missionName);
        tagCompound.setString("MissionID", missionID);
        tagCompound.setTag("StagesList", stageTagList);
        tagCompound.setTag("PlayerStageNumbers", gameProfileTagList);

    }


}
