package com.walrushunter7.campaignApi.event;

import com.walrushunter7.campaignApi.camera.CameraHandler;
import com.walrushunter7.campaignApi.entity.EntityCamera;
import com.walrushunter7.campaignApi.mission.MissionHandler;
import com.walrushunter7.campaignApi.save.SaveDataHandler;
import com.walrushunter7.campaignApi.team.TeamHandler;
import com.walrushunter7.campaignApi.team.TeamSaveData;
import com.walrushunter7.campaignApi.util.Log;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventHandler {

    private static final Logger logger = LogManager.getLogger();

    private static Map<World, CameraHandler> cameraHandlerMap = new HashMap<World, CameraHandler>();

    @SubscribeEvent
    public void Load(WorldEvent.Load event) {
        if (!event.world.isRemote) {
            WorldServer world = (WorldServer) event.world;

            if (!cameraHandlerMap.containsKey(world)) {
                cameraHandlerMap.put(world, new CameraHandler(world));
                readWorldData(world);
            }

            if (world.provider.dimensionId == 0) {
                logger.info("Loading dim 0");
                TeamSaveData saveData = (TeamSaveData) world.perWorldStorage.loadData(TeamSaveData.class, "teams");

                if (saveData == null) {
                    saveData = new TeamSaveData();
                    world.perWorldStorage.setData("teams", saveData);
                }
                saveData.markDirty();
                TeamHandler.saveData = saveData;

                SaveDataHandler.Load();

            }

        }
    }

    @SubscribeEvent
    public void Save(WorldEvent.Save event) {
        if (!event.world.isRemote) {
            WorldServer world = (WorldServer) event.world;

            if (world.provider.dimensionId == 0) {
                MissionHandler.SaveAllPlayers();
                SaveDataHandler.Save();
            }
        }
        writeWorldData(event.world);
    }

    @SubscribeEvent
    public void Unload(WorldEvent.Unload event) {
        if (cameraHandlerMap.containsKey(event.world)) {
            cameraHandlerMap.remove(event.world);
        }
    }

    @SubscribeEvent
    public void LoadChunk(ChunkEvent.Load event) {
        CameraHandler cameraHandler = gerWorldCameraHandler(event.world);

        List[] list = event.getChunk().entityLists;
        for (List alist: list) {
            Iterator entityIterator = alist.iterator();
            while (entityIterator.hasNext()) {
                Object entityObject = entityIterator.next();
                if (entityObject instanceof EntityCamera) {
                    EntityCamera entityCamera = (EntityCamera) entityObject;
                    cameraHandler.loadCamera(entityCamera, entityCamera.getCameraId());
                    //entityCamera.getCameraId()
                    Log.info("Camera loaded: " + entityCamera.getCameraId());
                }
            }

        }
    }

    @SubscribeEvent
    public void UnloadChunk(ChunkEvent.Unload event) {
        CameraHandler cameraHandler = gerWorldCameraHandler(event.world);

        List[] list = event.getChunk().entityLists;
        for (List entityList: list) {
            Iterator entityIterator = entityList.iterator();
            while (entityIterator.hasNext()) {
                Object entityObject = entityIterator.next();
                if (entityObject instanceof EntityCamera) {
                    EntityCamera entityCamera = (EntityCamera) entityObject;
                    cameraHandler.unloadCamera(entityCamera);
                    //entityCamera.getCameraId()
                    Log.info("Camera unloaded: " + entityCamera.getCameraId());
                }
            }

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
