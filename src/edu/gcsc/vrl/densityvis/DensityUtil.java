/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 * Util class that provides methods for computing the density distribution in
 * image stacks.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class DensityUtil {

    /**
     * Computes the density distribution in the specified image voxel data. The
     * average density of each voxel set is stored in the density information
     * object that is returned.
     *
     * @param imageVoxels image voxels (usually from .tif-stack)
     * @param voxelSetWidth width of the voxel set (in image coordinates)
     * @param voxelSetHeight height of the voxel set (in image coordinates)
     * @param voxelSetDepth depth of the voxel set (in image coordinates)
     * @return density information
     */
    public static Density computeDensity(ImageVoxels imageVoxels,
            int voxelSetWidth, int voxelSetHeight, int voxelSetDepth) {
        return new DensityImpl(
                imageVoxels, voxelSetWidth, voxelSetHeight, voxelSetDepth);
    }
}
