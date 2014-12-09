package com.walrushunter7.campaignApi.event;

import com.walrushunter7.campaignApi.mission.MissionHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class FMLEventHandler {

    @SubscribeEvent
    public void PlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        MissionHandler.playerLogin(event.player);
    }

    @SubscribeEvent
    public void PlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        MissionHandler.playerLogout(event.player);
    }

}
