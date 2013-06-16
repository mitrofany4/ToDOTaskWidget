package com.malta.taskwidget;



import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class TaskWidget extends AppWidgetProvider{
	private static final String LOG_TAG = "TaskWidget";
	public static String TASK_WIDGET_UPDATE = "com.malta.taskwidget.TASK_WIDGET_UPDATE";
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
		RemoteViews updateViews = new RemoteViews(context.getPackageName(),	R.layout.main);
//		updateViews.setTextViewText(R.id.task_list_text, "Hello!!!");
		appWidgetManager.updateAppWidget(appWidgetID, updateViews);
		Log.d(LOG_TAG, "updateAppWidget");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
/*
		final int N = appWidgetIds.length;

		Log.d(LOG_TAG, "Updating Example Widgets.");

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch ExampleActivity
			Intent intent = new Intent(context, Add_task.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,	intent, 0);

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
			views.setOnClickPendingIntent(R.id.add_imagebutton, pendingIntent);
			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);


			// Update The clock label using a shared method
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	
*/
		    Log.w(LOG_TAG, "onUpdate method called");
		    // Get all ids
		    ComponentName thisWidget = new ComponentName(context,
		        TaskWidget.class);
		    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		    // Build the intent to call the service
		    Intent intent = new Intent(context.getApplicationContext(),
		        UpdateWidgetService.class);
		    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		    // Update the widgets via the service
		    context.startService(intent);

	}
}
