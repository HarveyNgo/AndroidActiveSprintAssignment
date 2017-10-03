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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidactivesprint.Todo.TaskAdapter;
import com.androidactivesprint.base.DragListener;
import com.androidactivesprint.base.RecycleListener;
import com.androidactivesprint.components.Priority;
import com.androidactivesprint.components.Status;
import com.androidactivesprint.components.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecycleListener<Task>, AdapterView.OnItemSelectedListener {

    @BindView(R.id.main_rv_todo_list)
    RecyclerView main_rv_todo_list;

    @BindView(R.id.main_rv_progress_list)
    RecyclerView main_rv_progress_list;

    @BindView(R.id.main_ll_progress_list)
    LinearLayout main_ll_progress_list;

    @BindView(R.id.main_ll_todo_list)
    LinearLayout main_ll_todo_list;

    @BindView(R.id.main_header)
    View main_header;

    @BindView(R.id.header_iv_create_task)
    View header_iv_create_task;

    private int selectedTaskType=0;
    private int selectedPriority=0;
    private ArrayList<Task> todoTaskList;
    private ArrayList<Task> progressTaskList;
    private TaskAdapter todoListAdapter;
    private TaskAdapter progressListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActiveActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureHeader();
        setupRecycleView(main_rv_todo_list);
        setupRecycleView(main_rv_progress_list);
        setupView();
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
        todoTaskList = new ArrayList<>();
        todoTaskList.add(new Task("aa"));
        todoTaskList.add(new Task("bb"));
        todoListAdapter = new TaskAdapter(getLayoutInflater(),todoTaskList,this);
        main_rv_todo_list.setAdapter(todoListAdapter);
        main_rv_todo_list.setOnDragListener(new DragListener());
        todoListAdapter.notifyDataSetChanged();

    }
    private void setupProgressList(){
        progressTaskList = new ArrayList<>();
        progressTaskList.add(new Task("cc"));
        progressTaskList.add(new Task("dd"));
        progressListAdapter = new TaskAdapter(getLayoutInflater(),progressTaskList,this);
        main_rv_progress_list.setAdapter(progressListAdapter);
        main_rv_progress_list.setOnDragListener(new DragListener());
        progressListAdapter.notifyDataSetChanged();

    }

    private void setupView(){
        setupTodoList();
        setupProgressList();
        main_ll_todo_list.setOnDragListener(new DragListener());
        main_ll_progress_list.setOnDragListener(new DragListener());
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
        Spinner new_task_spn_task_type = (Spinner) dialog.findViewById(R.id.new_task_spn_task_type);
        ArrayAdapter<CharSequence> taskTypeAdapter = ArrayAdapter.createFromResource(this,R.array.task_type, android.R.layout.simple_spinner_item);
        taskTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_task_spn_task_type.setAdapter(taskTypeAdapter);
        new_task_spn_task_type.setOnItemSelectedListener(this);

        Spinner new_task_spn_priority = (Spinner) dialog.findViewById(R.id.new_task_spn_priority);
        ArrayAdapter<CharSequence> priorityTypeAdapter = ArrayAdapter.createFromResource(this,R.array.priority_type, android.R.layout.simple_spinner_item);
        priorityTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_task_spn_priority.setAdapter(priorityTypeAdapter);
        new_task_spn_priority.setOnItemSelectedListener(this);
        TextView new_task_tv_create =(TextView) dialog.findViewById(R.id.new_task_tv_create);
        TextView new_task_tv_cancel =(TextView) dialog.findViewById(R.id.new_task_tv_cancel);
        new_task_tv_cancel.setOnClickListener(v -> dialog.dismiss());
        new_task_tv_create.setOnClickListener(v -> createTask(dialog));
        dialog.show();
    }

    private void createTask(Dialog dialog){
        Task task = new Task();
        task.setDescription(((EditText)dialog.findViewById(R.id.new_task_et_description)).getText().toString());
        task.setSummary(((EditText)dialog.findViewById(R.id.new_task_et_summary)).getText().toString());
        task.setStatus(getTaskType(selectedTaskType));
        task.setPriority(getPriority(selectedPriority));
        task.setAssignee(((EditText)dialog.findViewById(R.id.new_task_et_assignee)).getText().toString());

        if(todoTaskList == null)
            todoTaskList = new ArrayList<>();
        todoTaskList.add(task);
        todoListAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.new_task_spn_task_type:
                selectedTaskType =position;
                break;
            case R.id.new_task_spn_priority:
                selectedPriority = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Status getTaskType(int index){
        switch (index){
            case 0:
                return Status.TO_DO;
            case 1:
                return Status.IN_PROGRESS;
            case 2:
                return Status.DONE;
            default:
                return Status.TO_DO;
        }
    }

    private Priority getPriority(int index){
        switch (index){
            case 0:
                return Priority.High;
            case 1:
                return Priority.Medium;
            case 2:
                return Priority.Low;
            default:
                return Priority.Medium;
        }
    }
}
