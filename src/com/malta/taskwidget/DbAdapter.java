package com.malta.taskwidget;



import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DbAdapter {
	private static final String LOG = "DbAdapter";
	private Context context;
	private SQLiteDatabase db;
	private DbOpenHelper dbHelper;
//    public ArrayList <Task> tasks = new ArrayList<Task>();
	
	public DbAdapter(Context context){
		this.context=context;
	}
	
	public DbAdapter OpentoWrite() throws SQLException {
		dbHelper = new DbOpenHelper(context);
		db = dbHelper.getWritableDatabase();
		Log.d(LOG, "DB Opened to write");
		return this;
	}
	
	public DbAdapter OpentoRead() throws SQLException {
		dbHelper = new DbOpenHelper(context);
		db = dbHelper.getReadableDatabase();
		Log.d(LOG, "DB Opened to read");
		return this;
	}	
		
	public  void Close(){
		dbHelper.close();
	}
	
	public long createTask(String task_name, String end_date, String category){
		
		ContentValues initialValues = createContentValues(task_name, end_date, category);
		Log.d(LOG, "Task created");
		return db.insert(DbOpenHelper.TABLE_NAME, null, initialValues);
		
	}

	private ContentValues createContentValues(String task_name,
			String end_date, String category) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TASK_NAME, task_name);
		values.put(DbOpenHelper.CATEGORY, category);
		values.put(DbOpenHelper.END_DATE, end_date);
		return values;
	}
	
	public ArrayList<Task> getAllTasks(){

        ArrayList <Task> tasks = new ArrayList<Task>();
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME, new String[]
				{DbOpenHelper.TASK_NAME, DbOpenHelper.END_DATE,DbOpenHelper.CATEGORY},
				null, null, null, null, null);

        tasks.clear();
        if (cursor.moveToFirst()){
            do {
               long id = cursor.getLong(cursor.getColumnIndex(DbOpenHelper.KEY_ID));
               String name = cursor.getString(cursor.getColumnIndex(DbOpenHelper.TASK_NAME));
               String category = cursor.getString(cursor.getColumnIndex(DbOpenHelper.CATEGORY));
               String enddate = cursor.getString(cursor.getColumnIndex(DbOpenHelper.END_DATE));

               Task newtask = Task.createTask(id,name, category, enddate);
               tasks.add(newtask);
            } while (cursor.moveToNext());
        }

		cursor.close();
        return  tasks;
    }

}
