package org.idi.pomoctivity;

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
	HorizontalTouchListener(LlistarTasquesActivity llistarTasquesActivity) {
		activity = llistarTasquesActivity;
	}

	private float downX;
	private float downY;
	private float upX;
	private float upY;

	public boolean onTouch(View view, MotionEvent event) {
	
	    if(activity.mode == LlistarTasquesActivity.LIST_MODE) {
	    	if(isFingerPressed(event))                    
	            getDownCoordinades(event);
	        else if(isFingerReleased(event)) {
	            getUpCoordinades(event);
	            if(isAnHorizontalMovement())
	                activateDeleteMode(view);
	        }
	    }
	    return false;
	}

	private void activateDeleteMode(View view) {

		activity.mode = LlistarTasquesActivity.DELETE_MODE;
		hideOrderKeys(view);
		showDeleteTaskOption(view);
	}

	private void hideOrderKeys(View view) {

		view.findViewById(R.id.orderKeys).setVisibility(View.GONE);
	}

	private void showDeleteTaskOption(View v) {

		View trash = v.findViewById(R.id.imageButton3);
		trash.setVisibility(View.VISIBLE);
		trash.setOnClickListener(new DeleteTaskListener());
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

	private boolean isAnHorizontalMovement() {

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