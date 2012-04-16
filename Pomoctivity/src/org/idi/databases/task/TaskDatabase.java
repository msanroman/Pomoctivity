package org.idi.databases.task;

import java.lang.reflect.Field;

import org.idi.model.Task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TaskDatabase {

    public static final String ID = "_id";
    public static final String REMAINING_POMODOROS = "remaining_pomodoros";
    public static final String NAME = "name";
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
        return database.insert(table, null, taskValues);
    }

    public Task getTaskWithName(String name) {

        Task task = getTaskWithWhereClause("name = '" + name +"'");
        return task;
    }

    private Task getTaskWithWhereClause(String where) {

        SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();
        String[] columns = {NAME, REMAINING_POMODOROS};
        Cursor cursor = database.query(true, DB_TABLE, columns, where , null, null, null, null, "1");
        Task task;
        if(cursor.moveToFirst())
            task = new Task(cursor.getString(0), cursor.getInt(1));
        else task = null;
        return task;
    }

    public Cursor getCursorForFetchingAllTasks() {

        SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();
        return database.query(DB_TABLE, new String[]{ ID, NAME, REMAINING_POMODOROS }, null, null, null, null, null) ;
    }

    public Task getTaskWithId(long id) {

        return getTaskWithWhereClause("_id = " + String.valueOf(id));
    }
    
}
