package org.idi.pomoctivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TaskTabActivity extends TabActivity {

	private Resources resources;
	private TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasks_tab);
		resources = getResources();
		tabHost = getTabHost();
		addTodoTab();
		addFinishedTab();
		addAllTasksTab();
		tabHost.setCurrentTab(0);
	}

	private void addAllTasksTab() {
		Intent finishedTasksIntent = new Intent(this, LlistarTasquesActivity.class);
		TabSpec finished = buildTabSpec(finishedTasksIntent, "Totes les tasques", "Totes", android.R.drawable.ic_menu_today);
		tabHost.addTab(finished);
	}

	private void addFinishedTab() {
		Intent finishedTasksIntent = new Intent(this, FinishedTasksActivity.class);
		TabSpec finished = buildTabSpec(finishedTasksIntent, "Finished", "Finished", android.R.drawable.ic_input_get);
		tabHost.addTab(finished);
	}

	private void addTodoTab() {
		Intent llistarTasquesIntent = new Intent(this, TodoTasksActivity.class);
		TabSpec toDo = buildTabSpec(llistarTasquesIntent, "To do today", "To do", android.R.drawable.ic_menu_agenda);
		tabHost.addTab(toDo);
	}

	private TabSpec buildTabSpec(Intent llistarTasquesIntent, String label, String iconLabel, int icon) {
		TabSpec tabSpec = tabHost.newTabSpec(label);
		tabSpec.setIndicator(iconLabel, resources.getDrawable(icon));
		tabSpec.setContent(llistarTasquesIntent);
		return tabSpec;
	}
}
