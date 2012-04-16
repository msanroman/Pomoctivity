package org.idi.pomoctivity;

import org.idi.databases.task.TaskDatabase;
import org.idi.model.Task;
import org.idi.pomoctivity.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class CrearTascaActivityTest {

    private CrearTascaActivity createTaskActivity;

    @Before
    public void setUp() {
        
        createTaskActivity = new CrearTascaActivity();
        createTaskActivity.onCreate(new Bundle());
    }
    
    @Test
    public void shouldPersistTasksCreated() throws Exception {
        
        EditText taskNameInput = (EditText) createTaskActivity.findViewById(R.id.editText1);
        taskNameInput.setText("testTask");
        
        SeekBar numberOfPomodoros = (SeekBar) createTaskActivity.findViewById(R.id.pomodoroBar);
        numberOfPomodoros.setProgress(5);
        
        Button createTask = (Button) createTaskActivity.findViewById(R.id.button1);
        createTask.performClick();
        
        TaskDatabase taskDatabase = new TaskDatabase(createTaskActivity);
        Task task = taskDatabase.getTaskWithName("testTask");
        assertThat(task.getName(), equalTo("testTask"));
    }

    @Test
    public void databaseShouldWork() {
        
        TaskDatabase taskDatabase = new TaskDatabase(createTaskActivity);
        
        Task task = new Task("test", 10);
        long rowId = taskDatabase.createTask(task);
        assertThat(rowId, equalTo(1l));
        
        Task newTask = taskDatabase.getTaskWithName("test");
        
        assertThat("test", equalTo(newTask.getName()));
        
    }
}


