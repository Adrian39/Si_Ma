package com.example.ezloop.yesmom.Activities;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.ezloop.yesmom.R;
import com.example.ezloop.yesmom.RecyclerViewAdapters.CompletedListAdapter;
import com.example.ezloop.yesmom.SQLite.DBAdapter;
import com.example.ezloop.yesmom.Task;

import java.util.ArrayList;

public class CompletedTasks extends AppCompatActivity {

    private Context mContext;
    private static ArrayList<Task> mTaskList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CompletedListAdapter mAdapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);

        mContext = this.getApplicationContext();

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //RECYCLER VIEW
        setListData();
    }

    private void setListData() {
        mTaskList.clear();
        DBAdapter dbAdapter = new DBAdapter(mContext);
        Task newTask;
        Cursor cursor = dbAdapter.getCompletedTasks();
        while (cursor.moveToNext());
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String desc = cursor.getString(2);
        int completed = cursor.getInt(3);

        newTask = new Task();
        newTask.setID(id);
        newTask.setName(name);
        newTask.setDescription(desc);
        newTask.setCompleted(completed);
        mTaskList.add(newTask);
    }
}
