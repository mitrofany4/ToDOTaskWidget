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
    public static String TASK_WIDGET_MOVEUP = "com.malta.taskwidget.ACTION_APPWIDGET_MOVEUP";
    public static String TASK_WIDGET_MOVEDOWN = "com.malta.taskwidget.ACTION_TASK_WIDGET_MOVEDOWN";
    public static int cur_item = 0;

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
        if (TASK_WIDGET_MOVEUP.equals(intent.getAction())) {
            cur_item++;
        }
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
		updateViews.setTextViewText(R.id.numbTV1, String.valueOf(cur_item+1));
/*        updateViews.setTextViewText(R.id.numbTV2,String.valueOf(cur_item+2));
        updateViews.setTextViewText(R.id.numbTV3,String.valueOf(cur_item+3));*/
		appWidgetManager.updateAppWidget(appWidgetID, updateViews);
		Log.d(LOG_TAG, "updateAppWidget");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
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
