package com.example.ezloop.yesmom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezloop.yesmom.SQLite.DBAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by adria on 3/4/2018.
 */

public class TaskListAdapter
        extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    private ArrayList<Task> mTaskList = new ArrayList<>();
    private Context mContext;

    public TaskListAdapter(ArrayList<Task> tasks, Context context) {
        this.mTaskList = tasks;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_list,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTaskName.setText(mTaskList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTaskName;
        public CheckBox cbTaskCompleted;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTaskName = (TextView) itemView.findViewById(R.id.tvTaskName);
            cbTaskCompleted = (CheckBox) itemView.findViewById(R.id.cbTaskCompleted);
            cbTaskCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        DBAdapter dbAdapter = new DBAdapter(mContext);
                        dbAdapter.openDB();
                        dbAdapter.setCompleted(mTaskList.get(getPosition()).getID());
                        deleteItem(getPosition());

                    }
                }
            });
        }
    }

    public void getNewTaskList(ArrayList<Task> newTaskList) {
        mTaskList = newTaskList;
    }
    public void deleteItem(int position) {
        mTaskList.remove(position);
        notifyItemRemoved(position);
    }
}
