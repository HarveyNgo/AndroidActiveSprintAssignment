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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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
    public void moveTaskFromTodoListToDoneList(){
        MainActivity activity = rule.getActivity();
        RecyclerView targetList = (RecyclerView) activity.findViewById(R.id.main_rv_done_list);
        RecyclerView sourceList = (RecyclerView) activity.findViewById(R.id.main_rv_todo_list);
        assertNotNull(targetList);
        assertNotNull(sourceList);
        int positionSource = 1; // assume 1st item in to_do list will be moved to done list
        int positionTarget=0; // new item will be move to position 0
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TaskAdapter adapterSource = (TaskAdapter) sourceList.getAdapter();
                Task task = adapterSource.getList().get(positionSource);
                assertNotNull(task);
                ArrayList<Task> listSource = adapterSource.getList();
                listSource.remove(positionSource);
                assertEquals(listSource.size(),1); //after remove check only 1 item
                adapterSource.updateList(listSource);
                adapterSource.notifyDataSetChanged();
                assertEquals(adapterSource.getItemCount(),1); //after remove adapter have only 1 item

                TaskAdapter adapterTarget = (TaskAdapter) targetList.getAdapter();
                ArrayList<Task> customListTarget = adapterTarget.getList();
                task.setStatus(adapterTarget.getAdapterStatus());
                if (positionTarget >= 0) {
                    customListTarget.add(positionTarget, task);
                } else {
                    customListTarget.add(task);
                }
                assertEquals(customListTarget.size(),1); //after add check have 1 item
                adapterTarget.updateList(customListTarget);
                adapterTarget.notifyDataSetChanged();
                assertEquals(adapterTarget.getItemCount(),1); //after add adapter have 1 item
            }
        });
    }
}
