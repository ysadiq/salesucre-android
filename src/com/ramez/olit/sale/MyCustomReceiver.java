package com.ramez.olit.sale;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ramez.olit.sale.R;

public class MyCustomReceiver extends BroadcastReceiver {
	  NotificationManager myNotificationManager;


	  @Override
	   public void onReceive(Context context, Intent intent) {
	       
	    String FILENAME = "notications.json";
        String string = intent.getExtras().getString("com.parse.Data");
        String res = "[]";

	    
	    FileOutputStream fos;
		try {
			File file = new File(context.getFilesDir() + "/" + FILENAME);
			if (file.exists()){
				res=readInternalStoragePrivate(FILENAME,context);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
			String currentDateandTime = sdf.format(new Date());
			
			JSONArray jsarr=new JSONArray(res);
			JSONArray orgjsarr=new JSONArray(res);
			
			JSONObject jobj=new JSONObject(string);
			jobj.put("read", "0");
			jobj.put("date", currentDateandTime);
			
			jsarr.put(0,jobj);
			for (int i=0;i<orgjsarr.length();i++){
				jsarr.put(orgjsarr.get(i));
			}
			
			
		    handleMessage(context,intent,jobj.getString("msg"));  
		    
		    
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			String S=jsarr.toString();
			
			fos.write(S.getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  
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
	  
	  
	  private void handleMessage(Context context, Intent intent,String msg)
		{
		 GeneratNotification(context,msg);
		}
	  
	  private void GeneratNotification(Context context,String msg){
		  
		  myNotificationManager =	(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		  
		  CharSequence NotificationTicket = "Sale Sucre Notification";
		  CharSequence NotificationTitle = "You have new message from Sale Sucre";
		  CharSequence NotificationContent = msg;
		  long when = System.currentTimeMillis();
		  
		  		  
		  Notification notification =
		   new Notification(R.drawable.icon,
		     NotificationTicket, when);
		    


		  Intent notificationIntent = new Intent(context.getApplicationContext(),NotificationActivity.class);
		  
		  PendingIntent contentIntent =
		  PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, 0);
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  
//		  Intent notifyIntent = new Intent(Intent.ACTION_MAIN);
//		  notifyIntent.setClass(context.getApplicationContext(), C2DMActivity.class);

		  notification.setLatestEventInfo(context, NotificationTitle,
		    NotificationContent, contentIntent);
		  
		  myNotificationManager.notify(1, notification);

		  
		  
		}
	
	}