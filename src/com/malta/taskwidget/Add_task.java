package com.malta.taskwidget;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Add_task extends Activity {
    private EditText tasknameEditText;
    private EditText startnameEditText;
    private EditText endnameEditText;
	private Button addbutton;
	private Button cancelbutton;
	
	private void updateAllWidgets(){
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
	    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TaskWidget.class));
	    if (appWidgetIds.length > 0) {
	        new TaskWidget().onUpdate(this, appWidgetManager, appWidgetIds);
	    }
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task);
		setUpViews();
	}
	
	protected void addTask(){
		DbAdapter dba = new DbAdapter(this);
		dba=dba.OpentoWrite();
		long n=dba.createTask(tasknameEditText.getText().toString(), 
					   startnameEditText.getText().toString(), 
					   endnameEditText.getText().toString());
		Log.i("Add task", "createTask "+Long.toString(n));
		updateAllWidgets();
		dba.Close();
		finish();
	}
	
	protected void cancel(){
		finish();
	}
	
	protected void setUpViews(){
		tasknameEditText = (EditText)findViewById(R.id.task_name);
		startnameEditText = (EditText)findViewById(R.id.start_name);
		endnameEditText = (EditText)findViewById(R.id.end_name);
		addbutton = (Button)findViewById(R.id.add_task);
		cancelbutton = (Button)findViewById(R.id.Cancel);
		
		addbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addTask();
			}
		});	
		
		cancelbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});
	}
}
