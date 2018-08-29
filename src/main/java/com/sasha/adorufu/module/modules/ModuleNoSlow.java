package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.events.ClientInputUpdateEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

/**
 * Created by Sasha on 11/08/2018 at 3:18 PM
 **/
@ModuleInfo(description = "Don't slow down whilst eating.") //todo
public class ModuleNoSlow extends AdorufuModule implements SimpleListener {
    public ModuleNoSlow() {
        super("NoSlow", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }

    @SimpleEventHandler
    public void onSlowDown(ClientInputUpdateEvent e) {
        if (this.isEnabled()) {
            e.getMovementInput().moveForward /= 0.5f;
            e.getMovementInput().moveStrafe /= 0.5f;
        }
    }
}
