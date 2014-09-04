package com.walrushunter7.campaignApi.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCamera extends EntityLivingBase{

    public EntityCamera(World world) {
        super(world);
        setSize(0.0F, 0.0F);
        boundingBox.setBounds(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        yOffset = 0.0F;
        noClip = true;
        
    }

    //Don't want fall damage
    @Override
    protected void fall(float blocks){

    }

    @Override
    public boolean isEntityInvulnerable(){
        return true;
    }

    @Override
    protected boolean canTriggerWalking(){
        return false;
    }

    @Override
    public boolean canBeCollidedWith(){
        return false;
    }

    @Override
    public boolean canBePushed(){
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean canRenderOnFire(){
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getShadowSize(){
        return 0.0F;
    }

    @Override
    public ItemStack getHeldItem(){
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int i){
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int i, ItemStack itemstack){

    }

    @Override
    public ItemStack[] getLastActiveItems(){
        return new ItemStack[]{null, null, null, null, null};
    }

}
