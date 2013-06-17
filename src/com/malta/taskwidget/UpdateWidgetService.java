package com.malta.taskwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {
	private static final String LOG = "Service";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(LOG, "Called");
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		ComponentName thisWidget = new ComponentName(getApplicationContext(),
				TaskWidget.class);

		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

		Log.w(LOG, "From Intent" + String.valueOf(allWidgetIds.length));
		Log.w(LOG, "Direct" + String.valueOf(allWidgetIds2.length));

        if (allWidgetIds.length > 0) {
		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.main);

            remoteViews.setTextViewText(R.id.taskTV1,String.valueOf(TaskWidget.cur_item+1));

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    Add_task.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,	clickIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.addIB, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
            stopSelf();
    }
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
