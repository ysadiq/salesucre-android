package com.ramez.olit.sale;

import java.io.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.ramez.olit.sale.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class NotificationActivity extends Activity {
	NotificationsAdapter adapter;
	com.ramez.olit.sale.PullToRefreshListView list;
	
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
	       
	        list=(com.ramez.olit.sale.PullToRefreshListView)findViewById(R.id.list);
			
			list.setOnRefreshListener(new OnRefreshListener() {
				@Override
	            public void onRefresh() {
					ParseQuery query = new ParseQuery("SSNotifications");
			        query.orderByDescending("createdAt");
			        query.findInBackground(new FindCallback() {
			            public void done(List<ParseObject> results, ParseException e) {
			                if (e != null) {
			                  // There was an error
			                } else {
			                	
			                  // results have all the Posts the current user liked.
			                	lMsg.clear();
			                	lDate.clear();
			                	lReads.clear();
			                	
			                	for (int n=0;n<results.size();n++){
			                		if(results.get(n).getBoolean("showUP")==true){
			                		lMsg.add(results.get(n).getString("content").toString());
			                		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
			                		String CreatedAtDate = sdf.format(results.get(n).getCreatedAt());
			                		lDate.add(CreatedAtDate);
			                		lReads.add("0");
			                		}
			                	}
		                		
			                	
			                	               	
			                	
		                		msgs= new String[lMsg.size()];
		    					msgs=lMsg.toArray(msgs);

		    					dates= new String[lDate.size()];
		    					dates=lDate.toArray(dates);
		    					
		    					reads= new String[lReads.size()];
		    					reads=lReads.toArray(reads);

		    					
		    					if(results.size()<1){
		    						msgs= new String[0];
		    						dates= new String[0];
		    						reads= new String[0];
		    					 }
		    					
		    					
		    					adapter=new NotificationsAdapter(NotificationActivity.this, msgs,dates,reads);
		    					
		    					
		    					
		    					
		    	    			list.setAdapter(adapter);
		    	    			
		    	    			
		    	    			if (adapter.getCount()<1){
		    	    				list.setVisibility(View.GONE);
		    	    				RelativeLayout em=(RelativeLayout) findViewById(R.id.emptyNotification);
		    	    				em.setVisibility(View.VISIBLE);
		    	    			}

		    	    			
		    	    			list.onRefreshComplete();
			                }
			              }
			          });
	            }
	        });
	        
	        
	        ParseQuery query = new ParseQuery("SSNotifications");
	        query.orderByDescending("createdAt");
	        query.findInBackground(new FindCallback() {
	            public void done(List<ParseObject> results, ParseException e) {
	                if (e != null) {
	                  // There was an error
	                } else {
	                  // results have all the Posts the current user liked.
	                	for (int n=0;n<results.size();n++){
	                		if(results.get(n).getBoolean("showUP")==true){
	                		lMsg.add(results.get(n).getString("content").toString());
	                		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
	                		String CreatedAtDate = sdf.format(results.get(n).getCreatedAt());
	                		lDate.add(CreatedAtDate);
	                		lReads.add("0");
	                		}
	                	}
                		
	                	
	                	               	
	                	
                		msgs= new String[lMsg.size()];
    					msgs=lMsg.toArray(msgs);

    					dates= new String[lDate.size()];
    					dates=lDate.toArray(dates);
    					
    					reads= new String[lReads.size()];
    					reads=lReads.toArray(reads);

    					
    					if(results.size()<1){
    						msgs= new String[0];
    						dates= new String[0];
    						reads= new String[0];
    					 }
    					adapter=new NotificationsAdapter(NotificationActivity.this, msgs,dates,reads);
    					
    					
    					
    					
    	    			list.setAdapter(adapter);
    	    			
    	    			
    	    			if (adapter.getCount()<1){
    	    				list.setVisibility(View.GONE);
    	    				RelativeLayout em=(RelativeLayout) findViewById(R.id.emptyNotification);
    	    				em.setVisibility(View.VISIBLE);
    	    			}
    	    			RelativeLayout prog=(RelativeLayout) findViewById(R.id.progressView);
    	    			prog.setVisibility(View.GONE);
	                }
	              }
	          });
	        
	        
	       
	        
	        
	        LinearLayout notificationsMenu=(LinearLayout) findViewById(R.id.notificationLinearLayout);
			notificationsMenu.setBackgroundResource(R.drawable.tabbaritem);
			
			LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
			MLL.setBackgroundDrawable(null);
			
			LinearLayout LLL=(LinearLayout) findViewById(R.id.locationLinearLayout);
			LLL.setBackgroundDrawable(null);
			
			LinearLayout MRL=(LinearLayout) findViewById(R.id.moreLinearLayout);
			MRL.setBackgroundDrawable(null);
			
	        
//	        String res = "[]";
//	        String FILENAME = "notications.json";
//		    
//		    FileOutputStream fos;
//		    File file = new File(getFilesDir() + "/" + FILENAME);
//			if (file.exists()){
//				try {
//					res=readInternalStoragePrivate(FILENAME,this);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
	        
			try {
//				parseJSON(res);
//				adapter=new NotificationsAdapter(NotificationActivity.this, msgs,dates,reads);
//				list=(ListView)findViewById(R.id.list);  
//    			list.setAdapter(adapter);
//    			
//    			if (adapter.getCount()<1){
//    				list.setVisibility(View.GONE);
//    				RelativeLayout em=(RelativeLayout) findViewById(R.id.emptyNotification);
//    				em.setVisibility(View.VISIBLE);
//    			}
    			
//    			JSONArray jArray = new JSONArray(res);
//    			for (int i = 0; i < jArray.length(); i++){
//    				JSONObject row = jArray.getJSONObject(i);
//    				row.put("read","1");
//    			}
//    			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//    			String S=jArray.toString();
//    			
//    			fos.write(S.getBytes());
//    			fos.close();
    			
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        
	        
			MenuSetter m=new MenuSetter();
			m.setMenuItems(NotificationActivity.this,R.id.notificationLinearLayout);
			
			
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
			
			
//			 private void parseJSON(String jString) throws Exception {
//			    	
//				 JSONArray jArray = new JSONArray(jString);
//				 
//					for (int i = 0; i < jArray.length(); i++) {
//					    JSONObject row = jArray.getJSONObject(i);
//					    lMsg.add(row.getString("msg")) ;
//					    lDate.add(row.getString("date"));
//					    lReads.add(row.getString("read"));
//					    
//					msgs= new String[lMsg.size()];
//					msgs=lMsg.toArray(msgs);
//
//					dates= new String[lDate.size()];
//					dates=lDate.toArray(dates);
//
//					reads= new String[lReads.size()];
//					reads=lReads.toArray(reads);
//
//										
//					
//					}
//					if(jArray.length()<1){
//						msgs= new String[0];
//						dates= new String[0];
//						reads= new String[0];
//					 }
//				}

			 
			 
			 
			 			 
}
