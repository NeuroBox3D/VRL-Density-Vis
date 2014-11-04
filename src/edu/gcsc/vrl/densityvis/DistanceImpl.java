/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import javax.vecmath.Point3f;

/**
 * Internal implementation of the density interface. This class must not be
 * exported as public API.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DistanceImpl implements Distance{
    
    private VoxelSet voxel;
    private Point3f point;
    private double distance;

    /**
     * Constructor.
     * @param voxel voxel set
     * @param distance distance
     */
    public DistanceImpl(VoxelSet voxel, Point3f p, double distance) {
        this.voxel = voxel;
        this.point = p;
        this.distance = distance;
    }

    
    /**
     * @return the voxel
     */
    @Override
    public VoxelSet getVoxel() {
        return voxel;
    }

    /**
     * @return the distance
     */
    @Override
    public double getDistance() {
        return distance;
    } 

    @Override
    public Point3f getPoint() {
        return point;
    }
}
