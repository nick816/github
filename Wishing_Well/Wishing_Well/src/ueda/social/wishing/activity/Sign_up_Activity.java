package ueda.social.wishing.activity;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Register;
import ueda.social.wishing.http_request.InterfaceHttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class Sign_up_Activity extends Activity implements InterfaceHttpRequest{

	private Spinner day,month,year;
	private String[] days=new String[31];
	private String[] years=new String[200];
	private EditText firstname,lastname,country,zipcode,email,phonenumber,username,passcode,confirm;
	private RadioGroup radio_gender;
	private String first_name,last_name,str_country,zip_code,e_mail,phone_number,user_name,pass_word,confirm_pass,birth_day,birth_month,birth_year,birthday;
    private int gender;
    private InterfaceHttpRequest _interface=this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.sign_up_layout);
       
        day=(Spinner)this.findViewById(R.id.birth_day);
        month=(Spinner)this.findViewById(R.id.birth_month);
        year=(Spinner)this.findViewById(R.id.birth_year);
        firstname=(EditText)this.findViewById(R.id.firstname);
        lastname=(EditText)this.findViewById(R.id.lastname);
        country=(EditText)this.findViewById(R.id.country);
        zipcode=(EditText)this.findViewById(R.id.zipcode);
        email=(EditText)this.findViewById(R.id.email);
        phonenumber=(EditText)this.findViewById(R.id.phone_number);
        username=(EditText)this.findViewById(R.id.sign_username);
        passcode=(EditText)this.findViewById(R.id.sign_password);
        confirm=(EditText)this.findViewById(R.id.confirm_pass);
        radio_gender=(RadioGroup)this.findViewById(R.id.gender);
        for (int i = 1; i <= 31; i++) {
			days[i-1]=String.valueOf(i);
		}
        for (int i = 0; i <200 ; i++) {
			years[i]=String.valueOf(i+1900);
		}
        ArrayAdapter<String> dayadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,days);
        dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayadapter);
        ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearadapter);   
        
        day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				birth_day=parent.getItemAtPosition(position).toString();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				birth_day="1";
			}
		});
        month.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				birth_month=String.valueOf(position+1);	
				Log.d("Birth_month", birth_month);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				birth_month="January";
			}
		});
        year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				birth_year=parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				birth_year="1900";
			}
		});
        
    }
    public void onSignup(View view){
    	first_name=firstname.getText().toString();
    	last_name=lastname.getText().toString();
    	str_country=country.getText().toString();
    	zip_code=zipcode.getText().toString();
    	e_mail=email.getText().toString();
    	phone_number=phonenumber.getText().toString();
    	user_name=username.getText().toString();
    	pass_word=passcode.getText().toString();
    	confirm_pass=confirm.getText().toString();  
    	birthday=padding(Integer.parseInt(birth_day))+"-"+padding(Integer.parseInt(birth_month))+"-"+birth_year;
    	int selectedId = radio_gender.getCheckedRadioButtonId(); 
	    if (selectedId==R.id.male) {
			gender=0;
		}
	    else{
	    	gender=1;
	    }
	    if (first_name.equals("")||last_name.equals("")) {
			Toast.makeText(Sign_up_Activity.this, "Enter your name correctly!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (str_country.equals("")) {
			Toast.makeText(Sign_up_Activity.this,"Enter your country!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (zip_code.equals("")) {
			Toast.makeText(Sign_up_Activity.this, "Enter zip code!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (e_mail.equals("")) {
			Toast.makeText(Sign_up_Activity.this, "Enter your e-mail address!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (phone_number.equals("")) {
			Toast.makeText(Sign_up_Activity.this, "Enter phone number!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (user_name.equals("")) {
			Toast.makeText(Sign_up_Activity.this, "Enter username!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (pass_word.equals("")||pass_word.length()<8) {
			Toast.makeText(Sign_up_Activity.this, "Enter password correctly,More than 8 characters!", Toast.LENGTH_SHORT).show();
			return;
		}
	    if (confirm_pass.equals("")||!pass_word.equals(confirm_pass)) {
			Toast.makeText(Sign_up_Activity.this, "Error confirm password!", Toast.LENGTH_SHORT).show();
			return;
		}	    
	    Register register = new Register(this,first_name,last_name,gender,birthday,str_country,zip_code,e_mail,phone_number,user_name,pass_word, _interface);
		register.onRun();
    }
    public String padding(int temp){
    	if (temp<10) {
			return "0"+String.valueOf(temp);
		}
    	else{
    		return String.valueOf(temp);    
    	}
    }
	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void requestSuccess() {		
		// TODO Auto-generated method stub	
		Toast.makeText(Sign_up_Activity.this, "Success!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(Sign_up_Activity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
