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

    @BindView(R.id.main_header)
    View main_header;

    @BindView(R.id.header_iv_create_task)
    View header_iv_create_task;

    private int selectedTaskType=0;
    private int selectedPriority=0;


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
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.new_task_spn_task_type:
                Status s = Status.valueOf("TO_DO");
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
