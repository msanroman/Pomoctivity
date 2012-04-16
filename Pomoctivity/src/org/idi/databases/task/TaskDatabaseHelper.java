package org.idi.databases.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pomoctivity";
    private static final int DATABASE_VERSION = 1;

    public TaskDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        TaskTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        TaskTable.onUpgrade(db, oldVersion, newVersion);
    }

    public void open() {

        // TODO Auto-generated method stub
        
    }

}
