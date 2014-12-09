package com.walrushunter7.campaignApi.camera;

import com.walrushunter7.campaignApi.CampaignAPI;
import com.walrushunter7.campaignApi.entity.EntityCamera;
import com.walrushunter7.campaignApi.network.CameraPacket;
import com.walrushunter7.campaignApi.util.Log;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CameraHandler {

    //public static Map<Integer,EntityCamera> entityCameraMap = new HashMap<Integer, EntityCamera>();
    public static Map<Integer,Integer> entityIdMap = new HashMap<Integer, Integer>();
    //public Map<Integer, Integer> entityIdMap2 = new HashMap<Integer, Integer>();

    private static boolean hideGUI;
    private static float fovSetting;
    private static int thirdPersonView;
    private static boolean isInCamera = false;

    public World world;

    public CameraHandler(World world) {
        this.world = world;
    }

    public void newCamera(int cameraId, EntityPlayerMP player) {
        EntityCamera entityCamera = new EntityCamera(world);
        entityCamera.setCameraId(cameraId);
        entityCamera.setPositionAndRotation(player.posX, player.posY + 1.5, player.posZ, player.rotationYawHead, player.rotationPitch);
        world.spawnEntityInWorld(entityCamera);
        loadCamera(entityCamera, cameraId);
        Log.info("Added camera: " + cameraId + " : " + entityCamera.getEntityId());
    }

    public void removeCamera(int id) {

        world.getEntityByID(entityIdMap.get(id)).setDead();

        entityIdMap.remove(id);
    }

    public void clearEntityIdMap() {
        entityIdMap.clear();
    }

    public void loadCamera(EntityCamera entityCamera, int cameraId) {
        if (entityIdMap.containsKey(cameraId)) {
            if (entityIdMap.get(cameraId) != null && !(entityIdMap.get(cameraId) == cameraId)) {
                Entity entity = world.getEntityByID(entityIdMap.get(cameraId));
                if (entity instanceof EntityCamera) {
                    EntityCamera entityCamera1 = (EntityCamera)entity;
                    entityCamera1.setDead();
                }
            }
        }
        entityIdMap.put(cameraId, entityCamera.getEntityId());
    }

    public void unloadCamera(EntityCamera entityCamera) {
        entityIdMap.put(entityCamera.getCameraId(), null);
    }

    public EntityCamera getCamera(int id) {
        if (entityIdMap.get(id) != null) {
            return (EntityCamera) world.getEntityByID(entityIdMap.get(id));
        } else {
            return null;
        }

    }

    public void setPlayerCamera(int cameraId, EntityPlayerMP playerMP) {
        if (entityIdMap.get(cameraId) != null) {
            CampaignAPI.network.sendTo(new CameraPacket((byte) 4, cameraId, entityIdMap.get(cameraId)), playerMP);
        } else {
            playerMP.addChatMessage(new ChatComponentText("Camera " + cameraId + " does not exist or is not in loaded chunks"));
            Log.error("Camera " + cameraId + " does not exist or is not in loaded chunks");
        }

    }

    @SideOnly(Side.CLIENT)
    public static void setPlayerCameraClient(int entityId) {
        Minecraft mc = Minecraft.getMinecraft();

        if (!isInCamera) {
            mc.getSoundHandler().stopSounds();
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation("campaignapi:hoc")));
            hideGUI = mc.gameSettings.hideGUI;
            fovSetting = mc.gameSettings.fovSetting;
            thirdPersonView = mc.gameSettings.thirdPersonView;

            mc.gameSettings.hideGUI = true;
            mc.gameSettings.fovSetting = 70F;
            mc.gameSettings.thirdPersonView = 0;
        }
        isInCamera = true;

        Entity entity = mc.theWorld.getEntityByID(entityId);
        if (entity != null && entity instanceof EntityCamera) {
            mc.renderViewEntity = (EntityCamera) entity;
        }
        else {
            Log.error("Could not find camera " + entityId);
        }

    }

    public void normalPlayerCamera(EntityPlayerMP playerMP) {
        CampaignAPI.network.sendTo(new CameraPacket((byte)2, 0, 0), playerMP);
    }

    @SideOnly(Side.CLIENT)
    public static void normalPlayerCameraClient() {
        isInCamera = false;
        Minecraft mc = Minecraft.getMinecraft();
        mc.getSoundHandler().stopSounds();
        mc.gameSettings.hideGUI = hideGUI;
        mc.gameSettings.fovSetting = fovSetting;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.renderViewEntity = mc.thePlayer;
    }

    public void WriteToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList tagList = new NBTTagList();
        Set<Map.Entry<Integer, Integer>> entries = entityIdMap.entrySet();

        for (Map.Entry<Integer, Integer> entry : entries) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("CameraId", entry.getKey());
            //tagCompound.setInteger("EntityId", entry.getValue());
            tagList.appendTag(tagCompound);
        }
        nbtTagCompound.setTag("CameraList", tagList);
    }

    public void ReadFromNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList tagList = nbtTagCompound.getTagList("CameraList", 10);

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            int cameraId = tagCompound.getInteger("CameraId");
            //int entityId = tagCompound.getInteger("EntityId");
            if (!entityIdMap.containsKey(cameraId)) {
                entityIdMap.put(cameraId, null);
            }
        }
    }

}
