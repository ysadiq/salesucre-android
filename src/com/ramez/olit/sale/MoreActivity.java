package com.ramez.olit.sale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MoreActivity extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.morelayout);
	        
	        
	        Button smsB=(Button) findViewById(R.id.smsButton);
	        smsB.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					sendSMS();
					
				}
			});
	        
	        Button emailB=(Button) findViewById(R.id.emailButton);
	        emailB.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					sendEmail("meramez@gmail.com","Feedback","");
				}
			});
	        
	        ImageButton locationMenu=(ImageButton) findViewById(R.id.locationImageButton);
			locationMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MoreActivity.this, LocationsActivity.class);
					MoreActivity.this.startActivity(intent);
					MoreActivity.this.finish();
				}
			});
			
			ImageButton MainMenu=(ImageButton) findViewById(R.id.menuImageButton);
			MainMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MoreActivity.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					MoreActivity.this.finish();
				}
			});
			
			ImageButton notificationsMenu=(ImageButton) findViewById(R.id.notificationsImageButton);
			notificationsMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MoreActivity.this, NotificationActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					MoreActivity.this.finish();
				}
			});
	 }
	 
	 
	 public void sendSMS()  
     {  
         String number = "12346556;987654321";  // The number on which you want to send SMS  
         startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));  
     }  
	 
	 
	 private void sendEmail(String recipient, String subject, String message) {
		    try {
		        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		        emailIntent.setType("plain/text");
		        if (recipient != null)  emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{recipient});
		        if (subject != null)    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		        if (message != null)    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

		        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

		    } catch (ActivityNotFoundException e) {
		        // cannot send email for some reason
		    }
		}
	 
	 
}
