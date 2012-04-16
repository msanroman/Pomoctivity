package org.idi.listeners;

import org.idi.pomoctivity.R;

import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;


public class PomodoroBarListener implements SeekBar.OnSeekBarChangeListener {
    
    private Activity activity;

    public PomodoroBarListener(Activity activity) {
        
        this.activity = activity;
    }
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int numberOfPomodoros,
            boolean fromUser) {
    
        TextView pomodorosLabel = (TextView) activity.findViewById(R.id.numberOfPomodoros);
        pomodorosLabel.setText(String.valueOf(numberOfPomodoros) + " pomodoros");
    }
}