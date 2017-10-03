package com.androidactivesprint.Todo;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import com.androidactivesprint.R;
import com.androidactivesprint.base.DragListener;
import com.androidactivesprint.base.ItemViewHolder;
import com.androidactivesprint.base.RecycleAdapter;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Priority;
import com.androidactivesprint.components.Task;
import com.androidactivesprint.components.TaskType;
import com.androidactivesprint.tool.Utils;

import java.util.ArrayList;

/**
 * Created by Hung on 10/2/2017.
 */

public class TaskAdapter extends RecycleAdapter<Task> implements View.OnLongClickListener {


    private Context mContext;
    private RecycleListener<Task> listener;
    public TaskAdapter(Context mContext,LayoutInflater inflater, ArrayList<Task> items, RecycleListener<Task> listener) {
        super(inflater, items, listener);
        this.mContext =mContext;
        this.listener = listener;
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
            viewHolder.item_task_tv_assignee.setText(data.getAssignee());
            viewHolder.item_task_fl_container.setTag(position);
            //viewHolder.item_task_fl_container.setOnTouchListener(this);
            viewHolder.item_task_fl_container.setOnLongClickListener(this);
            viewHolder.item_task_fl_container.setOnDragListener(new DragListener());

            viewHolder.item_task_iv_priority.setImageDrawable(Utils.getDrawable(
                    data.getPriority()== Priority.High ? R.drawable.ic_arrow_red :
                            data.getPriority()== Priority.Medium ? R.drawable.ic_arrow_orange : R.drawable.ic_arrow_green));

            viewHolder.item_task_iv_task_type.setImageDrawable(Utils.getDrawable(
                    data.getTaskType() == TaskType.STORY ? R.drawable.ic_story :
                            data.getTaskType() == TaskType.TASK ? R.drawable.ic_task : R.drawable.ic_bug));

            viewHolder.item_task_fl_container.setOnClickListener(v -> listener.onItemClick(v,data,position,View.NO_ID));
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
