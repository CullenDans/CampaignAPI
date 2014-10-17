package com.walrushunter7.campaignApi.team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeamHandler {

    public static Map<String, Team> teams = new HashMap<String, Team>();

    public static Iterator<Team> getTeamIterator() {
        return teams.values().iterator();
    }

}
