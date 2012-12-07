/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.densityvis;

import eu.mihosoft.vrl.io.VJarUtil;
import eu.mihosoft.vrl.io.VersionInfo;
import eu.mihosoft.vrl.lang.visual.CompletionUtil;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Configurator extends VPluginConfigurator {

    public Configurator() {
        //specify the plugin name and version
        setIdentifier(new PluginIdentifier("Density-Vis-Plugin", "0.1"));

        // optionally allow other plugins to use the api of this plugin
        // you can specify packages that shall be
        // exported by using the exportPackage() method:
        // 
        exportPackage("edu.gcsc.vrl.densityvis");
//        disableAccessControl(true);

        // describe the plugin
        setDescription("Density Visualization Plugin");

        // copyright info
//        setCopyrightInfo("Sample-Plugin",
//                "(c) Your Name",
//                "www.you.com", "License Name", "License Text...");

        // specify dependencies
        // addDependency(new PluginDependency("VRL", "0.4.0", "0.4.0"));
        
        addDependency(new PluginDependency(
                "VRL-JFreeChart", "0.2.4", VersionInfo.UNDEFINED));

    }

    @Override
    public void register(PluginAPI api) {

        // register plugin with canvas
        if (api instanceof VPluginAPI) {
            VPluginAPI vapi = (VPluginAPI) api;

            // Register visual components:
            //
            // Here you can add additional components,
            // type representations, styles etc.
            //
            // ** NOTE **
            //
            // To ensure compatibility with future versions of VRL,
            // you should only use the vapi or api object for registration.
            // If you directly use the canvas or its properties, please make
            // sure that you specify the VRL versions you are compatible with
            // in the constructor of this plugin configurator because the
            // internal api is likely to change.
            //
            // examples:
            //
            // vapi.addComponent(MyComponent.class);
            // vapi.addTypeRepresentation(MyType.class);

            vapi.addTypeRepresentation(VCanvas3DType.class);
        }
    }

    @Override
    public void unregister(PluginAPI api) {
        // nothing to unregister
    }

    @Override
    public void init(InitPluginAPI iApi) {
        // nothing to init

//        CompletionUtil.registerClassesFromJar(
//                VJarUtil.getClassLocation(Configurator.class));
    }
}
