/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface WritableVoxel extends Voxel{

    /**
     * @param depth the depth to set
     */
    void setDepth(int depth);

    /**
     * @param height the height to set
     */
    void setHeight(int height);

    /**
     * @param value the value to set
     */
    void setValue(double value);

    /**
     * @param width the width to set
     */
    void setWidth(int width);

    /**
     * @param x the x to set
     */
    void setX(int x);

    /**
     * @param y the y to set
     */
    void setY(int y);

    /**
     * @param z the z to set
     */
    void setZ(int z);
    
}
