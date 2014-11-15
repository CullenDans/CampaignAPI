package com.walrushunter7.campaignApi.mission.stage;

import com.walrushunter7.campaignApi.mission.CompletionType;
import net.minecraft.nbt.NBTTagCompound;

public class MissionStage extends Stage{

    public CompletionType completionType;

    public MissionStage(int stageNumber, int nextStageNumber, CompletionType completionType, NBTTagCompound data) {
        super(stageNumber, nextStageNumber, data);
        this.completionType = completionType;
    }

    public void WriteToNBT(NBTTagCompound tagCompound) {
        super.WriteToNBT(tagCompound);
        tagCompound.setString("CompletionType", completionType.toString());
        tagCompound.setInteger("StageType", 1);
    }

}
