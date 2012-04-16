package org.idi.model;


public abstract class Rest {

    protected int remainingTime;
    protected Boolean stopped;

    public int getRemainingTime() {

        return remainingTime;
    }

    public Boolean isStopped() {
    
        return stopped;
    }
}