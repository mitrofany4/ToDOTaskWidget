package com.malta.taskwidget;



import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class TaskWidget extends AppWidgetProvider{
	private static final String LOG_TAG = "TaskWidget";
	public static String TASK_WIDGET_UPDATE = "com.malta.taskwidget.TASK_WIDGET_UPDATE";
    public static String TASK_WIDGET_MOVEUP = "com.malta.taskwidget.ACTION_APPWIDGET_MOVEUP";
    public static String TASK_WIDGET_MOVEDOWN = "com.malta.taskwidget.ACTION_TASK_WIDGET_MOVEDOWN";
    public static int cur_item = 0;
    public ArrayList<Task> mytasks = new ArrayList<Task>();

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Log.d(LOG_TAG, "Widget Provider enabled");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);

		if (TASK_WIDGET_UPDATE.equals(intent.getAction())) {
			Log.d(LOG_TAG, "Widget update");

            DbAdapter dba = new DbAdapter(context);
            dba=dba.OpentoRead();

            mytasks = dba.getAllTasks();

            dba.Close();

			// Get the widget manager and ids for this widget provider, then call the shared
			// clock update method.
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
		    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		    int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
		    for (int appWidgetID: ids) {
				updateAppWidget(context, appWidgetManager, appWidgetID);
		    	
		    }
		}
	}

	private void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetID) {
		// TODO Auto-generated method stub
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.main);


        appWidgetManager.updateAppWidget(appWidgetID, remoteViews);
		Log.d(LOG_TAG, "updateAppWidget");
	}

	@Override
	public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		    Log.w(LOG_TAG, "onUpdate method called");
		/*    // Get all ids
		    ComponentName thisWidget = new ComponentName(context,
		        TaskWidget.class);
		    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		    // Build the intent to call the service
		    Intent intent = new Intent(context.getApplicationContext(),
		        UpdateWidgetService.class);
		    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		    // Update the widgets via the service
		    context.startService(intent);*/
        for (int i=0; i<appWidgetIds.length; i++) {
        Intent svcIntent=new Intent(ctxt, WidgetService.class);

        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widget=new RemoteViews(ctxt.getPackageName(),
                R.layout.main);

        widget.setRemoteAdapter(appWidgetIds[i], R.id.widgetLV,
                svcIntent);

        Intent clickIntent=new Intent(ctxt, EditActivity.class);
        PendingIntent clickPI=PendingIntent
                .getActivity(ctxt, 0,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        widget.setPendingIntentTemplate(R.id.widgetLV, clickPI);

        appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }
	}
}
