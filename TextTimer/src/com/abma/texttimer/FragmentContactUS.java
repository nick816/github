package com.abma.texttimer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.abma.texttimer.utility.EmailValidator;

public class FragmentContactUS extends Fragment {

	 private static final String emailTo = "info@abmobileapps.com";
	 RelativeLayout btnSend;
	 EditText etName;
	 EditText etEmailAddress;
	 EditText etMessage;
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	      View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
	 
		btnSend = (RelativeLayout) view.findViewById(R.id.layoutButtonSend);
		etName = (EditText) view.findViewById(R.id.etName);
		etEmailAddress = (EditText) view.findViewById(R.id.etEmailAddress);
		etMessage = (EditText) view.findViewById(R.id.etMessage);
		 
		btnSend.setOnClickListener(new OnClickListener() {
		 
			@Override
			public void onClick(View v) {

				EmailValidator emailValidator = new EmailValidator();
				
				String emailAddress = etEmailAddress.getText().toString();
				if ( emailValidator.validate(emailAddress) ) {
					String to = emailTo;
					String subject = etName.getText().toString();
					String message = etMessage.getText().toString();
					if ( emailAddress.isEmpty() == false )
						message += "\n\nPlease reply to " + etEmailAddress.getText().toString();
					 
					Intent email = new Intent(Intent.ACTION_SEND);
					email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
					email.putExtra(Intent.EXTRA_SUBJECT, subject);
					email.putExtra(Intent.EXTRA_TEXT, message);
					 
					//need this to prompts email client only
					email.setType("message/rfc822");
					 
					startActivity(Intent.createChooser(email, "Choose an Email client :"));
				}
				else {
					AlertDialog.Builder alert = new AlertDialog.Builder(FragmentContactUS.this.getActivity());
					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    	etEmailAddress.setText("");
					    	dialog.dismiss();     
					    }
					});
					alert.setTitle(MainActivity.APP_NAME);
					alert.setMessage("Email address is invalid.\n" + emailAddress);
					alert.show();
				}
			}
		});
		  
		return view;
	}
	 
//	 private void sendMail(String email, String subject, String messageBody) {
//	        Session session = createSessionObject();
//
//	        try {
//	            Message message = createMessage(email, subject, messageBody, session);
//	            new SendMailTask().execute(message);
//	        } catch (AddressException e) {
//	            e.printStackTrace();
//	        } catch (MessagingException e) {
//	            e.printStackTrace();
//	        } catch (UnsupportedEncodingException e) {
//	            e.printStackTrace();
//	        }
//	    }
//
//	    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
//	        Message message = new MimeMessage(session);
//	        message.setFrom(new InternetAddress("tutorials@tiemenschut.com", "Tiemen Schut"));
//	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
//	        message.setSubject(subject);
//	        message.setText(messageBody);
//	        return message;
//	    }
//
//	    private Session createSessionObject() {
//	        Properties properties = new Properties();
//	        properties.put("mail.smtp.auth", "true");
//	        properties.put("mail.smtp.starttls.enable", "true");
//	        properties.put("mail.smtp.host", "smtp.gmail.com");
//	        properties.put("mail.smtp.port", "587");
//
//	        return Session.getInstance(properties, new javax.mail.Authenticator() {
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	                return new PasswordAuthentication(username, password);
//	            }
//	        });
//	    }
//
//	    private class SendMailTask extends AsyncTask<Message, Void, Void> {
//	        private ProgressDialog progressDialog;
//
//	        @Override
//	        protected void onPreExecute() {
//	            super.onPreExecute();
//	            progressDialog = ProgressDialog.show(FragmentContactUS.this.getActivity(), "Please wait", "Sending mail", true, false);
//	        }
//
//	        @Override
//	        protected void onPostExecute(Void aVoid) {
//	            super.onPostExecute(aVoid);
//	            progressDialog.dismiss();
//	        }
//
//	        @Override
//	        protected Void doInBackground(Message... messages) {
//	            try {
//	                Transport.send(messages[0]);
//	            } catch (MessagingException e) {
//	                e.printStackTrace();
//	            }
//	            return null;
//	        }
//	    }
}
