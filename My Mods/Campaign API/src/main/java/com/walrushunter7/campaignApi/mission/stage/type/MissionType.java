package com.walrushunter7.campaignApi.mission.stage.type;

import com.walrushunter7.campaignApi.mission.stage.Stage;
import cpw.mods.fml.common.eventhandler.Event;

import java.util.UUID;

public class MissionType extends Type{
    
    public MissionType(Stage stage) {
        super(stage);
    }

    public class KillEntity extends MissionType {
        public UUID targetUUID;

        public KillEntity(Stage stage, UUID targetUUID) {
            super(stage);
            this.targetUUID = targetUUID;
        }

        public void execute() {
            super.execute();
        }

    }

    public class KillMonsters extends MissionType {

        public int totalCount;
        public int currentCount;

        public KillMonsters(Stage stage, int totalCount, int currentCount) {
            super(stage);
            this.totalCount = totalCount;
            this.currentCount = currentCount;
        }
        public void onTrigger(Event event) {
            //super.execute();
            if (currentCount < totalCount) {
                currentCount++;
            } else {

            }
        }
    }

}
