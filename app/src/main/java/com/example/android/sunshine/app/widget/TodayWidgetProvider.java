/*
 * Copyright (c) 2016. Libero Strategies, LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and Confidential
 */

package com.example.android.sunshine.app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.sunshine.app.MainActivity;
import com.example.android.sunshine.app.R;

/**
 * Created by pink on 11/29/2016.
 */

public class TodayWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                context.getPackageName(),
                    R.layout.widget_today_small);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
