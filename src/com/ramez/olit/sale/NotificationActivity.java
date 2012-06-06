package com.ramez.olit.sale;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class NotificationActivity extends Activity {
	NotificationsAdapter adapter;
	ListView list;
	
	private ArrayList<String> lMsg=new ArrayList<String>();
	private ArrayList<String> lDate=new ArrayList<String>();
	private ArrayList<String> lReads=new ArrayList<String>();
	
	private String[] msgs;
    private String[] dates;
    private String[] reads;
    
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.notificationlayout);
	        
	        
	        String res = "[]";
	        String FILENAME = "notications.json";
		    
		    FileOutputStream fos;
		    File file = new File(getFilesDir() + "/" + FILENAME);
			if (file.exists()){
				try {
					res=readInternalStoragePrivate(FILENAME,this);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	        
			try {
				parseJSON(res);
				adapter=new NotificationsAdapter(NotificationActivity.this, msgs,dates,reads);
				list=(ListView)findViewById(R.id.list);  
    			list.setAdapter(adapter);
    			
    			if (adapter.getCount()<1){
    				list.setVisibility(View.GONE);
    				RelativeLayout em=(RelativeLayout) findViewById(R.id.emptyNotification);
    				em.setVisibility(View.VISIBLE);
    			}
    			
    			JSONArray jArray = new JSONArray(res);
    			for (int i = 0; i < jArray.length(); i++){
    				JSONObject row = jArray.getJSONObject(i);
    				row.put("read","1");
    			}
    			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
    			String S=jArray.toString();
    			
    			fos.write(S.getBytes());
    			fos.close();
    			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        
	        
	        
			
			
			
			
			
			
			
			
			
	        ImageButton locationMenu=(ImageButton) findViewById(R.id.locationImageButton);
			locationMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(NotificationActivity.this, LocationsActivity.class);
					NotificationActivity.this.startActivity(intent);
					NotificationActivity.this.finish();
				}
			});
			
			ImageButton MainMenu=(ImageButton) findViewById(R.id.menuImageButton);
			MainMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					NotificationActivity.this.finish();
				}
			});
			
			ImageButton MoreMenu=(ImageButton) findViewById(R.id.moreImageButton);
			MoreMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(NotificationActivity.this, MoreActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					NotificationActivity.this.finish();
				}
			});
	 }
			
			public String readInternalStoragePrivate(String filename,Context context) throws UnsupportedEncodingException {
			    int len = 1024;
			    byte[] buffer = new byte[len];
			    try {
			    	
			        FileInputStream fis = context.openFileInput(filename);
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        int nrb = fis.read(buffer, 0, len); // read up to len bytes
			        while (nrb != -1) {
			            baos.write(buffer, 0, nrb);
			            nrb = fis.read(buffer, 0, len);
			        }
			        buffer = baos.toByteArray();
			        fis.close();
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    String data=new String(buffer, "UTF8");
			    return data;
			}
			
			
			 private void parseJSON(String jString) throws Exception {
			    	
				 JSONArray jArray = new JSONArray(jString);
				 
					for (int i = 0; i < jArray.length(); i++) {
					    JSONObject row = jArray.getJSONObject(i);
					    lMsg.add(row.getString("msg")) ;
					    lDate.add(row.getString("date"));
					    lReads.add(row.getString("read"));
					    
					msgs= new String[lMsg.size()];
					msgs=lMsg.toArray(msgs);

					dates= new String[lDate.size()];
					dates=lDate.toArray(dates);

					reads= new String[lReads.size()];
					reads=lReads.toArray(reads);

										
					
					}
					if(jArray.length()<1){
						msgs= new String[0];
						dates= new String[0];
						reads= new String[0];
					 }
				}

			 
}
