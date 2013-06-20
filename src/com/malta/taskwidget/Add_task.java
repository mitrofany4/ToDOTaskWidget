package com.malta.taskwidget;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;


public class Add_task extends Activity {
    private EditText tasknameEditText;
	private Button addbutton;
	private Button cancelbutton;
    private DatePicker enddatepicker;
    private TimePicker endtimepicker;
    private Spinner mySpinner;

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
        long n = dba.createTask(tasknameEditText.getText().toString(),
                        mySpinner.getSelectedItem().toString(),
					   dateF+" "+timeF.toString());
		Log.i("Add task", "createTask "+Long.toString(n));
		updateAllWidgets();
		dba.Close();
		finish();
	}
	
	protected void cancel(){
		finish();
	}
	
	protected void setUpViews(){

        tasknameEditText = (EditText)findViewById(R.id.tasknameET);
        enddatepicker = (DatePicker) findViewById(R.id.datePicker);
        endtimepicker = (TimePicker) findViewById(R.id.timePicker);
        endtimepicker.setIs24HourView(true);
        mySpinner= (Spinner) findViewById(R.id.cat_spinner);
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
