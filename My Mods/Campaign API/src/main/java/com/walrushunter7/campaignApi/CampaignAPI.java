package com.walrushunter7.campaignApi;

import com.walrushunter7.campaignApi.Reference.Reference;
import com.walrushunter7.campaignApi.command.CameraCommand;
import com.walrushunter7.campaignApi.command.MissionCommand;
import com.walrushunter7.campaignApi.entity.EntityCamera;
import com.walrushunter7.campaignApi.entity.EntityMan;
import com.walrushunter7.campaignApi.event.EventHandler;
import com.walrushunter7.campaignApi.event.FMLEventHandler;
import com.walrushunter7.campaignApi.nbt.NBTtoFile;
import com.walrushunter7.campaignApi.network.CameraPacket;
import com.walrushunter7.campaignApi.util.Log;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class CampaignAPI {
    @SidedProxy(clientSide = Reference.Client_Proxy_Class, serverSide = Reference.Common_Proxy_Class)
    public static CommonProxy proxy;

    @Mod.Instance(Reference.MODID)
    public static CampaignAPI instance;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event){
        EntityRegistry.registerModEntity(EntityCamera.class, "Camera", EntityRegistry.findGlobalUniqueEntityId(), this, 256, 1, true);
        EntityRegistry.registerModEntity(EntityMan.class, "Man", 1, this, 256, 1, true);
        //EntityRegistry.registerGlobalEntityID(EntityCamera.class, "Camera", 300);

        network = NetworkRegistry.INSTANCE.newSimpleChannel("CampaignAPIChannel");
        network.registerMessage(CameraPacket.class, CameraPacket.class, 1, Side.CLIENT);



    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){
        //MinecraftForge.EVENT_BUS.register(new CameraWorldHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new FMLEventHandler());
        proxy.registerRender();

        registerEntityEgg(EntityMan.class, 111111, 000000);
    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event){

    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.getModState();
        MinecraftServer server = MinecraftServer.getServer();
        ICommandManager command = server.getCommandManager();
        ServerCommandManager manager = (ServerCommandManager) command;
        manager.registerCommand(new CameraCommand());
        manager.registerCommand(new MissionCommand());

        NBTtoFile.setDirectory(event.getServer().worldServerForDimension(0).getSaveHandler().getWorldDirectory());
        Log.info(event.getServer().worldServerForDimension(0).getSaveHandler().getWorldDirectory());
    }

    static int startEntityId = 300;

    public static int getUniqueEntityId() {

        do {
            startEntityId++;
        } while (EntityList.getStringFromID(startEntityId) != null);

        return startEntityId;

    }

    @SuppressWarnings("unchecked")
    public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) {
        int id = getUniqueEntityId();
        EntityList.IDtoClassMapping.put(id, entity);
        EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, primaryColor, secondaryColor));
    }
}
