package com.androidactivesprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidactivesprint.adapter.MyDialog;
import com.androidactivesprint.components.Priority;
import com.androidactivesprint.components.Status;
import com.androidactivesprint.components.Task;
import com.androidactivesprint.components.TaskType;
import com.androidactivesprint.tool.DateUtility;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hung on 10/3/2017.
 */


public class MyDialogTest{
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);


//    @Test
//    public void testShowDialog() {
//        MainActivity activity = rule.getActivity();
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                MyDialog myDialog = new MyDialog(activity, null, new MyDialog.MyDialogListener() {
//                    @Override
//                    public void onFinish(Task task, int type) {
//
//                    }
//                }, MainActivity.ADD_TASK);
//                assertNotNull(myDialog);
//                myDialog.show();
//                assertTrue(myDialog.isShowing());
//            }
//        });
//
//    }
//
//
//    @Test
//    public void testMyDialogClickButton() {
//        MainActivity activity = rule.getActivity();
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                MyDialog myDialog = new MyDialog(activity, null, new MyDialog.MyDialogListener() {
//                    @Override
//                    public void onFinish(Task task, int type) {
//
//                    }
//                }, MainActivity.ADD_TASK);
//                assertNotNull(myDialog);
//                myDialog.show();
//                View v = myDialog.findViewById(R.id.new_task_tv_create);
//                assertTrue(v.performClick());
//                View cancel = myDialog.findViewById(R.id.new_task_tv_cancel);
//                assertTrue(cancel.performClick());
//            }
//        });
//
//    }

    @Test
    public void testAddNewTask() {
        MainActivity activity = rule.getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Task t = new Task();
                MyDialog myDialog = new MyDialog(activity, null, new MyDialog.MyDialogListener() {
                    @Override
                    public void onFinish(Task task, int type) {
                        assertEquals(task.getSummary(),"NewSummary");
                        assertEquals(task.getDescription(),"NewDescription");
                        assertEquals(task.getAssignee(),"hungnv39");
                        assertEquals(task.getPriority(),Priority.Medium);
                        assertEquals(task.getTaskType(),TaskType.TASK);
                        assertEquals(DateUtility.formatDate(task.getCreateDate(), DateUtility.DateFormatDefinition.YYYY_MM_DD),
                                DateUtility.formatDate(new Date(), DateUtility.DateFormatDefinition.YYYY_MM_DD));
                    }
                }, MainActivity.ADD_TASK);
                assertNotNull(myDialog);
                myDialog.show();
                EditText summary =(EditText) myDialog.findViewById(R.id.new_task_et_summary);
                summary.setText("NewSummary");
                EditText description =(EditText) myDialog.findViewById(R.id.new_task_et_description);
                description.setText("NewDescription");
                EditText assignee =(EditText) myDialog.findViewById(R.id.new_task_et_assignee);
                assignee.setText("hungnv39");
                Spinner priority =(Spinner) myDialog.findViewById(R.id.new_task_spn_priority);
                priority.setSelection(1); //update to Medium
                Spinner taskType =(Spinner) myDialog.findViewById(R.id.new_task_spn_task_type);
                taskType.setSelection(1); //update to Task
                View create = myDialog.findViewById(R.id.new_task_tv_create);
                create.performClick();
            }
        });

    }

    @Test
    public void testUpdateTask() {
        MainActivity activity = rule.getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Task t = new Task();
                t.setTaskType(TaskType.STORY);
                t.setAssignee("Hung");
                t.setPriority(Priority.Low);
                t.setSummary("BeforeUpdateSummary");
                t.setDescription("BeforeUpdateDescription");
                MyDialog myDialog = new MyDialog(activity, t, new MyDialog.MyDialogListener() {
                    @Override
                    public void onFinish(Task task, int type) {
                        assertEquals(task.getSummary(),"AfterUpdateSummary");
                        assertEquals(task.getDescription(),"AfterUpdateDescription");
                        assertEquals(task.getAssignee(),"HungNgo");
                        assertEquals(task.getPriority(),Priority.High);
                        assertEquals(task.getTaskType(),TaskType.TASK);
                        assertEquals(DateUtility.formatDate(task.getUpdateDate(), DateUtility.DateFormatDefinition.YYYY_MM_DD),
                                DateUtility.formatDate(new Date(), DateUtility.DateFormatDefinition.YYYY_MM_DD));
                    }
                }, MainActivity.UPDATE_TASK);
                assertNotNull(myDialog);
                myDialog.show();
                EditText summary =(EditText) myDialog.findViewById(R.id.new_task_et_summary);
                summary.setText("AfterUpdateSummary");
                EditText description =(EditText) myDialog.findViewById(R.id.new_task_et_description);
                description.setText("AfterUpdateDescription");
                EditText assignee =(EditText) myDialog.findViewById(R.id.new_task_et_assignee);
                assignee.setText("HungNgo");
                Spinner priority =(Spinner) myDialog.findViewById(R.id.new_task_spn_priority);
                priority.setSelection(0); //update to High
                Spinner taskType =(Spinner) myDialog.findViewById(R.id.new_task_spn_task_type);
                taskType.setSelection(1); //update to Task
                View create = myDialog.findViewById(R.id.new_task_tv_create);
                create.performClick();
            }
        });

    }

}

