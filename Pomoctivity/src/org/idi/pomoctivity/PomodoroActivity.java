package org.idi.pomoctivity;

import org.idi.model.Task;
import org.idi.pomoctivity.R;
import org.idi.thread.TimeThread;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PomodoroActivity extends Activity implements OnClickListener {

	private Task task;
	private boolean isStarting = true;
	private TimeThread timeThread;
	private int remainingTime;
	private MediaPlayer pomodoroTicking;
	private MediaPlayer alarmRinging;
	private boolean finishedPomodoro;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		task = new Task((String) getIntent().getExtras().getCharSequence("name"),
				getIntent().getExtras().getInt("remainingPomodori"));
		super.onCreate(savedInstanceState);
		setUpLayoutContent();
	}
	
	private void setUpLayoutContent() {
		
		setContentView(R.layout.pomodoro);

		TextView pomodoroLabel = (TextView) findViewById(R.id.pomodoroText);
		pomodoroLabel.setText(String.valueOf(task.getRemainingPomodori()) + " pomodoro remaining for " + task.getName());
		
		Button pomodoroButton = (Button) findViewById(R.id.pomodoroButton);
		pomodoroButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (isStarting) {
			isStarting = false;
			startTimer();
			((TextView) v).setText("Stop pomodoro");
		} else {
			isStarting = true;
			runOnUiThread(stopTimer);
			((TextView) v).setText("Start pomodoro");
		}
	}
	
	public void startTimer() {
		
		ProgressBar progress = (ProgressBar) findViewById(R.id.pomodoroProgressBar);
		timeThread = new TimeThread(this, progress, 10);
		if (remainingTime > 0)
			timeThread.setRemainingTime(remainingTime);
		timeThread.setRunning(true);
		timeThread.start();
		pomodoroTicking = MediaPlayer.create(this, R.raw.tictac);
		pomodoroTicking.setVolume(1f, 1f);
		pomodoroTicking.setLooping(true);
		finishedPomodoro = false;
		if(alarmRinging != null)
			alarmRinging.release();
		pomodoroTicking.start();
	}

	public void onTimeOut() {

		finishedPomodoro = true;
		remainingTime = 0;
		this.runOnUiThread(stopTimer);
		decrementRemainingPomodoros();
		this.runOnUiThread(showPomodoroIsOverDialog);
	}

	private Runnable showPomodoroIsOverDialog = new Runnable() {

		public void run() {

			Toast toast = Toast.makeText(getBaseContext(), "YAY", 3);
			toast.show();
//			Intent restTime = new Intent(getBaseContext(),
//					RestTimeActivity.class);
//			restTime.putExtra("taskId", id);
//			restTime.putExtra("name", name);
//			restTime.putExtra("pomoactual",
//					totalPomodoros - remainingPomodoros);
//			restTime.putExtra("terminat", isFinished());
//			startActivity(restTime);
		}
	};

	private void decrementRemainingPomodoros() {
		// TODO Auto-generated method stub
		
	}

	private Runnable stopTimer = new Runnable() {

		public void run() {
			((TextView) findViewById(R.id.pomodoroButton)).setText("Start pomodoro");
			isStarting = true;
			timeThread.setRunning(false);
			pomodoroTicking.stop();
			pomodoroTicking.release();
			if (finishedPomodoro)
				startRinging();
		}
		
	};

	protected void startRinging() {

		alarmRinging = MediaPlayer.create(PomodoroActivity.this, R.raw.clock_ringing);
		alarmRinging.setVolume(0.5f, 0.5f);
		alarmRinging.start();
	}

}
