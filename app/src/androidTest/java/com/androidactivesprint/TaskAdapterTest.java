package com.androidactivesprint;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;

import com.androidactivesprint.adapter.TaskAdapter;
import com.androidactivesprint.base.DragListener;
import com.androidactivesprint.components.Status;
import com.androidactivesprint.components.Task;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Hung on 10/3/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TaskAdapterTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureTaskListClickTest() throws Exception {
        MainActivity activity = rule.getActivity();
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.main_rv_todo_list);

        ArrayList<Task> items = new ArrayList<>();
        Task t = new Task();
        items.add(t);

        TaskAdapter taskAdapter = new TaskAdapter(Status.IN_PROGRESS,activity.getLayoutInflater(),items,null);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // simulate first item was clicked
                recyclerView.getChildAt(0).performClick();
            }
        });
    }

    @Test
    public void fillDataToRecyleView(){
        MainActivity activity = rule.getActivity();
        RecyclerView main_rv_todo_list = (RecyclerView) activity.findViewById(R.id.main_rv_todo_list);

        ArrayList<Task> progressTaskList = new ArrayList<>();
        progressTaskList.add(new Task("aa"));
        progressTaskList.add(new Task("bb"));
        TaskAdapter todoListAdapter = new TaskAdapter(Status.IN_PROGRESS,activity.getLayoutInflater(),progressTaskList,null);
        assertNotNull(todoListAdapter);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main_rv_todo_list.setAdapter(todoListAdapter);
                main_rv_todo_list.setOnDragListener(new DragListener());
                todoListAdapter.notifyDataSetChanged();
                assertNotNull(todoListAdapter);
            }
        });

    }

    @Test
    public void onLongClickRecyleView(){
        MainActivity activity = rule.getActivity();
        RecyclerView main_rv_todo_list = (RecyclerView) activity.findViewById(R.id.main_rv_todo_list);

        ArrayList<Task> progressTaskList = new ArrayList<>();
        progressTaskList.add(new Task("aa"));
        progressTaskList.add(new Task("bb"));
        TaskAdapter todoListAdapter = new TaskAdapter(Status.IN_PROGRESS,activity.getLayoutInflater(),progressTaskList,null);
        assertNotNull(todoListAdapter);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main_rv_todo_list.setAdapter(todoListAdapter);
                main_rv_todo_list.setOnDragListener(new DragListener(){
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        return super.onDrag(v, event);
                    }
                });
                todoListAdapter.notifyDataSetChanged();
                assertNotNull(todoListAdapter);
            }
        });

    }
}
