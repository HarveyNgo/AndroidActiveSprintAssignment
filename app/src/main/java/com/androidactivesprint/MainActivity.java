package com.androidactivesprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.androidactivesprint.Todo.TaskAdapter;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecycleListener<Task> {

    @BindView(R.id.main_rv_todo_list)
    RecyclerView main_rv_todo_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActiveActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecycleView();
        setupTodoList();
    }


    private void setupRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getActiveActivity());
        layoutManager.setAutoMeasureEnabled(true);
        main_rv_todo_list.setLayoutManager(layoutManager);
        main_rv_todo_list.setNestedScrollingEnabled(false);
        main_rv_todo_list.setHasFixedSize(true);
    }

    private void setupTodoList(){
        ArrayList<Task> todoTaskList = new ArrayList<>();
        todoTaskList.add(new Task("aa1"));
        todoTaskList.add(new Task("bb"));
        TaskAdapter adapter = new TaskAdapter(getLayoutInflater(),todoTaskList,this);
        main_rv_todo_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, Task item, int position, int clickType) {

    }
}
