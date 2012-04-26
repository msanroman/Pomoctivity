package org.idi.listeners;

import org.idi.databases.task.TaskNotFoundException;
import org.idi.model.Task;
import org.idi.pomoctivity.LlistarTasquesActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UpOrderButtonListener implements OnClickListener {
	/**
	 * 
	 */
	private LlistarTasquesActivity llistarTasquesActivity;
	private final TextView id;

	public UpOrderButtonListener(LlistarTasquesActivity llistarTasquesActivity, TextView id) {
		this.llistarTasquesActivity = llistarTasquesActivity;
		this.id = id;
	}

	public void onClick(View v) {
		try {
			Task taskToOrder, taskToDeorder;
			taskToOrder = this.llistarTasquesActivity.getTaskWithId(Long.valueOf(id.getText().toString()));
			taskToDeorder = this.llistarTasquesActivity.getTaskWithOrder(taskToOrder.getOrder() - 1);
			taskToOrder.decreaseOrder();
			taskToDeorder.increaseOrder();
			this.llistarTasquesActivity.updateTask(taskToOrder);
			this.llistarTasquesActivity.updateTask(taskToDeorder);
			this.llistarTasquesActivity
					.setOrderPosition(this.llistarTasquesActivity.getOrderPosition() - 1);
			this.llistarTasquesActivity.fillData();
		} catch (TaskNotFoundException exception) {
			exception.printStackTrace();
		}
	}
}