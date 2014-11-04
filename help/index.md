CSS:	../../VRL/help/resources/css/vrl-documentation.css
Title:	Density-Visualization Plugin
Author:	Michael Hoffer

# Density-Visualization Plugin #

## Introduction ##

The density visualization plugin is designed to analyze and visualize density distribution of proteins in neurons.

![Screenshot][]

[Screenshot]: resources/img/overview-01.png width=600px

The following section gives an overview over all provided components and their parameters.

## Components ##

This plugin contains several components that can be combined into a complete workflow that computes and visualizes the density distribution and distances between voxel sets and the specified geometry.

### Compute Density ###

This component divides the specified image stack into voxel sets and computes the average density in each set. Input and output parameters are shown in [Compute Density].

![Compute Density][]

[Compute Density]: resources/img/compute-density-01.png width=600px

### Compute Distances ###

This component computes the minimum distances between the center of each voxel set and the specified geometry. Input and output parameters are shown in [Compute Distances].

![Compute Distances][]

[Compute Distances]: resources/img/compute-distances-01.png width=600px


### Density Visualization ###

This component visualizes the specified density information and the geometry. Input and output parameters are shown in [Density Visualization].

![Density Visualization][]

[Density Visualization]: resources/img/density-visualization-01.png width=600px

Visualizations can be saved as pixel image via `Right-Click->Save Image`. This is useful if the visualization shall be used in publications.

### Histogram Plotter ###

This component creates a histogram plot of the specified distance data. Input and output parameters are shown in [Histogram Plotter].

![Histogram Plotter][]

[Histogram Plotter]: resources/img/distance-histogram-01.png width=600px

The plot can be saved as vector image via `Right-Click->Export`. This is useful if the plot shall be used in publications.

## Further Reading ##

-

