package com.example.starnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Http.Login;
import com.example.HttpRequest.LogInHttpRequest;

public class LoginActivity extends Activity implements LogInHttpRequest{

	private EditText username,password;
	private ImageView login,sign;
	private LogInHttpRequest _login = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username=(EditText)this.findViewById(R.id.login_username);
		password=(EditText)this.findViewById(R.id.login_password);
		login=(ImageView)this.findViewById(R.id.btn_login);
		sign=(ImageView)this.findViewById(R.id.btn_sign);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (username.getText().toString().equals("")||password.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "Enter correct username and password!", Toast.LENGTH_SHORT).show();
				}else{
					Login login=new Login(LoginActivity.this, username.getText().toString(), password.getText().toString(), _login);
					login.onRun();
				}
			}
		});
		
		sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
				
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
				finish();
			}
		});
		
	}

	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(LoginActivity.this, MainActivity.class);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
		startActivity(i);
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub		
		finish();
	}
	
}
