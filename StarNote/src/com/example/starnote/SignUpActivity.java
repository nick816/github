package com.example.starnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Http.SignUp;
import com.example.HttpRequest.SignupHttpRequest;

public class SignUpActivity extends Activity implements SignupHttpRequest{

	private EditText username,password,confirm;
	private ImageView submit;
	private SignupHttpRequest _sign = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_signup);
		
		username=(EditText)this.findViewById(R.id.sign_username);
		password=(EditText)this.findViewById(R.id.sign_password);
		confirm=(EditText)this.findViewById(R.id.sign_confirm_password);
		submit=(ImageView)this.findViewById(R.id.btn_sign_up);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (username.getText().toString().equals("") || password.getText().toString().equals("") || confirm.getText().toString().equals("")) {
					Toast.makeText(SignUpActivity.this, "Enter username and password and confirm password!", Toast.LENGTH_SHORT).show();
				}
				if (password.getText().toString().equals(confirm.getText().toString())) {
					SignUp sign=new SignUp(SignUpActivity.this, username.getText().toString(), password.getText().toString(), _sign);
					sign.onRun();
				}else{
					Toast.makeText(SignUpActivity.this, "Correct confirm password!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestSuccess() {
		
		Toast.makeText(SignUpActivity.this, "Sign up success!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);		
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
		finish();
	}

}
