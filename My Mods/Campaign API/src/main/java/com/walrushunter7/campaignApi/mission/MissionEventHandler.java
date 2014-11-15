package com.walrushunter7.campaignApi.mission;

import com.walrushunter7.campaignApi.entity.EntityScriptableMob;
import com.walrushunter7.campaignApi.mission.stage.type.MissionType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.HashSet;
import java.util.Set;

public class MissionEventHandler {

    public Set<MissionType.KillEntity> livingDeathWatchers = new HashSet<MissionType.KillEntity>();
    public Set<MissionType.KillMonsters> killMonstersWatchers = new HashSet<MissionType.KillMonsters>();
    //public Set<>;

    @SubscribeEvent
    public void LivingDeath(LivingDeathEvent event) {
        if (!livingDeathWatchers.isEmpty()) {
            for (MissionType.KillEntity killEntity : livingDeathWatchers) {
                if (event.source.getSourceOfDamage() instanceof EntityPlayerMP) {
                    if (((EntityPlayerMP) event.source.getSourceOfDamage()).getGameProfile().equals(killEntity.player)) {
                        if (event.entityLiving.getUniqueID().equals(killEntity.targetUUID)) {
                            killEntity.execute();
                        }
                    }
                }
            }
        }
        else if (!killMonstersWatchers.isEmpty()) {
            for (MissionType.KillMonsters killMonsters : killMonstersWatchers) {
                if (event.source.getSourceOfDamage() instanceof EntityPlayerMP) {
                    if (((EntityPlayerMP) event.source.getSourceOfDamage()).getGameProfile().equals(killMonsters.player)) {
                        if (event.entityLiving instanceof EntityMob || event.entityLiving instanceof EntityScriptableMob) {
                            killMonsters.execute();
                        }
                    }
                }
            }
        }
    }



}
