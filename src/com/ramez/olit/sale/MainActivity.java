package com.ramez.olit.sale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.parse.PushService;

public class MainActivity extends Activity {
	private JSONArray jArray;
	private Integer[] iStrings;
	private String[] mStrings;
	private String[] tStrings;
	private String[] dStrings;
	ArrayList<String> names=new ArrayList<String>();
	ArrayList<Integer> ids=new ArrayList<Integer>();
	ArrayList<String> imgs=new ArrayList<String>();
	ArrayList<String> desc=new ArrayList<String>();
    ListView list;
    LazyAdapter adapter;
       
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getIntent().getAction()==null)) {
        	PushService.subscribe(this, "", NotificationActivity.class);
        	setContentView(R.layout.splash);
    } else{
    		setContentView(R.layout.main_new);
    		LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
    		MLL.setBackgroundResource(R.drawable.tabbaritem);
    }
        
        if (isNetworkAvailable()==false){
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setCancelable(true);
//        	builder.setIcon(R.drawable.dialog_question);
        	builder.setTitle("This application requires active internet connection");
        	builder.setInverseBackgroundForced(true);
        	builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
        	  @Override
        	  public void onClick(DialogInterface dialog, int which) {
        	    dialog.dismiss();
        	    System.exit(0);
        	    return;
        	  }
        	});
        	
        	AlertDialog alert = builder.create();
        	alert.show();
        	return;
        }
        
        
        Thread thread = new Thread()
        {
            @Override
            public void run() {
            	String f ;
        		f = getData(String.valueOf("http://api.olitintl.com/SaleSucreAPI/api/index.php/get/Category/1"));
        		try {
        			parseJSON(f);
        			adapter=new LazyAdapter(MainActivity.this, mStrings,tStrings,dStrings,iStrings);
        			
        			runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        	setContentView(R.layout.main_new);
                            LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
                    		MLL.setBackgroundResource(R.drawable.tabbaritem);
                    		
                        	RelativeLayout prog=(RelativeLayout) findViewById(R.id.progressView);
        	    			prog.setVisibility(View.GONE);
        	    			
                        	list=(ListView)findViewById(R.id.list);  
                			list.setAdapter(adapter);
                 			
                			MenuSetter m=new MenuSetter();
                			m.setMenuItems(MainActivity.this,R.id.menuLinearLayout);
                        }
                    });
        			
        		} catch (Exception e) {
        			e.printStackTrace();
        		}	

            }
        };
        thread.start();
       
    }
    
    @Override
    public void onDestroy()
    {
//    	adapter.imageLoader.clearCache();
        list.setAdapter(null);
        super.onDestroy();
    }
    
    
    public String getData(String URL) {
    	HttpClient httpclient = new DefaultHttpClient();
 	    HttpGet httpget = new HttpGet(URL);
 	    HttpResponse response = null;
	try {
		response = httpclient.execute(httpget);
		Log.d("resp", response.toString());
	       InputStream ips  = response.getEntity().getContent();
	       BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));
	       if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
	       {
//	           throw new Exception(response.getStatusLine().getReasonPhrase());
	       }
	       StringBuilder sb = new StringBuilder();
	       String s;
	       while(true )
	       {
	           s = buf.readLine();
	           if(s==null || s.length()==0)
	               break;
	           sb.append(s);

	       }
	       buf.close();
	       ips.close();
	       return sb.toString();
	} catch (IOException e) {
		e.printStackTrace();
		return e.getMessage();
	}
       
    }

    
    private void parseJSON(String jString) throws Exception {
    	
		jArray = new JSONArray(jString);
		for (int i = 0; i < jArray.length(); i++) {
		    JSONObject row = jArray.getJSONObject(i);
		    ids.add(row.getInt("Id")) ;
		    names.add(row.getString("Name"));
		    try{
		    	desc.add(row.getString("Description"));
		    } catch (Exception e){
		    	desc.add("");
		    }
		    
		    
		    JSONObject image=row.getJSONObject("ImageObject");
		    String imageName=image.getString("Name");
		    imgs.add("http://api.olitintl.com/SaleSucreAPI/api/imagehandler/getimage.php?width=60&height=60&oftype=2&image=" + imageName); //.replace("images/Categories/", "")
		}
    	
    	mStrings= new String[imgs.size()];
    	mStrings=imgs.toArray(mStrings);
    	
    	tStrings= new String[names.size()];
    	tStrings=names.toArray(tStrings);
    	
    	dStrings= new String[desc.size()];
    	dStrings=desc.toArray(dStrings);
    	
    	iStrings= new Integer[ids.size()];
    	iStrings=ids.toArray(iStrings);
    	
	}
    

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    
}