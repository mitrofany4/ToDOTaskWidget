package com.malta.taskwidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Максим on 20.06.13.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new TaskViewsFactory(this.getApplicationContext(),intent));
    }
}
