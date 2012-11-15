/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.v3d.VTriangleArray;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@ObjectInfo(serialize=false, serializeParam=false)
public class DensityResult {
    private transient VTriangleArray geometry;
    private transient Density density;

    public DensityResult(Density density, VTriangleArray geometry) {
        this.geometry = geometry;
        this.density = density;
    }

    public Density getDensity() {
        return density;
    }

    public VTriangleArray getGeometry() {
        return geometry;
    }
    
    
}
