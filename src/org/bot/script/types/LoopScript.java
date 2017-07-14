package org.bot.script.types;

import org.bot.Engine;

/**
 * Created by Ethan on 7/14/2017.
 */
public abstract class LoopScript {

    private final long startTime = System.currentTimeMillis();


    public abstract void onStart();

    public abstract int operate();

    public abstract void onStop();

    public final void stop() {
        Engine.getScriptHandler().stop();
    }

    public long getRuntime() {
        return System.currentTimeMillis() - startTime;
    }

}