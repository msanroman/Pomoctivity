package org.idi.pomoctivity;

import java.util.Enumeration;

import org.idi.databases.task.TaskDatabase;
import org.idi.databases.task.TaskNotFoundException;
import org.idi.model.Task;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TableRow;
import android.widget.TextView;


public class LlistarTasquesActivity 
    extends ListActivity {
    
    static final int LIST_MODE = 0;
    private static final int ORDER_MODE = 1;
    static final int DELETE_MODE = 2;
    
    
	TaskDatabase database;
	int mode;
	private int orderPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.mode = LIST_MODE;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        this.getListView().setDividerHeight(2);
        database = new TaskDatabase(this);
        fillData();
        this.getListView().setOnTouchListener(new HorizontalTouchListener(this));
        this.getListView().setOnItemLongClickListener(new TaskListOnItemLongClickListener(this));
    }

    private void fillData() {

        Cursor cursor = database.getCursorForFetchingAllTasks();
        startManagingCursor(cursor);
        
        String[] from = new String[] { TaskDatabase.ID, TaskDatabase.NAME, TaskDatabase.REMAINING_POMODOROS, TaskDatabase.ORDER };
        int[] to = new int[] { R.id.taskId, R.id.taskName, R.id.taskRemainingPomodoros, R.id.taskOrder };
        
        SimpleCursorAdapter tasks = new SimpleCursorAdapter(this, R.layout.task_row, cursor, from, to);
        setListAdapter(tasks);
        if (this.mode == ORDER_MODE)
//        	showOrderKeysForElement(orderPosition);
    }

	public void showOrderKeysForElement(int position) {

		this.mode = ORDER_MODE;
		View orderRow = this.getListView().getChildAt(position);
		orderRow.findViewById(R.id.orderKeys).setVisibility(View.VISIBLE);

		final TextView id = (TextView) orderRow.findViewById(R.id.taskId);
		this.orderPosition = position;
	
		orderRow.findViewById(R.id.orderKeys).findViewById(R.id.imageButton1).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				try {
					Task taskToOrder, taskToDeorder;
					taskToOrder = database.getTaskWithId(Long.valueOf(id.getText().toString()));
					taskToDeorder = database.getTaskWithOrder(taskToOrder.getOrder() - 1);
					taskToOrder.decreaseOrder();
					taskToDeorder.increaseOrder();
					database.updateTask(taskToOrder);
					database.updateTask(taskToDeorder);
					orderPosition--;
					fillData();
				} catch (TaskNotFoundException exception) {
					exception.printStackTrace();
				}
			}
		});
		orderRow.findViewById(R.id.orderKeys).findViewById(R.id.imageButton2).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				try {
					Task taskToOrder, taskToDeorder;
					taskToOrder = database.getTaskWithId(Long.valueOf(id.getText().toString()));
					taskToDeorder = database.getTaskWithOrder(taskToOrder.getOrder() + 1);
					taskToOrder.increaseOrder();
					taskToDeorder.decreaseOrder();
					database.updateTask(taskToOrder);
					database.updateTask(taskToDeorder);
					orderPosition++;
					fillData();
				} catch (TaskNotFoundException exception) {
					exception.printStackTrace();
				}				
			}
		});
	}
}
