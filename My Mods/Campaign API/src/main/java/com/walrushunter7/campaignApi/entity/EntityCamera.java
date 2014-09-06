package com.walrushunter7.campaignApi.entity;

import com.walrushunter7.campaignApi.camera.CameraWorldHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCamera extends EntityLivingBase{

    public EntityCamera(World world) {
        super(world);
        //this.dataWatcher.updateObject(28);
        setSize(0.0F, 0.0F);
        boundingBox.setBounds(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        yOffset = 0.0F;
        noClip = false;

    }



    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(28, 0);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        //this.posY = prevPosY;

    }

    //Don't want fall damage
    @Override
    protected void fall(float blocks){

    }

    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
    {
        if (this.isInWater())
        {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
        }
        else if (this.handleLavaMovement())
        {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }
        else
        {
            float f2 = 0.91F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f2;
            this.motionY *= (double)f2;
            this.motionZ *= (double)f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    public void setCameraId(int cameraId) {
        this.dataWatcher.updateObject(28, cameraId);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);
        this.dataWatcher.updateObject(28, tagCompound.getInteger("CameraId"));
        CameraWorldHandler.gerWorldCameraHandler(this.worldObj).addCamera(this, this.dataWatcher.getWatchableObjectInt(28));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("CameraId", this.getDataWatcher().getWatchableObjectInt(28));

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
    protected void updateFallState(double p_70064_1_, boolean p_70064_3_) {

    }

    @Override
    public ItemStack[] getLastActiveItems(){
        return new ItemStack[]{null, null, null, null, null};
    }

}
