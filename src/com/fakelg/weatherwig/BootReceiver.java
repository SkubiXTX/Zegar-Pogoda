package com.fakelg.weatherwig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {   
	
	@Override
	public void onReceive(Context context, Intent intent) {
	     Intent bootIntent = new Intent(context, MyService.class);
	     Intent bootAct = new Intent(context,BootAlarmAct.class);
	     bootAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     context.startService(bootIntent);
	     context.startActivity(bootAct);

	}
}