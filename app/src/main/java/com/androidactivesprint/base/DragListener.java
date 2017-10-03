package com.androidactivesprint.base;

import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;

import com.androidactivesprint.R;
import com.androidactivesprint.Todo.TaskAdapter;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hung on 10/2/2017.
 */


public class DragListener implements View.OnDragListener {

    private boolean isDropped = false;
   // private Listener listener;

//    DragListener(Listener listener) {
//        this.listener = listener;
//    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();

                switch (viewId) {
                    case R.id.main_rv_todo_list:
                    case R.id.main_rv_progress_list:
                    case R.id.item_task_fl_container:
                    case R.id.main_ll_todo_list:
                    case R.id.main_ll_progress_list:

                        RecyclerView target;
                        switch (viewId) {
                            case R.id.main_rv_todo_list:
                                target = (RecyclerView) v.getRootView().findViewById(R.id.main_rv_todo_list);
                                break;
                            case R.id.main_rv_progress_list:
                                target = (RecyclerView) v.getRootView().findViewById(R.id.main_rv_progress_list);
                                break;
                            default:
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();

                            TaskAdapter adapterSource = (TaskAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            Task task = adapterSource.getList().get(positionSource);
                            ArrayList<Task> listSource = adapterSource.getList();

                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();

                            TaskAdapter adapterTarget = (TaskAdapter) target.getAdapter();
                            ArrayList<Task> customListTarget = adapterTarget.getList();
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, task);
                            } else {
                                customListTarget.add(task);
                            }
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();

//                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
//                                listener.setEmptyListBottom(true);
//                            }
//                            if (viewId == tvEmptyListBottom) {
//                                listener.setEmptyListBottom(false);
//                            }
//                            if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
//                                listener.setEmptyListTop(true);
//                            }
//                            if (viewId == tvEmptyListTop) {
//                                listener.setEmptyListTop(false);
//                            }
                        }
                        break;
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }
}