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
import java.util.List;

/**
 * Internal density implementation. This class must not be exported through
 * public API.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DensityImpl implements Density {

    private ImageVoxels cube;
    private int voxelWidth;
    private int voxelHeight;
    private int voxelDepth;
    private ArrayList<WritableVoxel> voxels = new ArrayList<WritableVoxel>();

    /**
     * Constructor. <b>Note:</b> computes the average density for each voxel
     * subset.
     *
     * @param imageVoxels image voxels (usually from .tif-stack)
     * @param voxelSetWidth width of the voxel set (in image coordinates)
     * @param voxelSetHeight height of the voxel set (in image coordinates)
     * @param voxelSetDepth depth of the voxel set (in image coordinates)
     */
    public DensityImpl(ImageVoxels imageVoxels,
            int voxelWidth, int voxelHeight, int voxelDepth) {
        this.cube = imageVoxels;
        this.voxelWidth = voxelWidth;
        this.voxelHeight = voxelHeight;
        this.voxelDepth = voxelDepth;

        compute();
    }

    /**
     * Computes the average density for each voxel subset.
     */
    private void compute() {

        if (voxelWidth > cube.getWidth()) {
            voxelWidth = cube.getWidth();
        }

        if (voxelHeight > cube.getHeight()) {
            voxelHeight = cube.getHeight();
        }

        if (voxelDepth > cube.getDepth()) {
            voxelDepth = cube.getDepth();
        }

        for (int z = 0; z < cube.getDepth(); z += voxelDepth) {

            int depth = voxelDepth;

            if (z + depth > cube.getDepth() - 1) {
                depth = cube.getDepth() - z;
            }

            for (int y = 0; y < cube.getHeight(); y += voxelHeight) {

                int height = voxelHeight;

                if (y + height > cube.getHeight() - 1) {
                    height = cube.getHeight() - y;
                }

                for (int x = 0; x < cube.getWidth(); x += voxelWidth) {

                    int width = voxelWidth;

                    if (x + width > cube.getWidth() - 1) {
                        width = cube.getWidth() - x;
                    }

                    double value = 0;

                    int numVoxel = (depth) * (height) * (width);

                    for (int zz = z; zz < z + depth; zz++) {
                        for (int yy = y; yy < y + height; yy++) {
                            for (int xx = x; xx < x + width; xx++) {

                                value += cube.getData()[zz][xx][yy];
//                                value += cube.getVoxel(xx, yy, zz);
                            }
                        }
                    }

                    value /= numVoxel;
                    value /= 255.0; // scale to [0,1], image has 8-bit (min=0,max=255)

                    voxels.add(new VoxelImpl(
                            x, y, z, width, height, depth, value));

                } // for x
            } // for y
        } // for z

// relative scaling (currently disabled)
        
//        double min = Double.MAX_VALUE;
//        double max = Double.MIN_VALUE;
//
//        for (Voxel voxel : voxels) {
//            min = Math.min(min, voxel.getValue());
//            max = Math.max(max, voxel.getValue());
//        }
//
//        double scale = 255;//max - min;
//
//        for (WritableVoxel voxel : voxels) {
//            voxel.setValue(voxel.getValue() / scale);
//        }

    }

    @Override
    public List<? extends VoxelSet> getVoxels() {
        return voxels;
    }
}
