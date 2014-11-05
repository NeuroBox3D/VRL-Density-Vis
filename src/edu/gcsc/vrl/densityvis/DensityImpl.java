/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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