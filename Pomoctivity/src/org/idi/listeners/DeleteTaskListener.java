package org.idi.listeners;

import org.idi.pomoctivity.LlistarTasquesActivity;
import org.idi.pomoctivity.R;
import org.idi.pomoctivity.R.id;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.TextView;

public class DeleteTaskListener implements OnClickListener {
	
	private LlistarTasquesActivity activity;

	public DeleteTaskListener(LlistarTasquesActivity activity) {

		this.activity = activity;
	}

	public void onClick(View v) {

		View row = (View) v.getParent();
		TextView taskId = (TextView) row.findViewById(R.id.taskId);
		activity.deleteTask(Integer.valueOf((String) taskId.getText()));
	}
}