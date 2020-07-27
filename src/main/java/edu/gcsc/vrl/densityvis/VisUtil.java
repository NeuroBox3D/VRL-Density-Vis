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

//import edu.gcsc.vrl.jfreechart.HistogramData;
import eu.mihosoft.vrl.animation.LinearInterpolation;
import eu.mihosoft.vrl.v3d.Shape3DArray;
import eu.mihosoft.vrl.v3d.VTriangleArray;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.NewImage;
import ij.io.FileSaver;
import java.awt.Color;
import java.io.File;
import java.util.Collection;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 * Utility class that allows density/distance visualization in 2D and 3D.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VisUtil {

    /**
     * Returns a list of
     * <code>Shape3D</code> objects that visualize the specified density
     * information.
     *
     * @param density density information that shall be visualized
     * @param geometry geometry (e.g., cell body)
     * @param percentage defines the minimum density threashold (in %)
     * @param colorZero color that represents <code>density = 0.0</code>
     * @param colorOne color that represents <code>density = 1.0</code>
     * @param transparency defines whether to visualize density via transparency
     * (in addition to color)
     * @return a list of <code>Shape3D</code> objects that visualize the
     * specified density information
     */
    public static Shape3DArray density2Java3D(
            Density density, VTriangleArray geometry,
            double percentage,
            Color colorZero, Color colorOne,
            boolean transparency) {
        Shape3DArray result = new Shape3DArray();

        float scale = geometry.getScaleFactor();
        Vector3f offset = geometry.getOffset();

        for (VoxelSet v : density.getVoxels()) {

            // only visualize density above threashold
            if (v.getValue() < percentage / 100.f) {
                continue;
            }

            // color interpolation
            LinearInterpolation redInt = new LinearInterpolation(
                    colorZero.getRed() / 255.f, colorOne.getRed() / 255.f);
            LinearInterpolation greenInt = new LinearInterpolation(
                    colorZero.getGreen() / 255.f, colorOne.getGreen() / 255.f);
            LinearInterpolation blueInt = new LinearInterpolation(
                    colorZero.getBlue() / 255.f, colorOne.getBlue() / 255.f);
            LinearInterpolation alphaInt = new LinearInterpolation(
                    colorZero.getAlpha() / 255.f, colorOne.getAlpha() / 255.f);
            redInt.step(v.getValue());
            greenInt.step(v.getValue());
            blueInt.step(v.getValue());
            alphaInt.step(v.getValue());
            Color interpolatedColor = new Color(
                    (float) redInt.getValue(),
                    (float) greenInt.getValue(),
                    (float) blueInt.getValue(),
                    (float) alphaInt.getValue());

            // create a material for the density cube
            Material mat = new Material();
            mat.setCapability(Material.ALLOW_COMPONENT_WRITE);
            mat.setEmissiveColor(color2Color3f(interpolatedColor));
            mat.setDiffuseColor(color2Color3f(interpolatedColor));
            mat.setAmbientColor(color2Color3f(interpolatedColor));
            mat.setSpecularColor(color2Color3f(interpolatedColor));
            mat.setShininess(20.0f);

            float transparencyValue = 1.f - interpolatedColor.getAlpha() / 255.f;

            if (!transparency) {
                transparencyValue = 0.f;
            }

            // create the geometry that visualizes a voxel set
            VisualVoxelSet cubeCreator = new VisualVoxelSet(
                    v.getX(), v.getY(), v.getZ(),
                    v.getWidth(), v.getHeight(), v.getDepth(),
                    offset, scale,
                    mat, transparencyValue);

            Shape3D s = cubeCreator.getCubeContainer();

            result.add(s);
        }

        return result;
    }

    /**
     * Converts color from AWT to Java3D.
     *
     * @param c color to convert
     * @return color as Java3D color
     */
    private static Color3f color2Color3f(Color c) {
        return new Color3f(c.getRed() / 255f,
                c.getGreen() / 255f,
                c.getBlue() / 255f);
    }

    /**
     * Renders the specified density information as image stack (.tif).
     *
     * @param out the output file (TIF)
     * @param voxels the image voxels of the source image
     * @param density density information that shall be visualized
     */
    public static void density2ImageStack(File out, ImageVoxels voxels, Density density) {

        ImagePlus ip = NewImage.createByteImage("Density Stack",
                voxels.getWidth(), voxels.getHeight(), voxels.getDepth(), NewImage.FILL_BLACK);

        ImageStack is = ip.getStack();

        for (VoxelSet v : density.getVoxels()) {

            int value = (int) (256 * v.getValue());

            for (int z = v.getZ(); z < v.getZ() + v.getDepth(); z++) {
                for (int y = v.getY(); y < v.getY() + v.getHeight(); y++) {
                    for (int x = v.getX(); x < v.getX() + v.getWidth(); x++) {

                        is.setVoxel(x, y, z, value);
                    }
                }
            }
        }

        FileSaver saver = new FileSaver(ip);

        saver.saveAsTiffStack(out.getAbsolutePath());
    }

//    /**
//     * Visualizes the dinstances between voxel sets and the membrane as histogram.
//     *
//     * @param distances dinstances to visualize
//     * @param numBinds number of bins
//     * @return histogramdata that can be visualized via
//     * <code>HistogramPlotter</code> or directly via <code>JFreeChart</code>
//     */
//    public static HistogramData distance2Histogram(Collection<Distance> distances, int numBinds) {
//        HistogramData data = new HistogramData();
//        double[] values = new double[distances.size()];
//
//        HistogramDataset dataset = new HistogramDataset();
//        dataset.setType(HistogramType.FREQUENCY);
//
//        int i = 0;
//        for (Distance d : distances) {
//            values[i] = d.getDistance();
//            i++;
//        }
//
//        dataset.addSeries("Histogram", values, numBinds);
//
//        data.setPlotTitle("Distances");
//        data.setxAxisLabel("Distance to Membrane");
//        data.setyAxisLabel("Frequency");
//        data.setPlotOrientation(PlotOrientation.VERTICAL);
//        
//        data.setData(dataset);
//
//        return data;
//    }
}
