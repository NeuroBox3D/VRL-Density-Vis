/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class DensityVis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

//        File imageIn = new File("/home/miho/Dropbox/NeuGen/neugen/1/Bic1-crop.tif");

        File imageIn = new File("/Users/miho/Dropbox/NeuGen/neugen/1/Bic1-crop.tif");
        File geometryIn = new File("/Users/miho/Dropbox/NeuGen/neugen/1/Bic1.txt");

        File imageOut = new File("out.tiff");

        ImageVoxels cube = ImageVoxels.load(imageIn);

        Density density = DensityUtil.computeDensity(cube, 10, 10, 5);
//        VisUtil.density2ImageStack(imageOut, cube, density);
        
        TxT2Geometry loader = new TxT2Geometry();
        
        VTriangleArray geometry = loader.loadAsVTriangleArray(geometryIn);
        Point3f[] points = GeometryUtil.vTriangleArray2PointArray(geometry);
        
        DistanceUtil.computeDistance(density, points, 40);

    }
}
