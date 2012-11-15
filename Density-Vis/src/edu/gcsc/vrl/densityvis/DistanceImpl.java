/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DistanceImpl implements Distance{
    
    private Voxel voxel;
    private double distance;

    public DistanceImpl(Voxel voxel, double distance) {
        this.voxel = voxel;
        this.distance = distance;
    }

    
    /**
     * @return the voxel
     */
    @Override
    public Voxel getVoxel() {
        return voxel;
    }

    /**
     * @return the distance
     */
    @Override
    public double getDistance() {
        return distance;
    } 
}
