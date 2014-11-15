package com.walrushunter7.campaignApi.mission;

import com.mojang.authlib.GameProfile;
import com.walrushunter7.campaignApi.nbt.NBTtoFile;
import com.walrushunter7.campaignApi.util.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MissionHandler {

    public static Map<GameProfile, PlayerMissionHandler> playerMissionHandlerMap = new HashMap<GameProfile, PlayerMissionHandler>();

    public static void playerLogin(EntityPlayer entityPlayer) {
        Log.info("Player Login");
        GameProfile gameProfile = entityPlayer.getGameProfile();
        if (!playerMissionHandlerMap.containsKey(gameProfile)) {
            NBTTagCompound tagCompound = NBTtoFile.readTag(NBTtoFile.playerDataDir, gameProfile.getId().toString());
            if (tagCompound != null) {
                playerMissionHandlerMap.put(gameProfile, MissionNBT.playerMissionHandlerFromNBT(tagCompound));
            } else {
                playerMissionHandlerMap.put(gameProfile, new PlayerMissionHandler(gameProfile));
            }
        }
    }

    public static void playerLogout(EntityPlayer entityPlayer) {
        Log.info("Player Logout");
        GameProfile gameProfile = entityPlayer.getGameProfile();
        PlayerMissionHandler playerMissionHandler = playerMissionHandlerMap.get(gameProfile);
        playerMissionHandlerMap.remove(gameProfile);

        playerMissionHandler.Save();
    }

    public static void SaveAllPlayers() {
        Log.info("Saving all players");
        Collection<PlayerMissionHandler> missionHandlers = playerMissionHandlerMap.values();
        for (PlayerMissionHandler missionHandler: missionHandlers) {
            missionHandler.Save();
        }
    }

    public static void LoadAllPlayers() {

    }

}
