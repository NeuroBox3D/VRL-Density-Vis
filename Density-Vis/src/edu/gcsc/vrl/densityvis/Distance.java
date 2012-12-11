/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import javax.vecmath.Point3f;

/**
 * Represents the distance between a voxel set and a geometry point.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Distance {
    /**
     * Returns the voxel set.
     * @return voxel
     */
    public VoxelSet getVoxel();
    
    /**
     * Returns the geometry point.
     * @return point
     */
    public Point3f getPoint();
    
    /**
     * Returns the distance.
     * @return distance
     */
    public double getDistance();
}
