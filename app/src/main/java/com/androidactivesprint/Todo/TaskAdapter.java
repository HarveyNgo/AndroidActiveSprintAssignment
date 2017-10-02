package com.androidactivesprint.Todo;

import android.content.ClipData;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import com.androidactivesprint.R;
import com.androidactivesprint.base.DragListener;
import com.androidactivesprint.base.ItemViewHolder;
import com.androidactivesprint.base.RecycleAdapter;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;

/**
 * Created by Hung on 10/2/2017.
 */

public class TaskAdapter extends RecycleAdapter<Task> implements View.OnLongClickListener {


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
            viewHolder.item_task_tv_content.setText(data.getDescription());

            viewHolder.item_task_fl_container.setTag(position);
            //viewHolder.item_task_fl_container.setOnTouchListener(this);
            viewHolder.item_task_fl_container.setOnLongClickListener(this);
            viewHolder.item_task_fl_container.setOnDragListener(new DragListener());
        }
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//    }

    public  ArrayList<Task> getList(){
        return items;
    }

    public void updateList(ArrayList<Task> listSource) {
        items = listSource;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
        return true;
    }
}
