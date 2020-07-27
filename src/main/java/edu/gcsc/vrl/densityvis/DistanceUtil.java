/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015–2020 G-CSC, Uni Frankfurt
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. A suitable
 * notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Please cite the publication(s) listed below.
 *
 * Publications:
 *
 * M. Hoffer, C. Poliwoda, & G. Wittum. (2013). Visual reflection library:
 * a framework for declarative GUI programming on the Java platform.
 * Computing and Visualization in Science, 2013, 16(4),
 * 181–192. http://doi.org/10.1007/s00791-014-0230-y
 */
package edu.gcsc.vrl.densityvis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Point3f;

/**
 * Utility class that provides methods to compute the minimum distance between
 * voxel sets and points.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class DistanceUtil {

    /**
     * Computes the minimum distance between the specified voxel sets (density)
     * and the specified geometry (points). <p> <b>Note:</b> the computation is
     * performed in multiple threads (#threads = #cores) </p>
     *
     * @param density voxel sets (density information)
     * @param points point array (geometry)
     * @param percentage minimum density threashold (in %)
     * @return the minimum distances between the specified voxel sets and the
     * specified geometry
     */
    public static Collection<Distance> computeDistance(
            Density density, Point3f[] points,
            int percentage) {

        return computeDistance(density, points, percentage,
                Runtime.getRuntime().availableProcessors());
    }

    /**
     * Computes the minimum distance between the specified voxel sets (density)
     * and the specified geometry (points).
     *
     * @param density voxel sets (density information)
     * @param points point array (geometry)
     * @param percentage minimum density threashold (in %)
     * @param numThreads number of threads that shall be used
     * @return the minimum distances between the specified voxel sets and the
     * specified geometry
     */
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
                Logger.getLogger(DistanceUtil.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }

        for (DistanceThread thread : distanceThreads) {
            distances.addAll(thread.getDistances());
        }

        return distances;
    }
}

/**
 * Distance computation thread. This class is for internal use only.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DistanceThread extends Thread {

    private Collection<Distance> distances = new ArrayList<Distance>();
    private int startIndex;
    private int stopIndex;
    private double percentage;
    private List<? extends VoxelSet> voxels;
    private Point3f[] points;

    /**
     * Constructor.
     *
     * @param startIndex start index
     * @param stopIndex stop index
     * @param voxels voxel sets
     * @param points geometry points
     * @param percentage minimum density threashold (in %)
     */
    public DistanceThread(int startIndex, int stopIndex,
            List<? extends VoxelSet> voxels, Point3f[] points, double percentage) {
        this.startIndex = startIndex;
        this.stopIndex = stopIndex;
        this.voxels = voxels;
        this.points = points;
        this.percentage = percentage;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= stopIndex; i++) {

            VoxelSet voxel = voxels.get(i);

            if (voxel.getValue() < percentage / 100.f) {
                continue;
            }

            double minDist = Double.MAX_VALUE;
            Point3f minDistPoint = null;

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

                if (dist < minDist) {
                    minDist = dist;
                    minDistPoint = p;
                }
            }

            getDistances().add(
                    new DistanceImpl(
                    voxel, new Point3f(minDistPoint), minDist));
        }
    }

    /**
     * @return the distances
     */
    public Collection<Distance> getDistances() {
        return distances;
    }
    
}
