package org.idi.pomoctivity;

import java.util.ArrayList;

import org.idi.listeners.DownOrderButtonListener;
import org.idi.listeners.UpOrderButtonListener;
import org.idi.model.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<Task> {

	private LayoutInflater inflater;
	private ArrayList<Task> tasks;
	private int orderPosition = -1;
	private LlistarTasquesActivity activity;

	public TaskListAdapter(Context context, int textViewResourceId, ArrayList<Task> tasks) {
		super(context, textViewResourceId, tasks);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.tasks = tasks;
		this.activity = (LlistarTasquesActivity) context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TaskView taskView;
		Task task = tasks.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.task_row, null);
			taskView = new TaskView(convertView);
			convertView.setTag(taskView);
		}
		else taskView = (TaskView) convertView.getTag();
		
		taskView.id.setText(String.valueOf(task.getId()));
		taskView.name.setText(task.getName());
		taskView.order.setText(String.valueOf(task.getOrder()));
		taskView.remainingPomodoros.setText(String.valueOf(task.getRemainingPomodori()));
		if(position == orderPosition) {
			
			taskView.orderKeys.setVisibility(View.VISIBLE);
			taskView.orderKeys.findViewById(R.id.imageButton1).setOnClickListener(new UpOrderButtonListener(activity, taskView.id));
			taskView.orderKeys.findViewById(R.id.imageButton2).setOnClickListener(new DownOrderButtonListener(activity, taskView.id));
		}
		else taskView.orderKeys.setVisibility(View.GONE);
		return convertView;
	}
	
	public void setOrderPosition(int orderPosition) {
	
		this.orderPosition = orderPosition;
	}

	private class TaskView {
		
		public TextView remainingPomodoros;
		public TextView order;
		public TextView id;
		public TextView name;
		public LinearLayout orderKeys;

		public TaskView(View convertView) {
			name = (TextView) convertView.findViewById(R.id.taskName);
			id = (TextView) convertView.findViewById(R.id.taskId);
			order = (TextView) convertView.findViewById(R.id.taskOrder);
			remainingPomodoros = (TextView) convertView.findViewById(R.id.taskRemainingPomodoros);
			orderKeys = (LinearLayout) convertView.findViewById(R.id.orderKeys);
		}
	}
}

	
