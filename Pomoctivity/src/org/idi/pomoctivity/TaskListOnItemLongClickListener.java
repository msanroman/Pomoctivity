package org.idi.pomoctivity;

import org.idi.databases.task.TaskNotFoundException;
import org.idi.model.Task;

import org.idi.pomoctivity.LlistarTasquesActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class TaskListOnItemLongClickListener implements
		OnItemLongClickListener {

    protected CharSequence[] singleChoiceItems = { "Començar Pomodoro", "Modificar", "Esborrar", "Finalitzar Tasca", "Order tasks" };
	private LlistarTasquesActivity activityList;

	TaskListOnItemLongClickListener(LlistarTasquesActivity llistarTasquesActivity) {
		activityList = llistarTasquesActivity;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
	        long id) {

	    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
	    Task task;
		try {
			task = activityList.database.getTaskWithId(id);
		} catch (TaskNotFoundException e) {
			task = null;
			e.printStackTrace();
		}
	    Bundle bundle = new Bundle();
	    bundle.putCharSequence("name", task.getName());
	    bundle.putInt("remainingPomodori", task.getRemainingPomodori());
	    
	    dialogBuilder.setTitle("What do you wanna do with this task?");
	    dialogBuilder.setSingleChoiceItems(this.singleChoiceItems, 0, new OnClickListener() {
	        
	        public void onClick(DialogInterface dialog, int which) {
	        	if (which == 4) {

	        		activityList.showOrderKeysForElement(position);
	        	}
	        		
	        	dialog.dismiss();
	        }
	    });
	    
	    AlertDialog dialog = dialogBuilder.create();
	    dialog.show();
	    
	    return false;
	}
}