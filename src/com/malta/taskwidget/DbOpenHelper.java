package com.malta.taskwidget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper{
	  private static final String LOG = "DbOpenHelper";
	  private static final int DB_VERSION = 1;
	  private static final String DB_NAME = "database";
	  
	  public static final String KEY_ID = "_id";
	  public static final String TABLE_NAME = "tasks";
	  public static final String TASK_NAME = "task_name";
	  public static final String CATEGORY = "category";
	  public static final String END_DATE = "end_date";
      public static final String STATUS = "status";
	  
	  private static final String CREATE_TABLE = "create table " +
              TABLE_NAME + " ( " +
              KEY_ID + " INTEGER primary key autoincrement, " +
              TASK_NAME + " TEXT, " +
              CATEGORY + " TEXT, " +
              STATUS + " INTEGER, " +
              END_DATE + " TEXT);";

	  public DbOpenHelper(Context context) {
		    super(context, DB_NAME, null,DB_VERSION);
		  }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
		Log.d(LOG, "CREATE_TABLE "+TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	    Log.w(DbOpenHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	        onCreate(db);
	}
	
	

}
