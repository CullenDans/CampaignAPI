package com.walrushunter7.campaignApi.event;

import com.walrushunter7.campaignApi.team.TeamSaveData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;

public class EventHandler {

    @SubscribeEvent
    public static void load(WorldEvent.Load event) {

        if (!event.world.isRemote) {
            WorldServer worldServer = (WorldServer) event.world;

            if (worldServer.provider.dimensionId == 0) {

                TeamSaveData saveData = (TeamSaveData) worldServer.perWorldStorage.loadData(TeamSaveData.class, "teams");

                if (saveData == null) {
                    worldServer.perWorldStorage.setData("teams", new TeamSaveData());
                }

            }
        }

    }

}
