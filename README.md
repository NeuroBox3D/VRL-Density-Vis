VRL-Density-Vis
===============

## Introduction ##

The density visualization plugin is designed to analyze and visualize density distribution of proteins in neurons. But it can also be used for other types distributions.

![Screenshot](https://github.com/NeuroBox3D/VRL-Density-Vis/blob/master/help/resources/img/overview-01.png)

The following section gives an overview over all provided components and their parameters.

## Components ##

This plugin contains several components that can be combined into a complete workflow that computes and visualizes the density distribution and distances between voxel sets and the specified geometry.

### Compute Density ###

This component divides the specified image stack into voxel sets and computes the average density in each set. Input and output parameters are shown in [Compute Density].

![Compute Density](https://github.com/NeuroBox3D/VRL-Density-Vis/blob/master/help/resources/img/compute-density-01.png)

### Compute Distances ###

This component computes the minimum distances between the center of each voxel set and the specified geometry. Input and output parameters are shown in [Compute Distances].

![Compute Distances](https://github.com/NeuroBox3D/VRL-Density-Vis/blob/master/help/resources/img/compute-distances-01.png)


### Density Visualization ###

This component visualizes the specified density information and the geometry. Input and output parameters are shown in [Density Visualization].

![Density Visualization](https://github.com/NeuroBox3D/VRL-Density-Vis/blob/master/help/resources/img/density-visualization-01.png)

Visualizations can be saved as pixel image via `Right-Click->Save Image`. This is useful if the visualization shall be used in publications.

### Histogram Plotter ###

This component creates a histogram plot of the specified distance data. Input and output parameters are shown in [Histogram Plotter].



**INFO: currently disabled**

![Histogram Plotter](https://github.com/NeuroBox3D/VRL-Density-Vis/blob/master/help/resources/img/distance-histogram-01.png)

The plot can be saved as vector image via `Right-Click->Export`. This is useful if the plot shall be used in publications.

## How To Build The Plugin

### 1. Dependencies

- JDK >= 1.8 (tested with JDK 8-13)
- Internet Connection (other dependencies will be downloaded automatically)
- Optional: IDE with [Gradle](http://www.gradle.org/) support


### 2. Configuration (Optional)

If the plugin shall be installed to a custom destination, specify the path in `build.properties`, e.g.,
    
    # vrl property folder location (plugin destination)
    vrldir=/path/to/.vrl/0.4.4/myvrl
    
Otherwise, the plugin will be installed to the default location (depends on VRL version that is specified in the gradle dependencies).

### 3. Build & Install

#### IDE

To build the project from an IDE do the following:

- open the  [Gradle](http://www.gradle.org/) project
- call the `installVRLPlugin` Gradle task to build and install the plugin
- restart VRL-Studio

#### Command Line

Building the project from the command line is also possible.

Navigate to the project folder and call the `installVRLPlugin` [Gradle](http://www.gradle.org/)
task to build and install the plugin.

##### Bash (Linux/OS X/Cygwin/other Unix-like OS)

    cd Path/To/VRL-Density-Vis
    bash ./gradlew installVRLPlugin
    
##### Windows (CMD)

    cd Path\To\VRL-Density-Vis
    gradlew installVRLPlugin

Finally, restart VRL-Studio
