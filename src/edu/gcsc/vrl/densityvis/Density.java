/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import java.util.List;

/**
 * Density information interface.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Density {
    /**
     * Returns the voxel subsets that contain average density of the contained
     * voxels.
     * @return voxel subsets
     */
    public List<? extends VoxelSet> getVoxels();
}
