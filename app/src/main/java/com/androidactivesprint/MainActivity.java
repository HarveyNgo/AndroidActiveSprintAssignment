package com.androidactivesprint;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.androidactivesprint.Todo.TaskAdapter;
import com.androidactivesprint.base.DragListener;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecycleListener<Task> {

    @BindView(R.id.main_rv_todo_list)
    RecyclerView main_rv_todo_list;

    @BindView(R.id.main_rv_progress_list)
    RecyclerView main_rv_progress_list;

    @BindView(R.id.main_header)
    View main_header;

    @BindView(R.id.header_iv_create_task)
    View header_iv_create_task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActiveActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureHeader();
        setupRecycleView(main_rv_todo_list);
        setupRecycleView(main_rv_progress_list);
        setupTodoList();
        setupProgressList();
    }

    private void configureHeader(){
        header_iv_create_task.setOnClickListener(v -> showCreateNewTaskDialog());
    }

    private void setupRecycleView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getActiveActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

    }

    private void setupTodoList(){
        ArrayList<Task> todoTaskList = new ArrayList<>();
        todoTaskList.add(new Task("aa1"));
        todoTaskList.add(new Task("bb"));
        TaskAdapter adapter = new TaskAdapter(getLayoutInflater(),todoTaskList,this);
        main_rv_todo_list.setAdapter(adapter);
        main_rv_todo_list.setOnDragListener(new DragListener());
        adapter.notifyDataSetChanged();

    }
    private void setupProgressList(){
        ArrayList<Task> todoTaskList = new ArrayList<>();
        todoTaskList.add(new Task("cc"));
        todoTaskList.add(new Task("dd"));
        TaskAdapter adapter = new TaskAdapter(getLayoutInflater(),todoTaskList,this);
        main_rv_progress_list.setAdapter(adapter);
        main_rv_progress_list.setOnDragListener(new DragListener());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, Task item, int position, int clickType) {

    }

    private void showCreateNewTaskDialog(){
        final Dialog dialog = new Dialog(this, R.style.dialog_full_transparent_background);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_task);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Spinner spinner = (Spinner) dialog.findViewById(R.id.new_task_spn_task_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.task_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        dialog.show();
    }
}
