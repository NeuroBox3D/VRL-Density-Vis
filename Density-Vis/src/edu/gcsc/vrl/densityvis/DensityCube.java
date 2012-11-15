package edu.gcsc.vrl.densityvis;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class DensityCube {

    private float depth;
    private float width;
    private float height;
    private Appearance cubeAppearance;
    private Shape3D cubeContainer;

    public DensityCube(float x, float y, float z,
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

    public Shape3D getCubeContainer() {
        return this.cubeContainer;
    }

    private void createGeometry(Vector3f offset, float scale, float x, float y, float z) {
        createGeometry(cubeContainer, offset, scale, x, y, z);
    }

    private void createGeometry(Shape3D container, Vector3f offset, float scale, float x, float y, float z) {

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

    private QuadArray createQuadArrayFromPoints(Point3f p1, Point3f p2, Point3f p3, Point3f p4) {
        QuadArray qa = new QuadArray(4, QuadArray.COORDINATES | QuadArray.NORMALS);
        qa.setCoordinate(0, p1);
        qa.setCoordinate(1, p2);
        qa.setCoordinate(2, p3);
        qa.setCoordinate(3, p4);
        return qa;
    }
}
