package org.idi.databases.task;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.idi.model.Task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TaskDatabase {

    public static final String ID = "_id";
    public static final String REMAINING_POMODOROS = "remaining_pomodoros";
    public static final String NAME = "name";
    public static final String ORDER = "task_order";
    public static final String DB_TABLE = "tasks";
    private Context context;
    private TaskDatabaseHelper taskDatabaseHelper;

    public TaskDatabase(Context context) {

        this.context = context;
        taskDatabaseHelper = new TaskDatabaseHelper(context);
    }

    public long createTask(Task task) {

        SQLiteDatabase database = taskDatabaseHelper.getWritableDatabase();
        String table = DB_TABLE;
        ContentValues taskValues = new ContentValues();
        taskValues.put(NAME, task.getName());
        taskValues.put(REMAINING_POMODOROS, task.getRemainingPomodori());
        taskValues.put("finished", task.isCompleted());
        taskValues.put(ORDER, this.getOrder());
        return database.insert(table, null, taskValues);
    }

    private int getOrder() {

    	Cursor tasks = this.getCursorForFetchingAllTasks(); 
    	int order = 0;
    	while (tasks.moveToNext())
    		if (order < tasks.getInt(3))
    			order = tasks.getInt(3);
    	return order+1;
	}

	public Task getTaskWithName(String name) throws TaskNotFoundException {

        Task task = getTaskWithWhereClause("name = '" + name +"'");
        return task;
    }

    private Task getTaskWithWhereClause(String where) throws TaskNotFoundException {

        SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();
        String[] columns = {ID, NAME, REMAINING_POMODOROS, ORDER};
        Cursor cursor = database.query(true, DB_TABLE, columns, where , null, null, null, null, "1");
        Task task;
        if(cursor.moveToFirst())
            task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        else throw new TaskNotFoundException();
        return task;
    }

    public Cursor getCursorForFetchingAllTasks() {

        SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();
        return database.query(DB_TABLE, new String[]{ ID, NAME, REMAINING_POMODOROS, ORDER }, null, null, null, null, "task_order, finished") ;
    }
    
    public ArrayList<Task> getAllTasks() {
    	
    	return getAllTasksWithWhere("");
    }

    public Task getTaskWithId(long id) throws TaskNotFoundException {

        return getTaskWithWhereClause("_id = " + String.valueOf(id));
    }

	public Task getTaskWithOrder(Integer order) throws TaskNotFoundException {

		return getTaskWithWhereClause(ORDER+" = " + String.valueOf(order));
	}

	public void updateTask(Task taskToOrder) {

		SQLiteDatabase database = taskDatabaseHelper.getWritableDatabase();
		ContentValues taskValues = new ContentValues();
		taskValues.put(ORDER, taskToOrder.getOrder());
		database.update(DB_TABLE, taskValues, "_id = " + taskToOrder.getId(), null);
	}

	public void deleteTask(Integer taskId) {

		SQLiteDatabase database = taskDatabaseHelper.getWritableDatabase();
		database.delete(DB_TABLE, ID + "=" + taskId, null);
		database.close();
	}

	public ArrayList<Task> getAllFinishedTasks() {
		
		return getAllTasksWithWhere("finished = 1");
	}

	public ArrayList<Task> getAllTodoTasks() {

		return getAllTasksWithWhere("finished = 0");
	}
	
	private ArrayList<Task> getAllTasksWithWhere(String where) {
		
		SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();
    	Cursor c = database.query(DB_TABLE, new String[]{ ID, NAME, REMAINING_POMODOROS, ORDER }, where, null, null, null, "task_order, finished") ;
    	ArrayList<Task> tasks = new ArrayList<Task>();
    	while(c.moveToNext()) {
    		Task task = new Task(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3));
    		tasks.add(task);
    	}
    	database.close();
    	return tasks;
	}
    
}
