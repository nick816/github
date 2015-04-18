package com.abma.texttimer.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.abma.texttimer.MainActivity;
import com.abma.texttimer.R;

public class AlarmReceiver extends BroadcastReceiver {
//	private final String SOMEACTION = "com.manish.alarm.ACTION";

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		/**
		 * call method to send sms
		 */
		String phonenumbers = intent.getStringExtra("phoneNumbers");
		String message = intent.getStringExtra("message");
		String[] phones = phonenumbers.split("\n");
		for ( int i = 0; i < phones.length; i++ ) {
			String[] number = phones[i].split(":");

//			ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
//		    ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
//			Intent sentIntent = new Intent(context, SmsSentReceiver.class);
//			sentIntent.putExtra("phoneNumbers", intent.getStringExtra("phoneNumbers"));
//			sentIntent.putExtra("message", "message");
//			sentIntent.putExtra("alarmid", "alarmid");
//			sentIntent.putExtra("receiver", "receiver");
//		    PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
//		    		new Intent(context, SmsSentReceiver.class), 0);
//		    PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
//		            new Intent(context, SmsDeliveredReceiver.class), 0);
		    try {
		        SmsManager sms = SmsManager.getDefault();
//		        ArrayList<String> mSMSMessage = sms.divideMessage(message);
//		        for (int j = 0; j < mSMSMessage.size(); j++) {
//		            sentPendingIntents.add(j, sentPI);
//		            deliveredPendingIntents.add(j, deliveredPI);
//		        }
		        sms.sendTextMessage(number[1], null, message, null, null);
		        
		        // Notification
		        String notiMsg = "Sent " + intent.getStringExtra("receiver") + " SMS - " + intent.getStringExtra("message");
		        final int NOTIF_ID = Integer.parseInt(intent.getStringExtra("alarmid"));
			    NotificationManager notofManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
			    
			    PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationReceiver.class), 0); 
			    Notification notification = new Notification(R.drawable.icon, notiMsg, System.currentTimeMillis());
			    notification.setLatestEventInfo(context, MainActivity.APP_NAME, notiMsg, contentIntent);
			    
			    notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP;
			    notification.defaults |= Notification.DEFAULT_SOUND;
			    notofManager.notify(NOTIF_ID, notification);
			    Toast.makeText(context, notiMsg, Toast.LENGTH_SHORT).show();

		    } catch (Exception e) {

		        e.printStackTrace();
		        Toast.makeText(context, "SMS sending failed...", Toast.LENGTH_SHORT).show();
		    }
		}
	}

//	/**
//	 * send sms method
//	 */
//	public static void sendSms(String phonenumbers, String message) {
//
//		String[] phones = phonenumbers.split("\n");
//		for ( int i = 0; i < phones.length; i++ ) {
//			String[] number = phones[i].split(":");
//
//			try {
//				SmsManager smsManager = SmsManager.getDefault();
//				smsManager.sendTextMessage(number[1], null, message, null, null);
//				System.out.println("message sent");
//			} catch (Exception e) {
//				System.out.println("sending failed!");
//				e.printStackTrace();
//			}
//		}
//	}
}