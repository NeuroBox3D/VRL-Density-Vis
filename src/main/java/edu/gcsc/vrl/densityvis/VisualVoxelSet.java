
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

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Visual representation of a voxel set. 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VisualVoxelSet {

    private float depth;
    private float width;
    private float height;
    private Appearance cubeAppearance;
    private Shape3D cubeContainer;

    /**
     * Constructor.
     * @param x x (in image coordinates)
     * @param y y (in image coordinates)
     * @param z z (in image coordinates)
     * @param width width (in image coordinates)
     * @param height height (in image coordinates)
     * @param depth depth (in image coordinates)
     * @param offset offset (in image coordinates)
     * @param scale scale factor
     * @param mat Java3D material
     * @param transparency transparency (range: [0.0, 1.0])
     */
    public VisualVoxelSet(float x, float y, float z,
            float width, float height, float depth,
            Vector3f offset, float scale,
            Material mat, float transparency) {
        this.depth = depth;
        this.width = width;
        this.height = height;

        this.cubeAppearance = new Appearance();

        this.cubeAppearance.setMaterial(mat);
        TransparencyAttributes ta = new TransparencyAttributes(
                TransparencyAttributes.BLEND_ZERO, transparency);
        this.cubeAppearance.setTransparencyAttributes(ta);
        this.cubeContainer = new Shape3D();
        this.cubeContainer.setAppearance(cubeAppearance);
        this.cubeContainer.removeGeometry(0);

        createGeometry(offset, scale, x, y, z);

    }

    /**
     * Returns the cube container.
     * @return  the cube container
     */
    public Shape3D getCubeContainer() {
        return this.cubeContainer;
    }

    /**
     * Creates the geometry.
     * @param offset offset (in image coordinates)
     * @param scale scale factor
     * @param x x (in image coordinates)
     * @param y y (in image coordinates)
     * @param z z (in image coordinates)
     */
    private void createGeometry(
            Vector3f offset, float scale, float x, float y, float z) {
        createGeometry(cubeContainer, offset, scale, x, y, z);
    }

    /**
     * Creates the geometry.
     * @param container <code>Shape3D</code> container
     * @param offset offset (in image coordinates)
     * @param scale scale factor
     * @param x x (in image coordinates)
     * @param y y (in image coordinates)
     * @param z z (in image coordinates)
     */
    private void createGeometry(
            Shape3D container, Vector3f offset,
            float scale, float x, float y, float z) {

        x -= offset.x;
        y -= offset.y;
        z -= offset.z;

        x *= scale;
        y *= scale;
        z *= scale;

        Point3f p1 = new Point3f(x, y, z);
        Point3f p2 = new Point3f(x + width * scale, y, z);
        Point3f p3 = new Point3f(x + width * scale, y + height * scale, z);
        Point3f p4 = new Point3f(x, y + height * scale, z);
        Point3f p5 = new Point3f(x, y, z + depth * scale);
        Point3f p6 = new Point3f(x + width * scale, y, z + depth * scale);
        Point3f p7 = new Point3f(x + width * scale, y + height * scale, z + depth * scale);
        Point3f p8 = new Point3f(x, y + height * scale, z + depth * scale);

        container.addGeometry(createQuadArrayFromPoints(p1, p4, p3, p2)); // bottom
        // face
        container.addGeometry(createQuadArrayFromPoints(p5, p6, p7, p8)); // up
        // face
        container.addGeometry(createQuadArrayFromPoints(p1, p2, p6, p5)); // front
        // face
        container.addGeometry(createQuadArrayFromPoints(p2, p3, p7, p6)); // right
        // face
        container.addGeometry(createQuadArrayFromPoints(p3, p4, p8, p7)); // back
        // face
        container.addGeometry(createQuadArrayFromPoints(p4, p1, p5, p8)); // left
        // face
    }

    /**
     * Create face from points.
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return face as quadarray
     */
    private QuadArray createQuadArrayFromPoints(Point3f p1, Point3f p2, Point3f p3, Point3f p4) {
        QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.NORMALS);
        qa.setCoordinate(0, p1);
        qa.setCoordinate(1, p2);
        qa.setCoordinate(2, p3);
        qa.setCoordinate(3, p4);
        return qa;
    }
}
