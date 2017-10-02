package com.androidactivesprint.Todo;

import android.view.LayoutInflater;

import com.androidactivesprint.R;
import com.androidactivesprint.base.ItemViewHolder;
import com.androidactivesprint.base.RecycleAdapter;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;

/**
 * Created by Hung on 10/2/2017.
 */

public class TaskAdapter extends RecycleAdapter<Task> {


    public TaskAdapter(LayoutInflater inflater, ArrayList<Task> items, RecycleListener<Task> listener) {
        super(inflater, items, listener);
    }

    @Override
    protected Class<? extends ItemViewHolder> getItemViewHolderClass() {
        return TaskViewHolder.class;
    }
    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE;
    }

    @Override
    protected int getItemLayoutResource() {
            return R.layout.item_task;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected void bindItemView(ItemViewHolder<Task> holder, Task data, int position) {

        if(holder instanceof TaskViewHolder){
            TaskViewHolder viewHolder  = (TaskViewHolder) holder;
            viewHolder.item_task_name.setText(data.getName());
        }
    }
}
