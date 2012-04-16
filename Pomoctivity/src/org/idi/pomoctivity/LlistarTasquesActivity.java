package org.idi.pomoctivity;

import org.idi.databases.task.TaskDatabase;
import org.idi.model.Task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;


public class LlistarTasquesActivity 
    extends ListActivity implements OnItemClickListener {
    
    private TaskDatabase database;
    protected CharSequence[] items = { "Començar Pomodoro", "Modificar", "Esborrar", "Finalitzar Tasca" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        this.getListView().setDividerHeight(2);
        database = new TaskDatabase(this);
        fillData();
        this.getListView().setOnTouchListener(new OnTouchListener() {
            
            private float downX;
            private float downY;
            private float upX;
            private float upY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
            
                if(isFingerPressed(event))                    
                    getDownCoordinades(event);
                else if(isFingerReleased(event)) {
                    getUpCoordinades(event);
                    if(isAnHorizontalMovement())
                        showDeleteTaskOption(view);
                }
                
                return false;
            }

            private void showDeleteTaskOption(View v) {

                Dialog dialog = new Dialog(v.getContext());
                dialog.setTitle("Yay!");
                dialog.show();
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
        });
        this.getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
                Task task = database.getTaskWithId(id);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("name", task.getName());
                bundle.putInt("remainingPomodori", task.getRemainingPomodori());
                
                dialogBuilder.setTitle("What do you wanna do with this task?");
                dialogBuilder.setSingleChoiceItems(items, 0, new OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    
                    }
                });
                
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    private void fillData() {

        Cursor cursor = database.getCursorForFetchingAllTasks();
        startManagingCursor(cursor);
        
        String[] from = new String[] { TaskDatabase.NAME, TaskDatabase.REMAINING_POMODOROS };
        int[] to = new int[] { R.id.taskName, R.id.taskRemainingPomodoros };
        
        SimpleCursorAdapter tasks = new SimpleCursorAdapter(this, R.layout.task_row, cursor, from, to);
        
        setListAdapter(tasks);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        
    }

}
