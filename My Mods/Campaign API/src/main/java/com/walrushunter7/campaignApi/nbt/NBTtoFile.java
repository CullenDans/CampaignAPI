package com.walrushunter7.campaignApi.nbt;

import com.walrushunter7.campaignApi.util.Log;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class NBTtoFile {

    public static File worldDirectory;
    public static File CampaignAPIDir;
    public static File playerDataDir;
    public static File missionsDir;

    public static boolean doesTagExist() {
        return false;
    }

    public static void readTag(File directory, String fileName, NBTTagCompound tagCompound) {
        try
        {
            File file1 = new File(directory, fileName + ".dat");

            if (file1.exists())
            {
                tagCompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
            }
        }
        catch (Exception exception) {}
    }

    public static void writeTag(File directory, String fileName, NBTTagCompound tagCompound) {
        try {

            File file1 = new File(directory, fileName + ".dat.tmp");
            File file2 = new File(directory, fileName + ".dat");
            CompressedStreamTools.writeCompressed(tagCompound, new FileOutputStream(file1));

            if (file2.exists())
            {
                file2.delete();
            }

            file1.renameTo(file2);
        }
        catch (Exception exception) {
            Log.error("Unable to save");
            Log.error(exception);
        }
    }

    public static void setDirectory(File worldDir) {
        worldDirectory = worldDir;
        CampaignAPIDir = new File(worldDir, "CampaignAPI");
        playerDataDir = new File(CampaignAPIDir, "player");
        missionsDir = new File(CampaignAPIDir, "missions");

        try {
            if (!CampaignAPIDir.isDirectory()) {
                CampaignAPIDir.mkdir();
            }
            if (!playerDataDir.isDirectory()) {
                playerDataDir.mkdir();
            }
            if (!missionsDir.isDirectory()) {
                missionsDir.mkdir();
            }
        } catch (Exception e) {
            Log.error("Unable to create the CampaignAPI directory");
        }

    }

}
