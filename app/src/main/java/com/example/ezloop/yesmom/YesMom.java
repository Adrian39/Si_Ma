package com.example.ezloop.yesmom;

import android.app.Application;

import com.example.ezloop.yesmom.SQLite.DBAdapter;

/**
 * Created by adria on 3/5/2018.
 */

public class YesMom extends Application {
    private DBAdapter dbAdapter;
    @Override
    public void onCreate(){
        super.onCreate();
        //dbAdapter = new DBAdapter(getApplicationContext());
        //dbAdapter.openDB();
    }
}
