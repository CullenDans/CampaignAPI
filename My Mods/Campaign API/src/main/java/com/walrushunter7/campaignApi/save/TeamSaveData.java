package com.walrushunter7.campaignApi.save;

import net.minecraft.nbt.NBTTagCompound;

import java.io.File;

public class TeamSaveData extends SaveData {

    public TeamSaveData(File saveDir, String fileName) {
        this.saveDir = saveDir;
        this.fileName = fileName;
    }

    public void WriteToNBT(NBTTagCompound tagCompound) {

    }

    public void ReadFromNBT(NBTTagCompound tagCompound) {

    }

}
