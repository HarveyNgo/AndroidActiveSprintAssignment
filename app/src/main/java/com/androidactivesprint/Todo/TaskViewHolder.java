package com.androidactivesprint.Todo;

import android.view.View;
import android.widget.TextView;

import com.androidactivesprint.R;
import com.androidactivesprint.base.ItemViewHolder;
import com.androidactivesprint.components.Task;

import butterknife.BindView;

/**
 * Created by Hung on 10/2/2017.
 */

public class TaskViewHolder extends ItemViewHolder<com.androidactivesprint.components.Task> {

    @BindView(R.id.item_task_name)
    TextView item_task_name;

    public TaskViewHolder(View itemView) {
        super(itemView);
    }
}
