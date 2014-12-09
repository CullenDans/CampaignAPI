package com.walrushunter7.campaignApi.team;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.scoreboard.Team;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TeamAI extends Team {

    private String teamName;
    private String teamId;

    private boolean allowFriendlyFire = true;

    //public Set<EntityScriptable> members
    public Set<GameProfile> players = new HashSet<GameProfile>();
    public Set<TeamAI> allies = new HashSet<TeamAI>();
    public Set<TeamAI> enemies = new HashSet<TeamAI>();

    public TeamAI(String teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public Iterator<GameProfile> getPlayersIterator() {
        return players.iterator();
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public boolean isSameTeam(TeamAI team) {
        return this.equals(team);
    }

    public boolean isAllie(TeamAI team) {
        return allies.contains(team);
    }

    public boolean isEnemy(TeamAI team) {
        return enemies.contains(team);
    }

    public String getRegisteredName() {
        return this.teamId;
    }

    public String formatString(String s) {
        return s;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_98297_h() {
        return false;
    }

    public boolean getAllowFriendlyFire() {
        return allowFriendlyFire;
    }



}
