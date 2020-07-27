/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015–2020 G-CSC, Uni Frankfurt
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. A suitable
 * notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Please cite the publication(s) listed below.
 *
 * Publications:
 *
 * M. Hoffer, C. Poliwoda, & G. Wittum. (2013). Visual reflection library:
 * a framework for declarative GUI programming on the Java platform.
 * Computing and Visualization in Science, 2013, 16(4),
 * 181–192. http://doi.org/10.1007/s00791-014-0230-y
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
