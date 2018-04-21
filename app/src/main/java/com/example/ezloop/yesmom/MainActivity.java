package com.example.ezloop.yesmom;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ezloop.yesmom.BroadcastReceivers.NotificationReceiver;
import com.example.ezloop.yesmom.Dialogs.AddTaskDialogFragment;
import com.example.ezloop.yesmom.SQLite.DBAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private static ArrayList<Task> mTaskList = new ArrayList<>();
    private static RecyclerView mRecyclerView;
    private static TaskListAdapter mAdapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this.getApplicationContext();

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //FAB - ADD NEW TASK
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
            }
        });

        //RECYCLER VIEW
        setTaskData();
        mRecyclerView = (RecyclerView) findViewById(R.id.rvTaskList);
        mAdapter = new TaskListAdapter(getTaskData(), mContext);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //NAVIGATION DRAWER
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(false);
                if (item.getItemId() == R.id.nav_pending){
                    Intent pendingIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(pendingIntent);
                }
                else if (item.getItemId() == R.id.nav_completed) {

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        if (mTaskList.size() != 0){
            setAlarm();
        }

    }

    private void addNewTask() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddTaskDialogFragment newFragment = new AddTaskDialogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        transaction.add(R.id.drawer_layout, newFragment).addToBackStack(null).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //GET AND SET DATA FROM DB
    public static void setTaskData() {
        mTaskList.clear();
        DBAdapter dbAdapter = new DBAdapter(mContext);
        Task newTask;
        Cursor cursor = dbAdapter.getUncompletedTasks();
        while (cursor.moveToNext()) {
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

    public ArrayList<Task> getTaskData(){
        return mTaskList;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTaskData();
        mAdapter.getNewTaskList(mTaskList);
        mAdapter.notifyDataSetChanged();
    }

    public void updateRecView(){
        setTaskData();
        mAdapter.notifyDataSetChanged();
    }

    public void setAlarm(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent mIntent;
        PendingIntent pendingIntent;
        mIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 100, mIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + 3600000,
                60000,
                pendingIntent);

    }
}