/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Voxel {

    /**
     * @return the depth
     */
    int getDepth();

    /**
     * @return the height
     */
    int getHeight();

    /**
     * @return the value
     */
    double getValue();

    /**
     * @return the width
     */
    int getWidth();

    /**
     * @return the x
     */
    int getX();

    /**
     * @return the y
     */
    int getY();

    /**
     * @return the z
     */
    int getZ();
    
}
