package com.example.phonecalllog;

import com.example.phonecalllog.DetailsInfoActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
	
	public static boolean hasNotification = false;
//	String PhoneNumber;
	@SuppressWarnings("unused")
	private static int pState = TelephonyManager.CALL_STATE_IDLE;
	String TAG;
	
	public void onReceive(Context context, Intent intent) {
		 		
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
        	    		
			Log.e(TAG, "88888888888888888888888888888888888");

        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)|| intent.getStringExtra(TelephonyManager.EXTRA_STATE).
        		equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
        	        	
        	hasNotification = true;
    		if ( MainActivity.isRunning == true)  {
    			
    				Intent i = new Intent(context, DetailsInfoActivity.class);
    				i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
    				context.startActivity(i);
//    			}
    		}
    		else {
    			Intent i = new Intent(context, DetailsInfoActivity.class);
    			i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			context.startActivity(i);
    		}
    		
        }
    }		
}
