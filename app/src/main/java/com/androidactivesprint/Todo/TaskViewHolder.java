package com.androidactivesprint.Todo;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidactivesprint.R;
import com.androidactivesprint.base.ItemViewHolder;

import butterknife.BindView;

/**
 * Created by Hung on 10/2/2017.
 */

public class TaskViewHolder extends ItemViewHolder<com.androidactivesprint.components.Task> {

    @BindView(R.id.item_task_tv_content)
    TextView item_task_tv_content;

    @BindView(R.id.item_task_fl_container)
    FrameLayout item_task_fl_container;

    @BindView(R.id.item_task_tv_assignee)
    TextView item_task_tv_assignee;

    @BindView(R.id.item_task_iv_priority)
    ImageView item_task_iv_priority;

    @BindView(R.id.item_task_iv_task_type)
    ImageView item_task_iv_task_type;


    public TaskViewHolder(View itemView) {
        super(itemView);
    }
}
