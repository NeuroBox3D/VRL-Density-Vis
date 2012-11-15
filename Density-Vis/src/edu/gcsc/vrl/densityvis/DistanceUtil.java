/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Point3f;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class DistanceUtil {

    public static Collection<Distance> computeDistance(
            Density density, Point3f[] points,
            int percentage) {

        return computeDistance(density, points, percentage,
                Runtime.getRuntime().availableProcessors());
    }

    public static Collection<Distance> computeDistance(
            Density density, Point3f[] points,
            int percentage, int numThreads) {

        Collection<Distance> distances = new ArrayList<Distance>();

        DistanceThread[] distanceThreads = new DistanceThread[numThreads];

        int n = density.getVoxels().size() - 1;
        int m = n / numThreads;

        int startIndex = 0;
        int stopIndex = m;

        for (int i = 0; i < numThreads; i++) {


            if (stopIndex > n) {
                stopIndex = n;
            }

            distanceThreads[i] = new DistanceThread(startIndex,
                    stopIndex,
                    density.getVoxels(),
                    points, percentage);

            startIndex = stopIndex + 1;
            stopIndex += m;

            distanceThreads[i].start();
        }

        for (DistanceThread thread : distanceThreads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(DistanceUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (DistanceThread thread : distanceThreads) {
            distances.addAll(thread.getDistances());
        }

        return distances;
    }
}

class DistanceThread extends Thread {

    private Collection<Distance> distances = new ArrayList<Distance>();
    private int startIndex;
    private int stopIndex;
    private int percentage;
    private List<? extends Voxel> voxels;
    private Point3f[] points;

    public DistanceThread(int startIndex, int stopIndex,
            List<? extends Voxel> voxels, Point3f[] points, int percentage) {
        this.startIndex = startIndex;
        this.stopIndex = stopIndex;
        this.voxels = voxels;
        this.points = points;
        this.percentage = percentage;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= stopIndex; i++) {

            Voxel voxel = voxels.get(i);

            if (voxel.getValue() < percentage / 100.f) {
                continue;
            }

            double minDist = Double.MAX_VALUE;

            for (Point3f p : points) {

                double voxelCenterX = voxel.getX() + voxel.getWidth() / 2.f;
                double voxelCenterY = voxel.getY() + voxel.getHeight() / 2.f;
                double voxelCenterZ = voxel.getZ() + voxel.getDepth() / 2.f;

                double xDiff = (voxelCenterX - p.x);
                double yDiff = (voxelCenterY - p.y);
                double zDiff = (voxelCenterZ - p.z);

                double dist = Math.sqrt(
                        xDiff * xDiff
                        + yDiff * yDiff
                        + zDiff * zDiff);

                minDist = Math.min(dist, minDist);
            }

            getDistances().add(new DistanceImpl(voxel, minDist));
        }
    }

    /**
     * @return the distances
     */
    public Collection<Distance> getDistances() {
        return distances;
    }
}
