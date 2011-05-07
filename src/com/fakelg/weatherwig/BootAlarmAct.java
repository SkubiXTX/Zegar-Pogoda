package com.fakelg.weatherwig;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BootAlarmAct extends Activity {
	private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bootalarmact);
		Log.v("bootalarm", "oncreate");
		
		Long Intval;
		Date sync;
		int sek;
		int synch;

		sync = new Date();
		sek = sync.getSeconds();
		synch = 60 - sek;
		//Log.v("sek", Integer.toString(sek)+ " "+ Integer.toString(synch));
		
		Intent myIntent = new Intent(BootAlarmAct.this, MyService.class);
		pendingIntent = PendingIntent.getService(BootAlarmAct.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intval = Long.valueOf("60000");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, synch);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),Intval, pendingIntent);
        Log.v("alarm","Start Alarm");
		
		this.finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("bootalarm", "onpause");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("bootalarm", "onstart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("bootalarm", "onstop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("bootalarm", "ondestroy");
	}
	
}
