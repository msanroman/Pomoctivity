package org.idi.pomoctivity;

import org.idi.model.Task;

public class FinishedTasksActivity extends LlistarTasquesActivity {

    public void fillData() {

    	tasks = database.getAllFinishedTasks();
    	taskAdapter.clear();
    	if (getMode() == ORDER_MODE)
    		taskAdapter.setOrderPosition(getOrderPosition());
    	else taskAdapter.setOrderPosition(-1);
    	for(Task task: tasks)
    		taskAdapter.add(task);
    	taskAdapter.notifyDataSetChanged();
    }
}
