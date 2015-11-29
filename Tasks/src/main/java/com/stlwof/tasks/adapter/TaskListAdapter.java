package com.stlwof.tasks.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stlwof.tasks.activity.R;
import com.stlwof.tasks.activity.TaskEditActivity;
import com.stlwof.tasks.interfaces.OnEditTask;

import static android.support.v7.app.AlertDialog.*;

public class TaskListAdapter
        extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>
        {
    static String[] fakeData = new String[] {
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Ah . .. ah . .. ah!"
        };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.card_task, parent, false);
        // wrap it in a ViewHolder
        return new ViewHolder(v);
        }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Context context = viewHolder.titleView.getContext();

        viewHolder.titleView.setText(fakeData[i]);

        // set the thumbnail image
        Picasso.with(context)
                .load(getImageUrlForTask(i))
                .into(viewHolder.imageView);

        // Set the click action
        viewHolder.cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((OnEditTask) context).editTask(i);
                    }
                });

        // Set the long-press action
        viewHolder.cardView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        new Builder(context)
                                .setTitle(R.string.delete_q)
                                .setMessage(viewHolder.titleView.getText())
                                .setCancelable(true)
                                .setNegativeButton(android.R.string.cancel, null)
                                .setPositiveButton(
                                        R.string.delete,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialogInterface,
                                                    int i) {
                                                deleteTask(context, i);
                                            }
                                        })
                                .show();
                        return true;
                    }
                });

    }

    void deleteTask(Context context, long id ) {
        Log.d("TaskListAdapter", "Called deleteTask");
    }

    @Override
    public int getItemCount() {
        return fakeData.length;
        }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView;
        ImageView imageView;

        public ViewHolder(CardView card) {
            super(card);
            cardView = card;
            titleView = (TextView) card.findViewById(R.id.text1);
            imageView = (ImageView) card.findViewById(R.id.image);
        }
    }

    public static String getImageUrlForTask(long taskId) {
        return "http://lorempixel.com/600/400/cats/?fakeId=" + taskId;
    }
}
