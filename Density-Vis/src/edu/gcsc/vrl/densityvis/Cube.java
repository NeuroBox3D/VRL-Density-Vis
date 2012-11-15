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
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Cube {

    private int[][][] data;
    private int width;
    private int height;
    private int depth;
    
    private ImageStack stack;

    public Cube(ImageStack stack, int[][][] data, int width, int height, int depth) {
        this.stack = stack;
        this.data = data;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public static Cube load(File image) {

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

        return new Cube(stack, stackData, width, height, numSlices);
    }

    public int[][][] getData() {
        return data;
    }
    
    public double getVoxel(int x, int y, int z) {
        return stack.getVoxel(x, y, z);
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }
}
