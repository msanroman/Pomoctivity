package org.idi.pomoctivity;

import org.idi.databases.task.TaskDatabase;
import org.idi.listeners.PomodoroBarListener;
import org.idi.model.Task;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class CrearTascaActivity extends Activity {
    
    private TaskDatabase taskDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        taskDatabase = new TaskDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creartasca);
        setUpPomodoroCounterBar();
        TextView text = (TextView) findViewById(R.id.task_created_message);
        text.setVisibility(TextView.GONE);
    }

    public void createTask(View button) {
        
        SeekBar pomodoroBar = getSeekBar();
        EditText taskDescription = (EditText) findViewById(R.id.editText1);
        String taskName = taskDescription.getText().toString();
        int numberOfPomodoros = pomodoroBar.getProgress();
        Task task = new Task(taskName, numberOfPomodoros);
        taskDatabase.createTask(task);
        TextView text = (TextView) findViewById(R.id.task_created_message);
        text.setVisibility(TextView.VISIBLE);
    }

    private void setUpPomodoroCounterBar() {

        SeekBar numberOfPomodoros = getSeekBar();
        numberOfPomodoros.setMax(10);
        PomodoroBarListener pomodoroBarListener = new PomodoroBarListener(this);
        numberOfPomodoros.setOnSeekBarChangeListener(pomodoroBarListener);
    }

    private SeekBar getSeekBar() {

        SeekBar numberOfPomodoros = (SeekBar) findViewById(R.id.pomodoroBar);
        return numberOfPomodoros;
    }
    

}