package com.example.ezloop.yesmom.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.ezloop.yesmom.R;

/**
 * Created by adria on 3/4/2018.
 */

public class DBAdapter {

    //VARIABLE DECLARATION
    DBHelper dbHelper;
    private Context mContext;

    public void openDB(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            //Toast.makeText(mContext, mContext.getResources().getString(R.string.db_opened), Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.getWritableDatabase();
        }
    }

    public void closeDB(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.close();
        }
    }

    public DBAdapter(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }

    //INSERT DATA
    public long insertTask(String name, String desc) {
        long id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.T1_NAME, name);
            contentValues.put(DBHelper.T1_DESC, desc);
            id = db.insert(DBHelper.TABLE_1_NAME, null, contentValues);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //QUERY DATA
    public Cursor getUncompletedTasks(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + DBHelper.TABLE_1_NAME +
                " WHERE " + DBHelper.T1_COMP + " = 0;";
        try {
            cursor = db.rawQuery(query, null);
        } catch (SQLException e){
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.db_error),
                    Toast.LENGTH_SHORT).show();
        }
        return cursor;
    }

    //UPDATE
    public void setCompleted(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(DBHelper.T1_COMP, 1);
            db.update(DBHelper.TABLE_1_NAME, cv, DBHelper.T1_KEY_ID + "=" + id, null);
        } catch (SQLException e){
            Toast.makeText(mContext, mContext.getResources().getString(R.string.db_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    static class DBHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "planner";
        private static final int SCHEME_VERSION = 2;

        //TABLE DATA
        private static final String TABLE_1_NAME = "Tasks",
                                    T1_KEY_ID = "_id",
                                    T1_NAME = "Name",
                                    T1_DESC = "Description",
                                    T1_COMP = "Completion";

        private Context context;
        public DBHelper(Context context) {
            super(context, DB_NAME, null, SCHEME_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE " + TABLE_1_NAME +
                        "(" + T1_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        T1_NAME + " VARCHAR(50), " +
                        T1_DESC + " VARCHAR(100), " +
                        T1_COMP + " INTEGER DEFAULT 0);");
                Toast.makeText(context, context.getResources().getString(R.string.db_table_created),
                        Toast.LENGTH_SHORT).show();
            } catch (SQLException e){
                Toast.makeText(context, context.getResources().getString(R.string.db_error),
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context, context.getResources().getString(R.string.db_error),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}