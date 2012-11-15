/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class DensityUtil {

    public static Density computeDensity(Cube cube,
            int voxelWidth, int voxelHeight, int voxelDepth) {
        return new DensityImpl(cube, voxelWidth, voxelHeight, voxelDepth);
    }
}
