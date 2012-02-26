package com.nakamulabs.honkidasu_tokei;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class WatchWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_START_ALARM = "com.nakamulabs.honkidasu_tokei.WatchWidgetProvider.ACTION_START_ALARM";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		setAlarm(context, Calendar.getInstance());
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION_START_ALARM)) {
			if (ACTION_START_ALARM.equals(intent.getAction())) {
				Intent serviceIntent = new Intent(context, WatchService.class);
				context.startService(serviceIntent);
			}

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 1);

			setAlarm(context, cal);
		}

		super.onReceive(context, intent);
	}

	private void setAlarm(Context context, Calendar cal) {

		// AlermManagerで通知を開始
		Intent intent = new Intent(context, this.getClass());
		intent.setAction(ACTION_START_ALARM);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);

		// AlermManagerのIntervalを1分で設定
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		am.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
	}

}
