package com.malta.taskwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Максим on 20.06.13.
 */
public class EditActivity extends Activity {
    private Button addtask;
    private ListView tasksLV;
    private ArrayList <Task>  tasks = new ArrayList<Task>();
    private TaskItemAdapter adapter;
    private DbAdapter dba;
    private Button closebutton;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dba.Close();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tasks);
        addtask = (Button) findViewById(R.id.addbutton);
        closebutton = (Button) findViewById(R.id.closebutton);
        tasksLV = (ListView) findViewById(R.id.editLV);
        tasks.clear();
        dba = new DbAdapter(this);
        dba.OpentoRead();
        tasks = dba.getAllTasks();
        adapter = new TaskItemAdapter(EditActivity.this, R.layout.editlistitem, tasks);
        tasksLV.setAdapter(adapter);

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, Add_task.class);
                startActivity(intent);
            }
        });

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAllWidgets();
                finish();
            }
        });
    }

    private void updateAllWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TaskWidget.class));
        if (appWidgetIds.length > 0) {
            new TaskWidget().onUpdate(this, appWidgetManager, appWidgetIds);
        }
    }
}
