package org.idi.listeners;

import org.idi.databases.task.TaskNotFoundException;
import org.idi.model.Task;

import org.idi.pomoctivity.LlistarTasquesActivity;
import org.idi.pomoctivity.PomodoroActivity;
import org.idi.pomoctivity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

public class TaskListOnItemLongClickListener implements
		OnItemLongClickListener {

    protected CharSequence[] singleChoiceItems = { "Start Pomodoro", "Edit Task", "Finish Task", "Move task" };
	private LlistarTasquesActivity activityList;

	public TaskListOnItemLongClickListener(LlistarTasquesActivity llistarTasquesActivity) {
		activityList = llistarTasquesActivity;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
	        long id) {

	    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
	    Task task;
		try {
			TextView taskIdText = (TextView) view.findViewById(R.id.taskId);
			int taskId = Integer.valueOf((String) taskIdText.getText());
			task = activityList.getTaskWithId(taskId);
		} catch (TaskNotFoundException e) {
			task = null;
			e.printStackTrace();
		}
	    final Bundle bundle = new Bundle();
	    bundle.putCharSequence("name", task.getName());
	    bundle.putInt("remainingPomodori", task.getRemainingPomodori());
	    
	    dialogBuilder.setTitle("What do you wanna do with this task?");
	    dialogBuilder.setSingleChoiceItems(this.singleChoiceItems, 0, new OnClickListener() {
	        
	        public void onClick(DialogInterface dialog, int which) {
	        	if (which == 0) {
	        		Intent intent = new Intent(activityList.getBaseContext(), PomodoroActivity.class);
	        		intent.putExtras(bundle);
	        		activityList.startActivity(intent);
	        		//START POMODORO;
	        	}
	        	else if (which == 1) {
	        		//EDIT TASK;
	        	}
	        	else if (which == 2) {
	        		//FINISH TASK;
	        	}
	        	else if (which == 3) {

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