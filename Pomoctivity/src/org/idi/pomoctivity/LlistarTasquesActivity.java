package org.idi.pomoctivity;

import java.util.ArrayList;

import org.idi.databases.task.TaskDatabase;
import org.idi.databases.task.TaskNotFoundException;
import org.idi.listeners.DownOrderButtonListener;
import org.idi.listeners.HorizontalTouchListener;
import org.idi.listeners.TaskListOnItemLongClickListener;
import org.idi.listeners.UpOrderButtonListener;
import org.idi.model.Task;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class LlistarTasquesActivity 
    extends ListActivity {
    
    public static final int LIST_MODE = 0;
    protected static final int ORDER_MODE = 1;
    public static final int DELETE_MODE = 2;
    
	TaskDatabase database;
	private int mode;
	private int orderPosition;
	protected TaskListAdapter taskAdapter;
	protected ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.setMode(LIST_MODE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        this.getListView().setDividerHeight(2);
        database = new TaskDatabase(this);
        taskAdapter = new TaskListAdapter(this, R.layout.task_row, tasks);
        setListAdapter(taskAdapter);
        final HorizontalTouchListener horizontalTouchListener = new HorizontalTouchListener(this);
		getListView().setOnTouchListener(horizontalTouchListener);
        getListView().setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(horizontalTouchListener.isAnHorizontalMovement())
					horizontalTouchListener.activateDeleteMode(view);
				
			}
		});
        getListView().setOnItemLongClickListener(new TaskListOnItemLongClickListener(this));
        registerForContextMenu(getListView());
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	fillData();
    }

    public void fillData() {

    	tasks = database.getAllTasks();
    	taskAdapter.clear();
    	if (getMode() == ORDER_MODE)
    		taskAdapter.setOrderPosition(getOrderPosition());
    	else taskAdapter.setOrderPosition(-1);
    	for(Task task: tasks)
    		taskAdapter.add(task);
    	taskAdapter.notifyDataSetChanged();
    }

	public void showOrderKeysForElement(int position) {

		this.setMode(ORDER_MODE);
		showBackToListModeButton();
		View orderRow = this.getListView().getChildAt(position);
		orderRow.findViewById(R.id.orderKeys).setVisibility(View.VISIBLE);

		final TextView id = (TextView) orderRow.findViewById(R.id.taskId);
		this.setOrderPosition(position);
	
		orderRow.findViewById(R.id.orderKeys).findViewById(R.id.imageButton1).setOnClickListener(new UpOrderButtonListener(this, id));
		orderRow.findViewById(R.id.orderKeys).findViewById(R.id.imageButton2).setOnClickListener(new DownOrderButtonListener(this, id));
	}

	public void showBackToListModeButton() {
		final ImageButton backToListMode = (ImageButton) findViewById(R.id.goBackToListMode);
		backToListMode.setVisibility(View.VISIBLE);
		backToListMode.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				setMode(LIST_MODE);
				backToListMode.setVisibility(View.GONE);
				fillData();
			}
		});
	}

	public void deleteTask(Integer taskId) {

		setMode(LIST_MODE);
		findViewById(R.id.goBackToListMode).setVisibility(View.GONE);
		database.deleteTask(taskId);
		fillData();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Task getTaskWithId(long id) throws TaskNotFoundException {

		return database.getTaskWithId(id);
	}

	public Task getTaskWithOrder(int order) throws TaskNotFoundException {

		return database.getTaskWithOrder(order);
	}

	public void updateTask(Task task) {

		database.updateTask(task);
	}

	public int getOrderPosition() {
		return orderPosition;
	}

	public void setOrderPosition(int orderPosition) {
		this.orderPosition = orderPosition;
	}
}
