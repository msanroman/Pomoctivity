package org.idi.model;

public class Pomodoro {

    private static final int REMAINING_MINUTES = 25;
    private boolean stopped;
    private boolean started;

    public Pomodoro() {

        stopped = true;
        started = false;
    }

    public int getRemainingMinutes() {

        return REMAINING_MINUTES;
    }

    public Boolean isStopped() {

        return stopped;
    }

    public Boolean isStarted() {

        return started;
    }

    public void start() {

        started = true;
        stopped = false;
    }

}
