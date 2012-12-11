/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
