package com.stlwof.tasks.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.stlwof.tasks.interfaces.OnEditTask;

//import com.stlwof.tasks.R;

public class TaskListActivity extends Activity implements OnEditTask {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        setActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    /**
     * Called when the user asks to edit or insert a task.
     */
    @Override
    public void editTask(long id) {
// When we are asked to edit a reminder, start the
// TaskEditActivity with the id of the task to edit.
        startActivity(new Intent(this, TaskEditActivity.class)
                .putExtra(TaskEditActivity.EXTRA_TASKID, id));
    }

}
