package com.malta.taskwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by Максим on 20.06.13.
 */
public class TaskViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList <Task> items;
    private Context ctxt = null;
    private int appWidgetId;
   DbAdapter dba;

    public TaskViewsFactory(Context ctxt, Intent intent) {
        this.ctxt = ctxt;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        items = new ArrayList<Task>();
        dba = new DbAdapter(ctxt);
    }

    @Override
    public void onDataSetChanged() {

        items.clear();
        dba.OpentoRead();
       items = dba.getAllTasks();

    }

    @Override
    public void onDestroy() {
        dba.Close();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.widget_item);

        row.setTextViewText(R.id.itemnameTV, items.get(position).getName());
        row.setTextViewText(R.id.itemcatTV, items.get(position).getCategory());

        row.setInt(R.id.itemcatTV, "setBackgroundColor", getCategoryColor(items.get(position).getCategory()));

        row.setTextViewText(R.id.itemdateTV, items.get(position).getEnd_date());

        if (items.get(position).getDone()==1) {
            row.setImageViewResource(R.id.statusIV, R.drawable.checked);
        } else
            row.setImageViewResource(R.id.statusIV, R.drawable.unchecked);

/*        Intent i=new Intent();
        Bundle extras=new Bundle();

        extras.putString(WidgetProvider.EXTRA_WORD, items[position]);
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);*/

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    int getCategoryColor(String Cat){
        if (Cat.equals("семья")) return Color.YELLOW;
        else if (Cat.equals("работа")) return Color.RED;
        else if (Cat.equals("покупки")) return Color.BLUE;
        else return 0;
    }

}
