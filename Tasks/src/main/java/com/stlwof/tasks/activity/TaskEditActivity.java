package com.stlwof.tasks.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.stlwof.tasks.activity.R;
import com.stlwof.tasks.fragment.TaskEditFragment;
import com.stlwof.tasks.interfaces.OnEditFinished;
import com.stlwof.tasks.interfaces.OnEditTask;

public class TaskEditActivity extends Activity implements OnEditFinished {
    public static final String EXTRA_TASKID = "taskId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        setActionBar((Toolbar) findViewById(R.id.toolbar));

        long id = getIntent().getLongExtra(TaskEditActivity.EXTRA_TASKID,0L);
        Fragment fragment = TaskEditFragment.newInstance(id);
        String fragmentTag = TaskEditFragment.DEFAULT_FRAGMENT_TAG;
        if (savedInstanceState == null)
        getFragmentManager().beginTransaction().add(
                R.id.container,
                fragment,
                fragmentTag).commit();
    }

    @Override
    public void finishEditingTask() {
        // When the user dismisses the editor, call finish to destroy
        // this activity.
        finish();

        }


}

