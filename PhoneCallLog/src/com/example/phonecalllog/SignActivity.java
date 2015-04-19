package com.example.phonecalllog;

import com.example.phonecalllog.Register;
import com.example.phonecalllog.LogInActivity;
import com.example.phonecalllog.SignActivity;
import com.example.phonecalllog.SignActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignActivity extends Activity implements InterfaceHttpRequest{
	
    private InterfaceHttpRequest _interface=this;
    private Context context;
	private EditText passcode,confirm, editText;
	private String pass_word, confirm_pass, PhoneNumber;
	private String TAG;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign);
		
		this.context = this;
		
		passcode=(EditText)this.findViewById(R.id.sign_password);
        confirm=(EditText)this.findViewById(R.id.confirm_pass);
		context=this;
		TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		PhoneNumber = telManager.getLine1Number();
		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-11,PhoneNumber.length());
		PhoneNumber = "" + PhoneNumber;
		
//		EditText editText = null;
		PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
		editText = (EditText)findViewById(R.id.signnumber);
		editText.setText(PhoneNumber);
		
		Button btncancel = (Button)findViewById(R.id.btncancel);
		btncancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignActivity.this, LogInActivity.class);
				startActivity(i);
			}
		});
	}
	public void onSignup(View view){
		
		pass_word=passcode.getText().toString();
    	confirm_pass=confirm.getText().toString();
    	
    	if (pass_word.equals("")||pass_word.length()<8) {
    		Toast.makeText(SignActivity.this, "Enter password correctly,More than 8 characters!", Toast.LENGTH_SHORT).show();
    		return;
    	}
        if (confirm_pass.equals("")||!pass_word.equals(confirm_pass)) {
    		Toast.makeText(SignActivity.this, "Error confirm password!", Toast.LENGTH_SHORT).show();
    		return;
    	}
        Register register = new Register(context, PhoneNumber, pass_word, confirm_pass, _interface);
		register.onRun();
	}
	 public String padding(int temp){
		Log.e(TAG, "PhoneNumber");
	    	if (temp<10) {
				return "0"+String.valueOf(temp);
			}
	    	else{
	    		return String.valueOf(temp);    
	    	}
	    }
		public void requestFailure(String errMsg) {
			// TODO Auto-generated method stub
			
		}
	 public void requestSuccess() {		
			// TODO Auto-generated method stub	
			Toast.makeText(SignActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(SignActivity.this, LogInActivity.class);
			startActivity(intent);
			finish();
		}	
}
