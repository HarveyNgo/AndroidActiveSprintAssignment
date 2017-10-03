package com.androidactivesprint;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.androidactivesprint.base.RecycleAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Hung on 10/3/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureTodoListIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.main_rv_todo_list);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView listView = (RecyclerView) viewById;
        RecyclerView.Adapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(RecyclerView.Adapter.class));
        assertEquals(adapter.getItemCount(), 2);
    }

    @Test
    public void ensureProgressListIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.main_rv_progress_list);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView listView = (RecyclerView) viewById;
        RecyclerView.Adapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(RecyclerView.Adapter.class));
        assertEquals(adapter.getItemCount(), 2);
    }


    @Test
    public void ensureDoneListIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.main_rv_done_list);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView listView = (RecyclerView) viewById;
        RecyclerView.Adapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(RecyclerView.Adapter.class));
        assertEquals(adapter.getItemCount(), 0);
    }

    @Test
    public void ensureLinearLayoutIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View main_ll_progress_list = activity.findViewById(R.id.main_ll_progress_list);
        assertThat(main_ll_progress_list,notNullValue());
        assertThat(main_ll_progress_list, instanceOf(LinearLayout.class));

        View main_ll_todo_list = activity.findViewById(R.id.main_ll_todo_list);
        assertThat(main_ll_todo_list,notNullValue());
        assertThat(main_ll_todo_list, instanceOf(LinearLayout.class));

        View main_ll_done_list = activity.findViewById(R.id.main_ll_done_list);
        assertThat(main_ll_done_list,notNullValue());
        assertThat(main_ll_done_list, instanceOf(LinearLayout.class));
    }

    @Test
    public void ensureImageViewCreateTaskIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View header_iv_create_task = activity.findViewById(R.id.header_iv_create_task);
        assertThat(header_iv_create_task,notNullValue());
        assertThat(header_iv_create_task, instanceOf(ImageView.class));
    }

//    @Test
//    public void row_Click() {
//        // Click on one of the rows.
//        onRow(TEXT_ITEM_30).onChildView(withId(R.id.rowContentTextView)).perform(click());
//
//        // Check that the activity detected the click on the first column.
//        onView(ViewMatchers.withId(R.id.selection_row_value))
//                .check(matches(withText(TEXT_ITEM_30_SELECTED)));
//    }
//
//    private static DataInteraction onRow(String str) {
//        MainActivity activity = rule.getActivity();
//        View viewById = activity.findViewById(R.id.main_rv_done_list);
//        assertThat(viewById,notNullValue());
//        assertThat(viewById, instanceOf(RecyclerView.class));
//        RecyclerView listView = (RecyclerView) viewById;
//        RecyclerView.Adapter adapter = listView.getAdapter();
//        return onData(hasEntry(equalTo(MainActivity.ROW_TEXT), is(str)));
//    }
}
