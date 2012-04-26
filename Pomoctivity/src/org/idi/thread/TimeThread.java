package org.idi.thread;

import org.idi.pomoctivity.PomodoroActivity;

import android.widget.ProgressBar;

public class TimeThread extends Thread {

	private final PomodoroActivity pomodoroActivity;
	private final ProgressBar progress;
	private final int timeInSeconds;
	private int remainingTime;
	private boolean running;

	public TimeThread(PomodoroActivity pomodoroActivity, ProgressBar progress,
			int timeInSeconds) {
			
		this.pomodoroActivity = pomodoroActivity;
		this.progress = progress;
		this.progress.setProgress(timeInSeconds);
		this.progress.setMax(timeInSeconds);
		this.timeInSeconds = timeInSeconds;
		this.remainingTime = timeInSeconds;
	}

	public void setRemainingTime(int remainingTime) {
		
		this.remainingTime = remainingTime;
	}

	public void setRunning(boolean run) {

		this.running = run;
	}
	
	@Override
	public void run() {

		long ticksPS = 1000;
		long startTime;
		long sleepTime;
		while (running) {
			startTime = System.currentTimeMillis();
			--remainingTime;
			progress.setProgress(remainingTime);
			if (remainingTime == 0) {
				running = false;
				if (pomodoroActivity != null)
					pomodoroActivity.onTimeOut();
	//			else
	//				_restMain.onTimeOut();
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0)
				sleep(sleepTime);
				else
					sleep(10);
			} 
			catch (Exception e) {
			}
		}
	}
}