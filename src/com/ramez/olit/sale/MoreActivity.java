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
import android.widget.LinearLayout;

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
					sendEmail("salesucre@gmail.com","Feedback","");
				}
			});
	        
	        
	        MenuSetter m=new MenuSetter();
			m.setMenuItems(MoreActivity.this,R.id.moreLinearLayout);
			
			
			LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
			MLL.setBackgroundDrawable(null);
			
			LinearLayout LLL=(LinearLayout) findViewById(R.id.locationLinearLayout);
			LLL.setBackgroundDrawable(null);
			
			LinearLayout NLL=(LinearLayout) findViewById(R.id.notificationLinearLayout);
			NLL.setBackgroundDrawable(null);
			
			LinearLayout MoreMenu=(LinearLayout) findViewById(R.id.moreLinearLayout);
			MoreMenu.setBackgroundResource(R.drawable.tabbaritem);
			
	 }
	 
	 
	 public void sendSMS()  
     {  
         String number = "01008000840;01001794906";  // The number on which you want to send SMS  
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
