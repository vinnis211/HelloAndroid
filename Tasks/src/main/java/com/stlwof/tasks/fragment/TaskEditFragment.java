package com.stlwof.tasks.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;
import com.stlwof.tasks.activity.R;
import com.stlwof.tasks.activity.TaskEditActivity;
import com.stlwof.tasks.adapter.TaskListAdapter;
import com.stlwof.tasks.interfaces.OnEditFinished;

import java.text.DateFormat;
import java.util.Calendar;

public class TaskEditFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener
    {
    public static final String DEFAULT_FRAGMENT_TAG = "taskEditFragment";
    static final String TASK_ID = "taskId";
    static final String TASK_DATE_AND_TIME = "taskDateAndTime";

    // Views
    View rootView;
    EditText titleText;
    EditText notesText;
    ImageView imageView;
    TextView dateButton;
    TextView timeButton;

    long taskId;
    Calendar taskDateAndTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getLong(TaskEditActivity.EXTRA_TASKID, 0L);
        }

        if (savedInstanceState != null) {
            taskId = savedInstanceState.getLong(TASK_ID);
            taskDateAndTime =
            (Calendar) savedInstanceState.getSerializable
                    (TASK_DATE_AND_TIME);

        }
        // If we didn’t have a previous date, use "now"
        if (taskDateAndTime == null) {
            taskDateAndTime = Calendar.getInstance();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_edit,
                container, false);
        rootView = v.getRootView();
        titleText = (EditText) v.findViewById(R.id.title);
        notesText = (EditText) v.findViewById(R.id.notes);
        imageView = (ImageView) v.findViewById(R.id.image);
        dateButton = (TextView) v.findViewById(R.id.task_date);
        timeButton = (TextView) v.findViewById(R.id.task_time);



        // Set the thumbnail image
        Picasso.with(getActivity())
                .load(TaskListAdapter.getImageUrlForTask(taskId))
                .into(imageView);

        updateDateAndTimeButtons();

        // Tell the date and time buttons what to do when we click on
        // them.
        dateButton.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        timeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTimePicker();
                    }
                });



        return v;

    }

    /**
     * A helper method to show our Date picker
     */
    private void showDatePicker() {
        // Create a fragment transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DatePickerDialogFragment newFragment =
            DatePickerDialogFragment.newInstance( taskDateAndTime );
        newFragment.show(ft, "datePicker");
    }
    private void showTimePicker() {
    // Create a fragment transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        TimePickerDialogFragment fragment =
            TimePickerDialogFragment.newInstance(taskDateAndTime);
        fragment.show(ft, "timePicker");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // This field may have changed while our activity was
        // running, so make sure we save it to our outState bundle so
        // we can restore it later in onCreate.
        outState.putLong(TASK_ID, taskId);
        outState.putSerializable(TASK_DATE_AND_TIME, taskDateAndTime);
    }

    public static TaskEditFragment newInstance(long id) {
        TaskEditFragment fragment = new TaskEditFragment();
        Bundle args = new Bundle();
        args.putLong(TaskEditActivity.EXTRA_TASKID, id);
        fragment.setArguments(args);
        return fragment;
    }

    private static final int MENU_SAVE = 1;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, MENU_SAVE, 0, R.string.confirm)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        // The Save button was pressed
            case MENU_SAVE:
                //save(); →6
                ((OnEditFinished) getActivity()).finishEditingTask();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Call this method whenever the task’s date/time has changed and
     * we need to update our date and time buttons.
     */
    private void updateDateAndTimeButtons() {
    // Set the time button text
        DateFormat timeFormat =
        DateFormat.getTimeInstance(DateFormat.SHORT);
        String timeForButton = timeFormat.format(
                taskDateAndTime.getTime());
        timeButton.setText(timeForButton);
    // Set the date button text
        DateFormat dateFormat = DateFormat.getDateInstance();
        String dateForButton = dateFormat.format(
                taskDateAndTime.getTime());
        dateButton.setText(dateForButton);
    }

        /**
         * This is the method that our DatePicker dialog will call when
         * the user picks a date in the dialog.
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            taskDateAndTime.set(Calendar.YEAR, year);
            taskDateAndTime.set(Calendar.MONTH, monthOfYear);
            taskDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateAndTimeButtons();
        }
        /**
         * This is the method that our TimePicker dialog will call when
         * the user picks a time in the dialog.
         */
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            taskDateAndTime.set(Calendar.HOUR_OF_DAY, hour);
            taskDateAndTime.set(Calendar.MINUTE, minute);
            updateDateAndTimeButtons();
        }

    }