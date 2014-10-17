package com.walrushunter7.campaignApi.camera.camerapath;

import net.minecraft.util.MathHelper;

public class CameraPathPoint {

    /** The x coordinate of this point */
    public final double xCoord;
    /** The y coordinate of this point */
    public final double yCoord;
    /** The z coordinate of this point */
    public final double zCoord;
    /** A hash of the coordinates used to identify this point */
    //private final double hash;
    /** The index of this point in its assigned path */
    int index = -1;
    /** The distance along the path to this point */
    float totalPathDistance;
    /** The linear distance to the next point */
    float distanceToNext;
    /** The distance to the target */
    float distanceToTarget;
    /** The point preceding this in its assigned path */
    CameraPathPoint previous;
    /** Indicates this is the origin */
    public boolean isFirst;

    public CameraPathPoint(double xCoord, double yCoord, double zCoord)
    {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        //this.hash = makeHash(xCoord, yCoord, zCoord);
    }

    /*public static int makeHash(double xCoord, double yCoord, double zCoord)
    {
        return yCoord & 255 | (xCoord & 32767) << 8 | (zCoord & 32767) << 24 | (xCoord < 0 ? Integer.MIN_VALUE : 0) | (zCoord < 0 ? 32768 : 0);
    }
    */
    /**
     * Returns the linear distance to another path point
     */
    public float distanceTo(CameraPathPoint cameraPathPoint)
    {
        float f = (float)(cameraPathPoint.xCoord - this.xCoord);
        float f1 = (float)(cameraPathPoint.yCoord - this.yCoord);
        float f2 = (float)(cameraPathPoint.zCoord - this.zCoord);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    /**
     * Returns the squared distance to another path point
     */
    public float distanceToSquared(CameraPathPoint cameraPathPoint)
    {
        float f = (float)(cameraPathPoint.xCoord - this.xCoord);
        float f1 = (float)(cameraPathPoint.yCoord - this.yCoord);
        float f2 = (float)(cameraPathPoint.zCoord - this.zCoord);
        return f * f + f1 * f1 + f2 * f2;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof CameraPathPoint))
        {
            return false;
        }
        else
        {
            CameraPathPoint CameraPathPoint = (CameraPathPoint)p_equals_1_;
            return this.xCoord == CameraPathPoint.xCoord && this.yCoord == CameraPathPoint.yCoord && this.zCoord == CameraPathPoint.zCoord;
        }
    }

    /*public int hashCode()
    {
        return this.hash;
    }
    */
    /**
     * Returns true if this point has already been assigned to a path
     */
    public boolean isAssigned()
    {
        return this.index >= 0;
    }

    public String toString()
    {
        return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
    }
    
}
