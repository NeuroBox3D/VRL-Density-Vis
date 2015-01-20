/// package's name
package edu.gcsc.vrl.densityvis;

/// imports
import edu.gcsc.vrl.jfreechart.HistogramData;
import eu.mihosoft.vrl.animation.LinearInterpolation;
import eu.mihosoft.vrl.v3d.Shape3DArray;
import eu.mihosoft.vrl.v3d.VTriangleArray;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.NewImage;
import ij.io.FileSaver;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
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
	 * @brief rescale geometry or dont rescale geometry when converting the density information to Java3D
	 * 
	 * @param density
	 * @param geometry
	 * @param percentage
	 * @param colorZero
	 * @param colorOne
	 * @param transparency
	 * @param rescale
	 * @param factor
	 * @return
	 */
	public static Shape3DArray density2Java3D(
		Density density, VTriangleArray geometry,
		double percentage,
		Color colorZero, Color colorOne,
		boolean transparency,
		boolean rescale,
		double factor) {
		if (rescale) {
			return scaleDensity2Java3D(density, geometry, percentage, colorZero, colorOne, transparency, factor);
		} else {
			return density2Java3D(density, geometry, percentage, colorZero, colorOne, transparency);
		}
	}
	
	/**
	 * @brief delegate to density2Java3D (see above) with a default parameter for the rescaling factor
	 * 
	 * @param density
	 * @param geometry
	 * @param percentage
	 * @param colorZero
	 * @param colorOne
	 * @param transparency
	 * @param rescale
	 * @return 
	 */
	public static Shape3DArray density2Java3D(
		Density density, VTriangleArray geometry,
		double percentage,
		Color colorZero, Color colorOne,
		boolean transparency,
		boolean rescale) {
		if (rescale) {
			return scaleDensity2Java3D(density, geometry, percentage, colorZero, colorOne, transparency, 0.01d);
		} else {
			return density2Java3D(density, geometry, percentage, colorZero, colorOne, transparency);
		}
	}

	/**
	 * Returns a list of <code>Shape3D</code> objects that visualize the
	 * specified scaled density information.
	 *
	 * @param density density information that shall be visualized
	 * @param geometry geometry (e.g., cell body)
	 * @param percentage defines the minimum density threashold (in %)
	 * @param colorZero color that represents <code>density = 0.0</code>
	 * @param colorOne color that represents <code>density = 1.0</code>
	 * @param transparency defines whether to visualize density via
	 * @param factor
	 * transparency (in addition to color)
	 * @return a list of <code>Shape3D</code> objects that visualize the
	 * specified density information
	 */

	public static Shape3DArray scaleDensity2Java3D(
		Density density, VTriangleArray geometry,
		double percentage,
		Color colorZero, Color colorOne,
		boolean transparency,
		double factor) {
		Shape3DArray result = new Shape3DArray();

		float scale = geometry.getScaleFactor();
		Vector3f offset = geometry.getOffset();

		for (VoxelSet v : density.getVoxels()) {

			// only visualize density above threshold
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
			if (! (factor == 1.0)) {
				/// rescale with a given factor
				VisualVoxelSet cubeCreator = new VisualVoxelSet(
					v.getX() * 0.01f, v.getY() * 0.01f, v.getZ() * 0.01f,
					v.getWidth() * 0.01f, v.getHeight() * 0.01f, v.getDepth() * 0.01f,
					offset, scale,
					mat, transparencyValue);
				Shape3D s = cubeCreator.getCubeContainer();
				result.add(s);
			} else {
				TriangleArray vta = geometry.getTriangleArray();
				float[] coords = {0, 0, 0};
				ArrayList<Float> x = new ArrayList<Float>();
				ArrayList<Float> y = new ArrayList<Float>();
				ArrayList<Float> z = new ArrayList<Float>();
				for (int i = 0; i < vta.getVertexCount(); i++) {
					vta.getCoordinates(i, coords);
					x.add(coords[0]);
					y.add(coords[1]);
					z.add(coords[2]);
				}

				/// rescaling factor for voxels
  				float RF = 1.0f / (Collections.max(Arrays.asList(
						Math.abs(Collections.max(x) - Collections.min(x)),
						Math.abs(Collections.max(y) - Collections.min(y)),
						Math.abs(Collections.max(z) - Collections.min(z))
					    )));

				VisualVoxelSet cubeCreator = new VisualVoxelSet(
					v.getX() * RF,  v.getY() * RF, v.getZ() * RF,
					v.getWidth() * RF, v.getHeight() * RF, v.getDepth() * RF,
					offset, scale,
					mat, transparencyValue);
				Shape3D s = cubeCreator.getCubeContainer();
				result.add(s);
			}
		}
		return result;
	}
	
	/**
	 * Returns a list of <code>Shape3D</code> objects that visualize the
	 * specified density information.
	 *
	 * @param density density information that shall be visualized
	 * @param geometry geometry (e.g., cell body)
	 * @param percentage defines the minimum density threashold (in %)
	 * @param colorZero color that represents <code>density = 0.0</code>
	 * @param colorOne color that represents <code>density = 1.0</code>
	 * @param transparency defines whether to visualize density via
	 * transparency (in addition to color)
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

	/**
	 * Visualizes the dinstances between voxel sets and the membrane as
	 * histogram.
	 *
	 * @param distances dinstances to visualize
	 * @param numBinds number of bins
	 * @return histogramdata that can be visualized via
	 * <code>HistogramPlotter</code> or directly via <code>JFreeChart</code>
	 */
	public static HistogramData distance2Histogram(Collection<Distance> distances, int numBinds) {
		HistogramData data = new HistogramData();
		double[] values = new double[distances.size()];

		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);

		int i = 0;
		for (Distance d : distances) {
			values[i] = d.getDistance();
			i++;
		}

		dataset.addSeries("Histogram", values, numBinds);

		data.setPlotTitle("Distances");
		data.setxAxisLabel("Distance to Membrane");
		data.setyAxisLabel("Frequency");
		data.setPlotOrientation(PlotOrientation.VERTICAL);

		data.setData(dataset);

		return data;
	}
}
