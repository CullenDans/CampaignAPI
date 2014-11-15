package com.walrushunter7.campaignApi.mission;

import com.mojang.authlib.GameProfile;
import com.walrushunter7.campaignApi.mission.stage.ActionStage;
import com.walrushunter7.campaignApi.mission.stage.CutsceneStage;
import com.walrushunter7.campaignApi.mission.stage.MissionStage;
import com.walrushunter7.campaignApi.mission.stage.Stage;
import com.walrushunter7.campaignApi.util.Log;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;

public class MissionNBT {

    public static PlayerMissionHandler playerMissionHandlerFromNBT(NBTTagCompound tagCompound) {
        NBTTagCompound gameProfileTag = tagCompound.getCompoundTag("GameProfile");
        GameProfile gameProfile = NBTUtil.func_152459_a(gameProfileTag);

        PlayerMissionHandler playerMissionHandler = new PlayerMissionHandler(gameProfile);

        NBTTagList missionsList = tagCompound.getTagList("MissionsList", 10);
        for (int i = 0; i < missionsList.tagCount(); i++) {
            NBTTagCompound missionTag = missionsList.getCompoundTagAt(i);
            playerMissionHandler.addMission(MissionFromNBT(missionTag));
        }

        return playerMissionHandler;
    }

    public static Mission MissionFromNBT(NBTTagCompound tagCompound) {
        String missionID = tagCompound.getString("MissionID");
        String missionName = tagCompound.getString("MissionName");
        Mission mission = new Mission(missionID, missionName);

        NBTTagList stageTagList = tagCompound.getTagList("StagesList", 10);
        for (int i = 0; i < stageTagList.tagCount(); i++) {
            NBTTagCompound stageTag = stageTagList.getCompoundTagAt(i);
            Stage stage = StageFromNBT(stageTag);
            mission.stageMap.put(stage.stageNumber, stage);
        }

        NBTTagList gameProfileTagList = tagCompound.getTagList("PlayerStageNumbers", 10);
        for (int i = 0; i < gameProfileTagList.tagCount(); i++) {
            NBTTagCompound playerStageTag = gameProfileTagList.getCompoundTagAt(i);
            NBTTagCompound gameProfileTag = playerStageTag.getCompoundTag("GameProfile");
            GameProfile gameProfile = NBTUtil.func_152459_a(gameProfileTag);
            int stageNumber = playerStageTag.getInteger("StageNumber");
            mission.playerStageNumber.put(gameProfile, stageNumber);
        }

        return mission;
    }

    public static Stage StageFromNBT(NBTTagCompound tagCompound) {
        int stageNumber = tagCompound.getInteger("StageNumber");
        int nextStageNumber = tagCompound.getInteger("NextStageNumber");
        NBTTagCompound data = tagCompound.getCompoundTag("Data");

        int stageType = tagCompound.getInteger("StageType");

        switch (stageType) {
            case 1:
                CompletionType completionType = CompletionType.fromString(tagCompound.getString("CompletionType"));
                return new MissionStage(stageNumber, nextStageNumber, completionType, data);
            case 2:
                return new ActionStage(stageNumber, nextStageNumber, data);
            case 3:
                return new CutsceneStage(stageNumber, nextStageNumber, data);
            default:
                Log.error("Failed to create a stage of type " + stageType);
                return null;
        }

    }

}
