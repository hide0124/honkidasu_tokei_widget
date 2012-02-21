package com.nakamulabs.honkidasu_tokei;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WatchService extends Service {

	private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

	@Override
	public void onStart(Intent intent, int startId) {
		Date now = new Date();
		String time = format.format(now);

		String message = getResources().getStringArray(R.array.messages)[now
				.getHours()];

		updateLargeWidget(time, message);
		updateSmallWidget(message);
	}

	private void updateLargeWidget(String time, String message) {
		RemoteViews remoteViews = createRemoteViews(R.layout.large_watch);

		remoteViews.setTextViewText(R.id.watchTextView, time);
		remoteViews.setTextViewText(R.id.honkiTextView, message);

		updateWidget(remoteViews, LargeWatchWidgetProvider.class);
	}

	private void updateSmallWidget(String message) {
		RemoteViews remoteViews = createRemoteViews(R.layout.small_watch);

		remoteViews.setTextViewText(R.id.honkiTextView, message);

		updateWidget(remoteViews, SmallWatchWidgetProvider.class);
	}

	private RemoteViews createRemoteViews(int id) {
		return new RemoteViews(getPackageName(), id);
	}

	private void updateWidget(RemoteViews remoteViews, Class<?> clazz) {
		ComponentName largeWidget = new ComponentName(this, clazz);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(largeWidget, remoteViews);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}