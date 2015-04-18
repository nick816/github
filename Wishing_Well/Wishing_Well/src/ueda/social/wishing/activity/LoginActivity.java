package ueda.social.wishing.activity;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Forget_password;
import ueda.social.wishing.http.Login;
import ueda.social.wishing.http_request.ForgetHttpRequest;
import ueda.social.wishing.http_request.InterfaceHttpRequest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements InterfaceHttpRequest,ForgetHttpRequest{

	private TextView sign_up,forget,help;    
    private String user,pass;
    private EditText username,password;
    private InterfaceHttpRequest _interface=this;
    private ForgetHttpRequest _forget=this;
    
    private ProgressDialog progress_dg;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log_in);
        
        sign_up=(TextView)this.findViewById(R.id.btn_sign_up);
        forget=(TextView)this.findViewById(R.id.forget);
        help=(TextView)this.findViewById(R.id.btn_help);        
        
        progress_dg=new ProgressDialog(this);
        progress_dg.setMessage("Sending email...");
        
        username=(EditText)this.findViewById(R.id.username);
        password=(EditText)this.findViewById(R.id.password);
        //Testing network connection
        if(isConnected()){
            Toast.makeText(LoginActivity.this, "Network connected!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this, "Network disconnected!", Toast.LENGTH_SHORT).show();
        }
        sign_up.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,Sign_up_Activity.class);
				startActivity(intent);
			}
		}); 
        forget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(LoginActivity.this);
//				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dialog_forget_pw);
				dialog.setTitle("Forget Password...");
				// set the custom dialog components - text, image and button
				
				final EditText email=(EditText)dialog.findViewById(R.id.editText1);				
							
				Button submitButton = (Button) dialog.findViewById(R.id.button1);
				// if button is clicked, close the custom dialog
				submitButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (email.getText().toString().equals("")) {
							Toast.makeText(LoginActivity.this, "Please enter email address!", Toast.LENGTH_SHORT).show();
						}
						else{
							Forget_password forget=new Forget_password(LoginActivity.this, email.getText().toString(), _forget);
							forget.onRun();
							progress_dg.show();
							dialog.dismiss();
						}						
					}	
				});
				dialog.show();
			}
		});
        help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}
	public void onLogin(View view) {
		user=username.getText().toString();
        pass=password.getText().toString();
		if (user.equals("")&&pass.equals("")) {
			Toast.makeText(this, "Enter correct username and password!", Toast.LENGTH_SHORT).show();			
		}
		else{
			Login login = new Login(this,user,pass,_interface );
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

	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestSuccess() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(LoginActivity.this,SecondActivity.class);
		startActivity(intent);
		finish();
		Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void requestForgetFailure(String errMsg) {
		// TODO Auto-generated method stub
		progress_dg.dismiss();
	}
	@Override
	public void requestForgetSuccess() {
		// TODO Auto-generated method stub
		progress_dg.dismiss();
		Toast.makeText(LoginActivity.this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
	}	

}
