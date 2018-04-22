package com.example.ezloop.yesmom.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ezloop.yesmom.R;
import com.example.ezloop.yesmom.Task;

import java.util.ArrayList;

public class CompletedListAdapter extends RecyclerView.Adapter<CompletedListAdapter.MyViewHolder>{

    private ArrayList<Task> mTaskList = new ArrayList<>();
    private Context mContext;

    CompletedListAdapter(ArrayList<Task> tasks, Context context){
        this.mTaskList = tasks;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_completed_list,
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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTaskName;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvCompletedName);
        }
    }
}
