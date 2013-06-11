package com.malta.taskwidget;



import android.content.ContentValues;
import android.content.Context;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbAdapter {
	private static final String LOG = "DbAdapter";
	private Context context;
	private SQLiteDatabase db;
	private DbOpenHelper dbHelper;
	
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
	
	public long createTask(String task_name, String start_date, String end_date){
		
		ContentValues initialValues = createContentValues(task_name, start_date, end_date);
		Log.d(LOG, "Task created");
		return db.insert(DbOpenHelper.TABLE_NAME, null, initialValues);
		
	}

	private ContentValues createContentValues(String task_name,
			String start_date, String end_date) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TASK_NAME, task_name);
		values.put(DbOpenHelper.START_DATE, start_date);
		values.put(DbOpenHelper.END_DATE, end_date);
		return values;
	}
	
/*	public String getAllTasks(){
		StringBuilder tasks = new StringBuilder();
		
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME, new String[]
				{DbOpenHelper.TASK_NAME, DbOpenHelper.START_DATE,DbOpenHelper.END_DATE}, 
				null, null, null, null, null); 
	   
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Task task = CursorToTask(cursor);
	      tasks.append(task.toString());
	      tasks.append(" \n");
	      
	      cursor.moveToNext();
	    }
	    cursor.close();
		return tasks.toString();
		
	}
	
	  private Task CursorToTask(Cursor cursor) {
		    Task task = new Task();
		    task.setID(cursor.getLong(0));
		    task.setname(cursor.getString(1));
		    task.setStart(cursor.getString(2));
		    task.setEnd(cursor.getString(3));
		    return task;
		  }
*/		  
}
