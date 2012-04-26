package org.idi.listeners;

import org.idi.pomoctivity.LlistarTasquesActivity;
import org.idi.pomoctivity.R;
import org.idi.pomoctivity.R.id;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class HorizontalTouchListener implements OnTouchListener {
	/**
	 * 
	 */
	private LlistarTasquesActivity activity;

	/**
	 * @param llistarTasquesActivity
	 */
	public HorizontalTouchListener(LlistarTasquesActivity llistarTasquesActivity) {
		activity = llistarTasquesActivity;
	}

	private float downX;
	private float downY;
	private float upX;
	private float upY;

	public boolean onTouch(View view, MotionEvent event) {
	
	    if(activity.getMode() == LlistarTasquesActivity.LIST_MODE) {
	    	if(isFingerPressed(event))                    
	            getDownCoordinades(event);
	        else if(isFingerReleased(event)) {
	            getUpCoordinades(event);
	        }
	    }
	    return false;
	}

	public void activateDeleteMode(View view) {

		activity.setMode(LlistarTasquesActivity.DELETE_MODE);
		showDeleteTaskOption(view);
		activity.showBackToListModeButton();
	}

	private void hideOrderKeys(View view) {

		view.findViewById(R.id.orderKeys).setVisibility(View.GONE);
	}

	private void showDeleteTaskOption(View v) {

		View trash = v.findViewById(R.id.imageButton3);
		trash.setVisibility(View.VISIBLE);
		trash.setOnClickListener(new DeleteTaskListener(activity));
	}

	private void getUpCoordinades(MotionEvent event) {

	    upX = event.getX();
	    upY = event.getY();
	}

	private void getDownCoordinades(MotionEvent event) {

	    downX = event.getX();
	    downY = event.getY();
	}

	private boolean movementIsToTheLeft() {

	    return upX - downX <= 0;
	}

	public boolean isAnHorizontalMovement() {

	    return getHorizontalDistance() > getVerticalDistance();
	}

	private float getVerticalDistance() {

	    return Math.abs(upY - downY);
	}

	private float getHorizontalDistance() {

	    return Math.abs(upX - downX);
	}

	public boolean isFingerReleased(MotionEvent event) {

	    return event.getAction() == MotionEvent.ACTION_UP;
	}

	public boolean isFingerPressed(MotionEvent event) {

	    return event.getAction() == MotionEvent.ACTION_DOWN;
	}
}