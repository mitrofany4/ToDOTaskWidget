package com.malta.taskwidget;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class Add_task extends Activity {
    private EditText tasknameEditText;
    private EditText startnameEditText;
    private EditText endnameEditText;
	private Button addbutton;
	private Button cancelbutton;
    private DatePicker enddatepicker;
    private TimePicker endtimepicker;

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
        String dateF = enddatepicker.getDayOfMonth() + "."+ (enddatepicker.getMonth()+1)+enddatepicker.getYear();
        java.util.Formatter timeF = new java.util.Formatter();
        timeF.format("%d:%02d", endtimepicker.getCurrentHour(), endtimepicker.getCurrentMinute());
        long n=dba.createTask(tasknameEditText.getText().toString(),
					   dateF,
					   timeF.toString());
		Log.i("Add task", "createTask "+Long.toString(n));
		updateAllWidgets();
		dba.Close();
		finish();
	}
	
	protected void cancel(){
		finish();
	}
	
	protected void setUpViews(){
		/*tasknameEditText = (EditText)findViewById(R.id.task_name);
		startnameEditText = (EditText)findViewById(R.id.start_name);
		endnameEditText = (EditText)findViewById(R.id.end_name);*/
        tasknameEditText = (EditText)findViewById(R.id.tasknameET);
        enddatepicker = (DatePicker) findViewById(R.id.datePicker);
//        enddatepicker.init(2013,06,01,null);
        endtimepicker = (TimePicker) findViewById(R.id.timePicker);
        endtimepicker.setIs24HourView(true);
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
