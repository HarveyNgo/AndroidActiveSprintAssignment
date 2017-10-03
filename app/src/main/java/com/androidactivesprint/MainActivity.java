package com.androidactivesprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.androidactivesprint.adapter.MyDialog;
import com.androidactivesprint.adapter.TaskAdapter;
import com.androidactivesprint.base.DragListener;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Status;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecycleListener<Task>,MyDialog.MyDialogListener {

    @BindView(R.id.main_rv_todo_list)
    RecyclerView main_rv_todo_list;

    @BindView(R.id.main_rv_progress_list)
    RecyclerView main_rv_progress_list;

    @BindView(R.id.main_rv_done_list)
    RecyclerView main_rv_done_list;

    @BindView(R.id.main_ll_progress_list)
    LinearLayout main_ll_progress_list;

    @BindView(R.id.main_ll_todo_list)
    LinearLayout main_ll_todo_list;

    @BindView(R.id.main_ll_done_list)
    LinearLayout main_ll_done_list;

    @BindView(R.id.main_header)
    View main_header;

    @BindView(R.id.header_iv_create_task)
    View header_iv_create_task;

    private ArrayList<Task> todoTaskList;
    private ArrayList<Task> progressTaskList;
    private ArrayList<Task> doneTaskList;
    private TaskAdapter todoListAdapter;
    private TaskAdapter progressListAdapter;
    private TaskAdapter doneListAdapter;

    public static final int ADD_TASK=1;
    public static final int UPDATE_TASK=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActiveActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureHeader();
        setupRecycleView(main_rv_todo_list);
        setupRecycleView(main_rv_progress_list);
        setupRecycleView(main_rv_done_list);
        initView();
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

    private void initTodoList(){
        todoTaskList = new ArrayList<>();
        todoTaskList.add(new Task("aa"));
        todoTaskList.add(new Task("bb"));
        todoListAdapter = new TaskAdapter(Status.TO_DO,getLayoutInflater(),todoTaskList,this);
        main_rv_todo_list.setAdapter(todoListAdapter);
        main_rv_todo_list.setOnDragListener(new DragListener());
        todoListAdapter.notifyDataSetChanged();

    }
    private void initProgressList(){
        progressTaskList = new ArrayList<>();
        progressTaskList.add(new Task("cc"));
        progressTaskList.add(new Task("dd"));
        progressListAdapter = new TaskAdapter(Status.IN_PROGRESS,getLayoutInflater(),progressTaskList,this);
        main_rv_progress_list.setAdapter(progressListAdapter);
        main_rv_progress_list.setOnDragListener(new DragListener());
        progressListAdapter.notifyDataSetChanged();
    }

    private void initDoneList(){
        doneTaskList = new ArrayList<>();
        doneListAdapter = new TaskAdapter(Status.DONE,getLayoutInflater(),doneTaskList,this);
        main_rv_done_list.setAdapter(doneListAdapter);
        main_rv_done_list.setOnDragListener(new DragListener());
        doneListAdapter.notifyDataSetChanged();
    }

    private void initView(){
        initTodoList();
        initProgressList();
        initDoneList();
        main_ll_todo_list.setOnDragListener(new DragListener());
        main_ll_progress_list.setOnDragListener(new DragListener());
        main_ll_done_list.setOnDragListener(new DragListener());
    }

    @Override
    public void onItemClick(View view, Task item, int position, int clickType) {
        showUpdateTaskDialog(item);
    }

    private void showCreateNewTaskDialog(){
        MyDialog myDialog =  new MyDialog(this,null,this,ADD_TASK);
        myDialog.show();
    }

    private void showUpdateTaskDialog(Task task){
        MyDialog myDialog = new MyDialog(this,task,this,UPDATE_TASK);
        myDialog.show();
    }

    @Override
    public void onFinish(Task task, int mType) {
        if(mType == ADD_TASK){
            if(todoTaskList == null)
                todoTaskList = new ArrayList<>();
            todoTaskList.add(task);
            todoListAdapter.notifyDataSetChanged();
        }else if(mType == UPDATE_TASK) {
            if(todoListAdapter !=null)
                todoListAdapter.notifyDataSetChanged();
            if(progressListAdapter !=null)
                progressListAdapter.notifyDataSetChanged();
            if(doneListAdapter !=null)
                doneListAdapter.notifyDataSetChanged();

        }

    }
}

