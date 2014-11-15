package com.walrushunter7.campaignApi.mission.stage;

import net.minecraft.nbt.NBTTagCompound;

public class CutsceneStage extends Stage{

    public CutsceneStage(int stageNumber, int nextStageNumber, NBTTagCompound data) {
        super(stageNumber, nextStageNumber, data);
    }

    public void WriteToNBT(NBTTagCompound tagCompound) {
        super.WriteToNBT(tagCompound);
        tagCompound.setInteger("StageType", 3);
    }
}
