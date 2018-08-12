package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.misc.XdolfRender;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 10/08/2018 at 8:55 AM
 **/
@ModuleInfo(description = "Draws lines to nearby players.")
public class ModuleTracers extends XdolfModule {
    public static int i;
    public ModuleTracers() {
        super("Tracers", XdolfCategory.RENDER, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable(){
        i = 0;
    }
    @Override
    public void onRender(){
        if (this.isEnabled()) {
            i = XdolfRender.tracers();
        }
    }

    @Override
    public void onTick() {

    }
}