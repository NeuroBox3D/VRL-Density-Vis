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

import eu.mihosoft.vrl.io.VJarUtil;
import eu.mihosoft.vrl.io.VersionInfo;
import eu.mihosoft.vrl.lang.visual.CompletionUtil;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;
import java.io.File;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Configurator extends VPluginConfigurator {
    
    private File templateProjectSrc;

    public Configurator() {
        //specify the plugin name and version
        setIdentifier(new PluginIdentifier("Density-Vis-Plugin", "0.3"));

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
        addDependency(new PluginDependency("VRL", "0.4.2.7", VersionInfo.UNDEFINED));

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

        CompletionUtil.registerClassesFromJar(
                VJarUtil.getClassLocation(Configurator.class));
        
//        initTemplateProject(iApi);
    }
    
//    @Override
//    public void install(InitPluginAPI iApi) {
//        // ensure template projects are updated
//        new File(iApi.getResourceFolder(), "template-01.vrlp").delete();
//    }
//
//    private void saveProjectTemplate() {
//        InputStream in = Configurator.class.getResourceAsStream(
//                "/edu/gcsc/vrl/densityvis/resources/projects/template-01.vrlp");
//        try {
//            IOUtil.saveStreamToFile(in, templateProjectSrc);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(VRLPlugin.class.getName()).
//                    log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(VRLPlugin.class.getName()).
//                    log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void initTemplateProject(InitPluginAPI iApi) {
//        templateProjectSrc = new File(iApi.getResourceFolder(), "template-01.vrlp");
//
//        if (!templateProjectSrc.exists()) {
//            saveProjectTemplate();
//        }
//
//        iApi.addProjectTemplate(new ProjectTemplate() {
//
//            @Override
//            public String getName() {
//                return "Density-Vis - Template 1";
//            }
//
//            @Override
//            public File getSource() {
//                return templateProjectSrc;
//            }
//
//            @Override
//            public String getDescription() {
//                return "Density-Vis Template Project 1";
//            }
//
//            @Override
//            public BufferedImage getIcon() {
//                return null;
//            }
//        });
//    }
}
