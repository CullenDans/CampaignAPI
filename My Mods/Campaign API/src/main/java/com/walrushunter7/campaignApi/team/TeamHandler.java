package com.walrushunter7.campaignApi.team;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeamHandler {

    private static Map<String, TeamAI> teams = new HashMap<String, TeamAI>();
    private static Map<GameProfile, String> playerTeamIds = new HashMap<GameProfile, String>();

    public static TeamSaveData saveData;

    public static Iterator<TeamAI> getTeamIterator() {
        return teams.values().iterator();
    }
    
    public static TeamAI getTeamFromID(String teamID) {
        if (teamID != null) {
            if (teams.containsKey(teamID)) {
                return teams.get(teamID);
            }
        }
        return null;
    }

    public static TeamAI getPlayerTeam(EntityPlayer player) {
        return getPlayerTeam(player.getGameProfile());
    }

    public static TeamAI getPlayerTeam(GameProfile gameProfile) {
        if (playerTeamIds.containsKey(gameProfile)) {
            String teamId = playerTeamIds.get(gameProfile);
            if (teams.containsKey(teamId)) {
                return teams.get(teamId);
            }
        }
        return null;
    }

    public static void addPlayerToTeam(EntityPlayer player, String teamAI) {
        addPlayerToTeam(player.getGameProfile(), teamAI);
        saveData.markDirty();
    }

    public static void addPlayerToTeam(GameProfile gameProfile, String teamAI) {
        if (playerTeamIds.containsKey(gameProfile)) {
            playerTeamIds.remove(gameProfile);
        }
        playerTeamIds.put(gameProfile, teamAI);
        saveData.markDirty();
    }

}
