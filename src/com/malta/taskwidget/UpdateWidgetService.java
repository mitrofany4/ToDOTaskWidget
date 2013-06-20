package com.malta.taskwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class UpdateWidgetService extends Service {
	private static final String LOG = "Service";
    private ArrayList<Task> mytasks = new ArrayList<Task>();

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

        // DataBase Adapter
        DbAdapter dba = new DbAdapter(this);
        dba=dba.OpentoRead();

        mytasks = dba.getAllTasks();

        dba.Close();


        if (allWidgetIds.length > 0) {
		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.main);

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
