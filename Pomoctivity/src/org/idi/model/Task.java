package org.idi.model;


public class Task {

	private int id;
    private String name;
    private int remainingPomodori;
    private int order;
    
    public Task(int id, String taskName, int estimatedPomodori, int order) {

    	this.id = id;
        name = taskName;
        remainingPomodori = estimatedPomodori;
        this.order = order;
    }

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

	public void increaseOrder() {

		++order;
	}

	public void decreaseOrder() {
		
		--order;
	}
	
	public int getId() {
		
		return this.id;
	}

	public int getOrder() {

		return order;
	}

}
