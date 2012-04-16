package org.idi.model;


public class Task {

    private String name;
    private int remainingPomodori;

    public Task(String taskName, int estimatedPomodori) {

        name = taskName;
        remainingPomodori = estimatedPomodori;
    }

    public String getName() {

        return name;
    }

    public int getRemainingPomodori() {

        return remainingPomodori;
    }

    public Boolean isCompleted() {

        return remainingPomodori == 0;
    }

}
