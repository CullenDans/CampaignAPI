package com.walrushunter7.campaignApi.mission.stage.type;

import com.mojang.authlib.GameProfile;
import com.walrushunter7.campaignApi.mission.stage.Stage;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.nbt.NBTTagCompound;

public class Type {

    public Stage stage;
    public GameProfile player;

    public Type(Stage stage) {
        this.stage = stage;
    }

    public void execute() {

    }

    public void onEvent(Event event) {

    }

    public void onComplete() {

    }

    public void WriteToNBT(NBTTagCompound tagCompound) {

    }

}
