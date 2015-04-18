package com.example.phonecalllog;

import com.example.phonecalllog.LogInActivity;
import com.example.phonecalllog.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity implements InterfaceHttpRequest{
	
//	private TextView sign_up;    
    private String pass, PhoneNumber;
//    private String PhoneNumber;
    private EditText password;
    private InterfaceHttpRequest _interface=this;
	private EditText editText;
	private String TAG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		PhoneNumber = systemService.getLine1Number();		
		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-11, PhoneNumber.length());
		PhoneNumber = "" + PhoneNumber;
		
		PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
		editText = (EditText)this.findViewById(R.id.lognumber);
		editText.setText(PhoneNumber);
		Log.e(TAG, PhoneNumber);
				
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LogInActivity.this);
		SharedPreferences.Editor editor = sharedPreferences.edit();		
		editor.putString("phone_number", PhoneNumber);
		editor.commit();
		
		password=(EditText)this.findViewById(R.id.password);
		
		Button sign = (Button) findViewById (R.id.btnsign);
		sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LogInActivity.this, SignActivity.class);
				startActivity(i);
			}
		});
	}
	public void onLogin(View view) {
//		user=username.getText().toString();
		PhoneNumber=editText.getText().toString();
        pass = password.getText().toString();
		if (pass.equals("")) {
			Toast.makeText(this, "Enter correct password!", Toast.LENGTH_SHORT).show();			
		}
		else{
//			Log.e(TAG, "PhoneNumber");
			Login login = new Login(this, pass, PhoneNumber, _interface);
			login.onRun();
		}		
	}
	public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;    
    }

	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	public void requestSuccess() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(LogInActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
		Toast.makeText(LogInActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
	}
	
}
