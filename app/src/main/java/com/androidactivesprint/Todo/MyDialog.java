package com.androidactivesprint.Todo;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidactivesprint.R;
import com.androidactivesprint.components.Priority;
import com.androidactivesprint.components.Status;
import com.androidactivesprint.components.Task;
import com.androidactivesprint.components.TaskType;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.androidactivesprint.MainActivity.ADD_TASK;
import static com.androidactivesprint.MainActivity.UPDATE_TASK;

/**
 * Created by Hung on 10/3/2017.
 */

public class MyDialog extends Dialog implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.new_task_spn_task_type)
    Spinner new_task_spn_task_type;

    @BindView(R.id.new_task_spn_priority)
    Spinner new_task_spn_priority;

    @BindView(R.id.new_task_et_summary)
    EditText new_task_et_summary;

    @BindView(R.id.new_task_et_description)
    EditText new_task_et_description;

    @BindView(R.id.new_task_et_assignee)
    EditText new_task_et_assignee;

    @BindView(R.id.new_task_tv_create)
    TextView new_task_tv_create;

    @BindView(R.id.new_task_tv_cancel)
    TextView new_task_tv_cancel;

    private int selectedTaskType=0;
    private int selectedPriority=0;
    private Context mContext;
    private Task mTask;
    private MyDialogListener mListener;
    private int mType;

    public MyDialog(Context context,Task task, MyDialogListener listener, int type){
        super(context);
        this.mContext=context;
        this.mTask = task;
        this.mListener =listener;
        this.mType = type;
        init();
    }
    private MyDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.dialog_full_transparent_background);
        init();
    }

    private MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
       init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View contentView = getLayoutInflater().inflate(R.layout.new_task, null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        initData();
        if(mType == UPDATE_TASK)
            fillData(mTask);
    }

    private void init(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initData(){
        ArrayAdapter<CharSequence> taskTypeAdapter = ArrayAdapter.createFromResource(mContext,R.array.task_type, android.R.layout.simple_spinner_item);
        taskTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_task_spn_task_type.setAdapter(taskTypeAdapter);
        new_task_spn_task_type.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> priorityTypeAdapter = ArrayAdapter.createFromResource(mContext,R.array.priority_type, android.R.layout.simple_spinner_item);
        priorityTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_task_spn_priority.setAdapter(priorityTypeAdapter);
        new_task_spn_priority.setOnItemSelectedListener(this);

        new_task_tv_create.setText(mType ==UPDATE_TASK ? "Update" : "Create");
        new_task_tv_cancel.setOnClickListener(v -> dismiss());
        new_task_tv_create.setOnClickListener(v ->implementCreateUpdate() );
    }

    private void fillData(Task t){
        new_task_et_summary.setText(t.getSummary());
        new_task_et_description.setText(t.getDescription());
        new_task_et_assignee.setText(t.getAssignee());
        new_task_spn_task_type.setSelection(getTaskTypePosition(t.getTaskType()));
        new_task_spn_priority.setSelection(getPriorityPosition(t.getPriority()));

    }
    private void implementCreateUpdate(){
        if(mType ==UPDATE_TASK) {
            updateTask();
        }else if(mType == ADD_TASK) {
            createTask();
        }
        dismiss();
    }

    private void createTask() {
        Task task = new Task();
        task.setDescription(new_task_et_description.getText().toString());
        task.setSummary(new_task_et_summary.getText().toString());
        task.setStatus(Status.TO_DO);
        task.setTaskType(getTaskType(selectedTaskType));
        task.setPriority(getPriority(selectedPriority));
        task.setAssignee(new_task_et_assignee.getText().toString());
        task.setCreateDate(new Date());
        mListener.onFinish(task,mType);
    }

    private void updateTask() {
        if(mTask != null){
            mTask.setDescription(new_task_et_description.getText().toString());
            mTask.setSummary(new_task_et_summary.getText().toString());
            mTask.setTaskType(getTaskType(selectedTaskType));
            mTask.setPriority(getPriority(selectedPriority));
            mTask.setAssignee(new_task_et_assignee.getText().toString());
            mTask.setUpdateDate(new Date());
            mListener.onFinish(mTask,mType);
        }
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

//    private Status getStatus(int index){
//        switch (index){
//            case 0:
//                return Status.TO_DO;
//            case 1:
//                return Status.IN_PROGRESS;
//            case 2:
//                return Status.DONE;
//            default:
//                return Status.TO_DO;
//        }
//    }

    private TaskType getTaskType(int index){
        switch (index){
            case 0:
                return TaskType.STORY;
            case 1:
                return TaskType.TASK;
            case 2:
                return TaskType.BUG;
            default:
                return TaskType.TASK;
        }
    }

    private int getTaskTypePosition(TaskType t){
        if(t == TaskType.STORY){
            return 0;
        }else if(t == TaskType.TASK){
            return 1;
        }else  if(t == TaskType.BUG){
            return 2;
        }else
            return 0;
    }

//    private int getStatusPosition(Status s){
//        if(s == Status.TO_DO){
//            return 0;
//        }else if(s == Status.IN_PROGRESS){
//            return 1;
//        }else  if(s == Status.DONE){
//            return 2;
//        }else
//            return 0;
//    }

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

    private int getPriorityPosition(Priority p){
        if(p== Priority.High){
            return 0;
        }else if(p == Priority.Medium){
            return 1;
        }else  if(p == Priority.Low){
            return 2;
        }else
            return 1;
    }

    public interface MyDialogListener{
        void onFinish(Task task, int type);
    }
}
