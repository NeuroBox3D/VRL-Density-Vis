/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import ij.process.StackConverter;
import java.io.File;

/**
 * Image voxels contains the complete voxel data of an image stack (.tif). 
 * After loading an image stack it ensures that the voxel data is converted
 * to 8-bit gray scale.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class ImageVoxels {

    private int[][][] data;
    private int width;
    private int height;
    private int depth;
    
    private ImageStack stack;

    /**
     * Constructor.
     * @param stack image stack
     * @param data voxel data
     * @param width width (in image coordinates)
     * @param height height (in image coordinates)
     * @param depth depth (in image coordinates)
     */
    private ImageVoxels(
            ImageStack stack, int[][][] data, int width, int height, int depth) {
        this.stack = stack;
        this.data = data;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * Loads an image stack from file.
     * @param image image file
     * @return an <code>ImageVoxels</code> object from file
     */
    public static ImageVoxels load(File image) {

        ImagePlus imp = ij.IJ.openImage(image.getAbsolutePath());
        int numSlices = imp.getStackSize();
        int[][][] stackData = new int[numSlices][][];

        if (numSlices > 1) {
            StackConverter sc = new StackConverter(imp);
            sc.convertToGray8();
        } else {
            ImageConverter im = new ImageConverter(imp);
            im.convertToGray8();
        }

        ImageStack stack = imp.getStack();

        int width = 0;
        int height = 0;

        System.out.println(">> stack-size: " + stack.getSize());
        System.out.println(">> type: " + imp.getFileInfo());

        for (int i = 0; i < stack.getSize(); i++) {
            ImageProcessor ip = stack.getProcessor(i + 1);

            width = ip.getWidth();
            height = ip.getHeight();

            int[][] pixelMatrix = ip.getIntArray();

            stackData[i] = pixelMatrix;

            System.out.println(" --> slice:" + i + " = w: " + width + ", h: " + height);
        }

        return new ImageVoxels(stack, stackData, width, height, numSlices);
    }

    /**
     * Returns the voxel data.
     * @return voxel data
     */
    public int[][][] getData() {
        return data;
    }
    
    /**
     * Returns the value of the specified voxel.
     * @param x x (in image coordinates)
     * @param y y (in image coordinates)
     * @param z z (in image coordinates)
     * @return value of the specified voxel
     */
    public double getVoxel(int x, int y, int z) {
        return stack.getVoxel(x, y, z);
    }

    /**
     * @return the width (in image coordinates)
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height (in image coordinates)
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the depth (in image coordinates)
     */
    public int getDepth() {
        return depth;
    }
}
