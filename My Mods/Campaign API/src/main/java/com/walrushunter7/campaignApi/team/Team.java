package com.walrushunter7.campaignApi.team;

import com.mojang.authlib.GameProfile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Team {

    private String teamName;
    private int teamId;

    //public Set<EntityScriptable> members
    public Set<GameProfile> players = new HashSet<GameProfile>();
    public Set<Team> allies = new HashSet<Team>();
    public Set<Team> enemies = new HashSet<Team>();

    public Team(int teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public Iterator<GameProfile> getPlayersIterator() {
        return players.iterator();
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public boolean isSameTeam(Team team) {
        return this.equals(team);
    }

    public boolean isAllie(Team team) {
        return allies.contains(team);
    }

    public boolean isEnemy(Team team) {
        return enemies.contains(team);
    }



}
