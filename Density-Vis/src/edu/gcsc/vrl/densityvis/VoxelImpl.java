/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VoxelImpl implements WritableVoxel {
    private int x;
    private int y;
    private int z;
    private int width;
    private int height;
    private int depth;
    private double value;

    public VoxelImpl() {
    }

    public VoxelImpl(int x, int y, int z, int width, int height, int depth, double value) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.value = value;
    }

    /**
     * @return the x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    @Override
    public int getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    @Override
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * @return the width
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the depth
     */
    @Override
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return the value
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    @Override
    public void setValue(double value) {
        this.value = value;
    }
    
    
}
