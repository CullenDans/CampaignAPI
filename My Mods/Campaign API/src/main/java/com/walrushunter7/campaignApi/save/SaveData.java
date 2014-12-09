package com.walrushunter7.campaignApi.save;

import com.walrushunter7.campaignApi.nbt.NBTtoFile;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;

public abstract class SaveData {

    public File saveDir;
    public String fileName;

    public SaveData() {

    }

    public abstract void WriteToNBT(NBTTagCompound tagCompound);

    public abstract void ReadFromNBT(NBTTagCompound tagCompound);

    public void Save() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.WriteToNBT(tagCompound);
        NBTtoFile.writeTag(saveDir, fileName, tagCompound);
    }

    public void Load() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        NBTtoFile.readTag(saveDir, fileName, tagCompound);
        this.ReadFromNBT(tagCompound);
    }

}
