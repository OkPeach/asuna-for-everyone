/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientItemSpawnEvent;
import com.sasha.asuna.mod.events.playerclient.PlayerBlockBreakEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaRenderableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.misc.AsunaRender;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

import java.util.HashMap;
import java.util.LinkedHashMap;

@FeatureInfo(description = "Highlight blocks that might've not been mined on the server's side.")
public class GhostBlockWarningFeature extends AbstractAsunaTogglableFeature
        implements SimpleListener, IAsunaRenderableFeature {

    /**
     * dont ask why this variable is named this it was like this already HUSH PLS
     */
    private static LinkedHashMap<Coordinate, Boolean> coordMap = new LinkedHashMap<>();

    public GhostBlockWarningFeature() {
        super("GhostBlockWarning", AsunaCategory.RENDER);
    }

    private static boolean between(int i, int coord, double min, double max) {
        if (coord < 0) {
            coord--;
        }
        return i >= (coord - min) && i <= (coord + max);
    }

    @Override
    public void onRender() {
        if (!this.isEnabled()) {
            return;
        }
        for (HashMap.Entry<Coordinate, Boolean> entry : coordMap.entrySet()) {
            if (!entry.getValue()) {
                AsunaRender.ghostBlock(entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ(), 1.0f, 0.0f, 0.0f, 0.5f);
            }
        }
    }

    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockBreakEvent e) {
        if (e.getBlock().canHarvestBlock(AsunaMod.minecraft.world, e.getBlockPos(), AsunaMod.minecraft.player)) {
            coordMap.put(new Coordinate(e.getBlockPos().x, e.getBlockPos().y, e.getBlockPos().z, AsunaMod.minecraft.player.dimension), false);
        }
    }

    @SimpleEventHandler
    public void onItemDrop(ClientItemSpawnEvent e) {
        int[] coords = e.getCoordinate();
        for (HashMap.Entry<Coordinate, Boolean> fugg : coordMap.entrySet()) {
            if (!fugg.getValue() && between(fugg.getKey().getX(), coords[0], 0.25d, 0.25d) && between(fugg.getKey().getY(), coords[1], 0.25d, 0.25d) && between(fugg.getKey().getZ(), coords[2], 0.25d, 0.25d)) {
                coordMap.put(fugg.getKey(), true);
            }
        }
    }
}

class Coordinate {
    private int x, y, z, dim;

    public Coordinate(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getDim() {
        return dim;
    }
}
