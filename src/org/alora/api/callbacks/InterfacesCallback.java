package org.alora.api.callbacks;

import org.bot.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 7/4/2017.
 */


public class InterfacesCallback {
    private static long lastClear = System.currentTimeMillis();
    private static boolean print = false;
    public static void add(int id) {
        if(System.currentTimeMillis() - lastClear > 1000) {
            Engine.getServerProvider().getLoadedInterfaces().clear();
            lastClear = System.currentTimeMillis();
        }
        if(!Engine.getServerProvider().getLoadedInterfaces().contains(id)) {
            Engine.getServerProvider().getLoadedInterfaces().add(id);
        if(print) {
            System.out.println("Adding interface: " + id);
        }
        }
    }

}