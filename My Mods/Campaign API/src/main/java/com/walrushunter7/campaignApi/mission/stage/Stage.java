package com.walrushunter7.campaignApi.mission.stage;

import com.walrushunter7.campaignApi.mission.CompletionType;
import net.minecraft.nbt.NBTTagCompound;

public class Stage {

    public int stageNumber;
    public int nextStageNumber;

    public CompletionType completionType;

    public NBTTagCompound data;

    public Stage(int stageNumber, int nextStageNumber, NBTTagCompound data) {
        this.stageNumber = stageNumber;
        this.nextStageNumber = nextStageNumber;
        this.data = data;
    }

    public void WriteToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("StageNumber", stageNumber);
        tagCompound.setInteger("NextStageNumber", nextStageNumber);
        tagCompound.setTag("Data", data);
    }



}
