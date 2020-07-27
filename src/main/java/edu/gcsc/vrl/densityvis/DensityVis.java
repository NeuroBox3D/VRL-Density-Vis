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

import eu.mihosoft.vrl.v3d.TxT2Geometry;
import eu.mihosoft.vrl.v3d.VTriangleArray;
import java.io.File;
import java.io.IOException;
import javax.vecmath.Point3f;

/**
 * Main class for testing purposes (runs without user interface).
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
//public class DensityVis {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws IOException {
//
////        File imageIn = new File("/home/miho/Dropbox/NeuGen/neugen/1/Bic1-crop.tif");
//
//        File imageIn = new File("/Users/miho/Dropbox/NeuGen/neugen/1/Bic1-crop.tif");
//        File geometryIn = new File("/Users/miho/Dropbox/NeuGen/neugen/1/Bic1.txt");
//
//        File imageOut = new File("out.tiff");
//
//        ImageVoxels cube = ImageVoxels.load(imageIn);
//
//        Density density = DensityUtil.computeDensity(cube, 10, 10, 5);
////        VisUtil.density2ImageStack(imageOut, cube, density);
//        
//        TxT2Geometry loader = new TxT2Geometry();
//        
//        VTriangleArray geometry = loader.loadAsVTriangleArray(geometryIn);
//        Point3f[] points = GeometryUtil.vTriangleArray2PointArray(geometry);
//        
//        DistanceUtil.computeDistance(density, points, 40);
//
//    }
//}
