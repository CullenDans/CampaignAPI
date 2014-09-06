package com.walrushunter7.campaignApi.client;

import com.walrushunter7.campaignApi.CommonProxy;
import com.walrushunter7.campaignApi.client.render.ModelCamera;
import com.walrushunter7.campaignApi.client.render.RenderCamera;
import com.walrushunter7.campaignApi.entity.EntityCamera;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

    public void registerRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCamera.class, new RenderCamera(new ModelCamera()));
    }

}
