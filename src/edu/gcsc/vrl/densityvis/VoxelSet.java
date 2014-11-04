/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 * The VoxelSet class defines a subset (cuboid) of voxels in
 * <code>ImageVoxels</code> objects.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface VoxelSet {

    /**
     * @return the depth (in image coordinates)
     */
    int getDepth();

    /**
     * @return the height (in image coordinates)
     */
    int getHeight();

    /**
     * @return the value (e.g. average voxel density)
     */
    double getValue();

    /**
     * @return the width (in image coordinates)
     */
    int getWidth();

    /**
     * @return the x (in image coordinates)
     */
    int getX();

    /**
     * @return the y (in image coordinates)
     */
    int getY();

    /**
     * @return the z (in image coordinates)
     */
    int getZ();
    
}
