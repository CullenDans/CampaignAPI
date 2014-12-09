package com.walrushunter7.campaignApi.client;

import com.walrushunter7.campaignApi.CommonProxy;
import com.walrushunter7.campaignApi.client.render.ModelCamera;
import com.walrushunter7.campaignApi.client.render.RenderCamera;
import com.walrushunter7.campaignApi.entity.EntityCamera;
import com.walrushunter7.campaignApi.entity.EntityMan;
import com.walrushunter7.campaignApi.util.Log;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;

public class ClientProxy extends CommonProxy{

    public void registerRender() {
        Log.info("Worked_________");
        RenderingRegistry.registerEntityRenderingHandler(EntityCamera.class, new RenderCamera(new ModelCamera()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMan.class, new RenderBiped(new ModelBiped(1.0F), 1.0F));
    }

}
