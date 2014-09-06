package com.walrushunter7.campaignApi.camera;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CameraWorldHandler {

    private static final Logger logger = LogManager.getLogger();

    private static Map<World, CameraHandler> cameraHandlerMap = new HashMap<World, CameraHandler>();

    @SubscribeEvent
    public void Load(WorldEvent.Load event) {
        if (!cameraHandlerMap.containsKey(event.world)) {
            cameraHandlerMap.put(event.world, new CameraHandler(event.world));
            readWorldData(event.world);
        }
    }

    @SubscribeEvent
    public void Save(WorldEvent.Save event) {
        writeWorldData(event.world);
    }

    @SubscribeEvent
    public void Unload(WorldEvent.Unload event) {
        if (cameraHandlerMap.containsKey(event.world)) {
            cameraHandlerMap.remove(event.world);
        }
    }

    public static CameraHandler gerWorldCameraHandler(World world) {
        return cameraHandlerMap.get(world);
    }

    public void writeWorldData(World world) {
        try {
            NBTTagCompound tagCompound = new NBTTagCompound();
            cameraHandlerMap.get(world).WriteToNBT(tagCompound);
            File campaignAPIDirectory = world.getSaveHandler().getWorldDirectory();//new File(world.getSaveHandler().getWorldDirectory(), "CampaignAPI");

            File file1 = new File(campaignAPIDirectory, "CampaignWorldData.dat.tmp");
            File file2 = new File(campaignAPIDirectory, "CampaignWorldData.dat");
            CompressedStreamTools.writeCompressed(tagCompound, new FileOutputStream(file1));

            if (file2.exists())
            {
                file2.delete();
            }

            file1.renameTo(file2);
        }
        catch (Exception exception)
        {
            logger.warn(exception);
            logger.warn("Failed to save team data");
        }
    }

    public void readWorldData(World world) {
        NBTTagCompound tagCompound = this.getWorldData(world);

        if (tagCompound != null)
        {
            cameraHandlerMap.get(world).ReadFromNBT(tagCompound);
        }
    }

    public NBTTagCompound getWorldData(World world) {
        try
        {
            File campaignAPIDirectory = world.getSaveHandler().getWorldDirectory();//new File(world.getSaveHandler().getWorldDirectory(), "CampaignAPI");
            File file1 = new File(campaignAPIDirectory, "CampaignWorldData.dat");

            if (file1.exists())
            {
                return CompressedStreamTools.readCompressed(new FileInputStream(file1));
            }
        }
        catch (Exception exception)
        {
            logger.warn("Failed to load world data");
        }
        return null;
    }

}
