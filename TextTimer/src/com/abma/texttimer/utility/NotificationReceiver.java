package com.abma.texttimer.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.abma.texttimer.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {

	public static boolean hasNotification = false;
	public NotificationReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		hasNotification = true;
		if ( MainActivity.isRunning == true)  {
//			if ( MainActivity.isBackground == false ) {
//				MainActivity._mainActivity.onResume();
//			}
//			else {
//				Intent i = context.getPackageManager().getLaunchIntentForPackage("com.abma.texttime.MainActivity");
//				i.setFlags(0);
//				i.setPackage(null);
//				context.startActivity(i);
				Intent i = new Intent(context, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				context.startActivity(i);
//			}
		}
		else {
			Intent i = new Intent(context, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		}
	}

}
