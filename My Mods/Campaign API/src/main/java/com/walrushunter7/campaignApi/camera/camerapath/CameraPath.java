package com.walrushunter7.campaignApi.camera.camerapath;

public class CameraPath {

    /** Contains the points in this path */
    private CameraPathPoint[] cameraPathPoints = new CameraPathPoint[1024];
    /** The number of points in this path */
    private int count;

    /**
     * Adds a point to the path
     */
    public CameraPathPoint addPoint(CameraPathPoint cameraPathPoint)
    {
        if (cameraPathPoint.index >= 0)
        {
            throw new IllegalStateException("OW KNOWS!");
        }
        else
        {
            if (this.count == this.cameraPathPoints.length)
            {
                CameraPathPoint[] acamerapathpoint = new CameraPathPoint[this.count << 1];
                System.arraycopy(this.cameraPathPoints, 0, acamerapathpoint, 0, this.count);
                this.cameraPathPoints = acamerapathpoint;
            }

            this.cameraPathPoints[this.count] = cameraPathPoint;
            cameraPathPoint.index = this.count;
            this.sortBack(this.count++);
            return cameraPathPoint;
        }
    }

    /**
     * Clears the path
     */
    public void clearPath()
    {
        this.count = 0;
    }

    /**
     * Returns and removes the first point in the path
     */
    public CameraPathPoint dequeue()
    {
        CameraPathPoint cameraPathPoint = this.cameraPathPoints[0];
        this.cameraPathPoints[0] = this.cameraPathPoints[--this.count];
        this.cameraPathPoints[this.count] = null;

        if (this.count > 0)
        {
            this.sortForward(0);
        }

        cameraPathPoint.index = -1;
        return cameraPathPoint;
    }

    /**
     * Changes the provided point's distance to target
     */
    public void changeDistance(CameraPathPoint cameraPathPoint, float par2)
    {
        float f1 = cameraPathPoint.distanceToTarget;
        cameraPathPoint.distanceToTarget = par2;

        if (par2 < f1)
        {
            this.sortBack(cameraPathPoint.index);
        }
        else
        {
            this.sortForward(cameraPathPoint.index);
        }
    }

    /**
     * Sorts a point to the left
     */
    private void sortBack(int p_75847_1_)
    {
        CameraPathPoint cameraPathPoint = this.cameraPathPoints[p_75847_1_];
        int j;

        for (float f = cameraPathPoint.distanceToTarget; p_75847_1_ > 0; p_75847_1_ = j)
        {
            j = p_75847_1_ - 1 >> 1;
            CameraPathPoint cameraPathPoint1 = this.cameraPathPoints[j];

            if (f >= cameraPathPoint1.distanceToTarget)
            {
                break;
            }

            this.cameraPathPoints[p_75847_1_] = cameraPathPoint1;
            cameraPathPoint1.index = p_75847_1_;
        }

        this.cameraPathPoints[p_75847_1_] = cameraPathPoint;
        cameraPathPoint.index = p_75847_1_;
    }

    /**
     * Sorts a point to the right
     */
    private void sortForward(int p_75846_1_)
    {
        CameraPathPoint cameraPathPoint = this.cameraPathPoints[p_75846_1_];
        float f = cameraPathPoint.distanceToTarget;

        while (true)
        {
            int j = 1 + (p_75846_1_ << 1);
            int k = j + 1;

            if (j >= this.count)
            {
                break;
            }

            CameraPathPoint cameraPathPoint1 = this.cameraPathPoints[j];
            float f1 = cameraPathPoint1.distanceToTarget;
            CameraPathPoint cameraPathPoint2;
            float f2;

            if (k >= this.count)
            {
                cameraPathPoint2 = null;
                f2 = Float.POSITIVE_INFINITY;
            }
            else
            {
                cameraPathPoint2 = this.cameraPathPoints[k];
                f2 = cameraPathPoint2.distanceToTarget;
            }

            if (f1 < f2)
            {
                if (f1 >= f)
                {
                    break;
                }

                this.cameraPathPoints[p_75846_1_] = cameraPathPoint1;
                cameraPathPoint1.index = p_75846_1_;
                p_75846_1_ = j;
            }
            else
            {
                if (f2 >= f)
                {
                    break;
                }

                this.cameraPathPoints[p_75846_1_] = cameraPathPoint2;
                cameraPathPoint2.index = p_75846_1_;
                p_75846_1_ = k;
            }
        }

        this.cameraPathPoints[p_75846_1_] = cameraPathPoint;
        cameraPathPoint.index = p_75846_1_;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean isPathEmpty()
    {
        return this.count == 0;
    }

}
