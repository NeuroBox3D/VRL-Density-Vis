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

import eu.mihosoft.vrl.v3d.VTriangleArray;
import javax.vecmath.Point3f;

/**
 * Geometry util class that allows to perform tasks such as
 * convert triangle arrays to point arrays.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class GeometryUtil {

    /**
     * Converts a triangle arrays to a point array.
     * @param array array to convert
     * @return triangle array as point array
     */
    public static Point3f[] vTriangleArray2PointArray(VTriangleArray array) {
        Point3f[] result = new Point3f[array.size() * 3];
        
        double scale = 1/array.getScaleFactor();

        for (int i = 0; i < result.length; i += 3) {
            result[i] = new Point3f(array.get(i / 3).getNodeOne().getLocation());
            result[i + 1] = new Point3f(array.get(i / 3).getNodeTwo().getLocation());
            result[i + 2] = new Point3f(array.get(i / 3).getNodeThree().getLocation());

            result[i].x *= scale;
            result[i].x += array.getOffset().x;
            result[i].y *= scale;
            result[i].y += array.getOffset().y;
            result[i].z *= scale;
            result[i].z += array.getOffset().z;

            result[i + 1].x *= scale;
            result[i + 1].x += array.getOffset().x;
            result[i + 1].y *= scale;
            result[i + 1].y += array.getOffset().y;
            result[i + 1].z *= scale;
            result[i + 1].z += array.getOffset().z;

            result[i + 2].x *= scale;
            result[i + 2].x += array.getOffset().x;
            result[i + 2].y *= scale;
            result[i + 2].y += array.getOffset().y;
            result[i + 2].z *= scale;
            result[i + 2].z += array.getOffset().z;
        }

        return result;
    }
}
