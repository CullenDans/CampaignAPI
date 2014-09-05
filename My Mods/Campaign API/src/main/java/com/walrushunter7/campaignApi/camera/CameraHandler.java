package com.walrushunter7.campaignApi.camera;

import com.walrushunter7.campaignApi.entity.EntityCamera;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class CameraHandler {

    public static Map<Integer,EntityCamera> entityCameraMap = new HashMap<Integer, EntityCamera>();

    private static boolean hideGUI;
    private static float fovSetting;
    private static int thirdPersonView;

    private static boolean isInCamera = false;

    @SideOnly(Side.CLIENT)
    public static int getUniqueId() {
        int i = 1;
        while (entityCameraMap.containsKey(i)) {
            i++;
        }
        return i;
    }

    @SideOnly(Side.CLIENT)
    public static void newCamera(int id) {
        World world = Minecraft.getMinecraft().theWorld;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        EntityCamera entityCamera = new EntityCamera(world);
        world.spawnEntityInWorld(entityCamera);
        entityCamera.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYawHead);
        addCamera(entityCamera, id);
    }

    @SideOnly(Side.CLIENT)
    public static void removeCamera(int id) {
        entityCameraMap.get(id).setDead();
        entityCameraMap.remove(id);
    }

    @SideOnly(Side.CLIENT)
    public static void addCamera(EntityCamera entityCamera, int id) {
        entityCameraMap.put(id, entityCamera);
    }

    @SideOnly(Side.CLIENT)
    public static EntityCamera getCamera(int id) {
        return entityCameraMap.get(id);
    }

    @SideOnly(Side.CLIENT)
    public static void setPlayerCamera(int id) {
        Minecraft mc = Minecraft.getMinecraft();

        if (!isInCamera) {
            hideGUI = mc.gameSettings.hideGUI;
            fovSetting = mc.gameSettings.fovSetting;
            thirdPersonView = mc.gameSettings.thirdPersonView;

            mc.gameSettings.hideGUI = true;
            mc.gameSettings.fovSetting = 70F;
            mc.gameSettings.thirdPersonView = 0;
        }
        isInCamera = true;
        mc.renderViewEntity = getCamera(id);
    }

    @SideOnly(Side.CLIENT)
    public static void normalPlayerCamera() {
        isInCamera = false;
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.hideGUI = hideGUI;
        mc.gameSettings.fovSetting = fovSetting;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.renderViewEntity = mc.thePlayer;
    }

}
