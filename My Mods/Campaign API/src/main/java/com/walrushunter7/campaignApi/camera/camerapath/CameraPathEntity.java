package com.walrushunter7.campaignApi.camera.camerapath;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class CameraPathEntity {

    /** The actual points in the path */
    private final CameraPathPoint[] points;
    /** PathEntity Array Index the Entity is currently targeting */
    private int currentPathIndex;
    /** The total length of the path */
    private int pathLength;
    private static final String __OBFID = "CL_00000575";

    public CameraPathEntity(CameraPathPoint[] cameraPathPoints)
    {
        this.points = cameraPathPoints;
        this.pathLength = cameraPathPoints.length;
    }

    /**
     * Directs this path to the next point in its array
     */
    public void incrementPathIndex()
    {
        ++this.currentPathIndex;
    }

    /**
     * Returns true if this path has reached the end
     */
    public boolean isFinished()
    {
        return this.currentPathIndex >= this.pathLength;
    }

    /**
     * returns the last PathPoint of the Array
     */
    public CameraPathPoint getFinalPathPoint()
    {
        return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
    }

    /**
     * return the PathPoint located at the specified PathIndex, usually the current one
     */
    public CameraPathPoint getPathPointFromIndex(int point)
    {
        return this.points[point];
    }

    public int getCurrentPathLength()
    {
        return this.pathLength;
    }

    public void setCurrentPathLength(int currentPathLength)
    {
        this.pathLength = currentPathLength;
    }

    public int getCurrentPathIndex()
    {
        return this.currentPathIndex;
    }

    public void setCurrentPathIndex(int currentPathIndex)
    {
        this.currentPathIndex = currentPathIndex;
    }

    /**
     * Gets the vector of the PathPoint associated with the given index.
     */
    public Vec3 getVectorFromIndex(Entity entity, int currentPathIndex)
    {
        double d0 = (double)this.points[currentPathIndex].xCoord + (double)((int)(entity.width + 1.0F)) * 0.5D;
        double d1 = (double)this.points[currentPathIndex].yCoord;
        double d2 = (double)this.points[currentPathIndex].zCoord + (double)((int)(entity.width + 1.0F)) * 0.5D;
        return Vec3.createVectorHelper(d0, d1, d2);
    }

    /**
     * returns the current PathEntity target node as Vec3D
     */
    public Vec3 getPosition(Entity entity)
    {
        return this.getVectorFromIndex(entity, this.currentPathIndex);
    }

    /**
     * Returns true if the EntityPath are the same. Non instance related equals.
     */
    public boolean isSamePath(CameraPathEntity cameraPathEntity)
    {
        if (cameraPathEntity == null)
        {
            return false;
        }
        else if (cameraPathEntity.points.length != this.points.length)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < this.points.length; ++i)
            {
                if (this.points[i].xCoord != cameraPathEntity.points[i].xCoord || this.points[i].yCoord != cameraPathEntity.points[i].yCoord || this.points[i].zCoord != cameraPathEntity.points[i].zCoord)
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns true if the final PathPoint in the PathEntity is equal to Vec3D coords.
     */
    public boolean isDestinationSame(Vec3 vec3)
    {
        CameraPathPoint cameraPathPoint = this.getFinalPathPoint();
        return cameraPathPoint != null && cameraPathPoint.xCoord == (int) vec3.xCoord && cameraPathPoint.zCoord == (int) vec3.zCoord;
    }

}
