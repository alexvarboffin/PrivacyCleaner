package com.walhalla.privacycleaner;

import java.util.Timer;
import java.util.TimerTask;

public class CoolTimer extends Timer {
    public CoolTimer() {
    }

    private boolean hasStarted = false;

    @Override
    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        super.scheduleAtFixedRate(task, delay, period);
        this.hasStarted = true;
    }

    public boolean hasRunStarted() {
        return this.hasStarted;
    }
}
