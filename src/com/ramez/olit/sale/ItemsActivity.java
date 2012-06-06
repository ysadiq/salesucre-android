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
import org.json.JSONException;
import org.json.JSONObject;

import com.ramez.olit.sale.R;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ItemsActivity extends Activity {
	private JSONArray jArray;
	private Integer[] iStrings;
	private String[] mStrings;
	private String[] tStrings;
	private String[] cStrings;
	private String[] dStrings;
	private Integer[] pStrings;
	private ArrayList<ArrayList<String>> gallery;
	ArrayList<String> names=new ArrayList<String>();
	ArrayList<Integer> ids=new ArrayList<Integer>();
	ArrayList<String> imgs=new ArrayList<String>();
	ArrayList<ArrayList<String>> gal=new ArrayList<ArrayList<String>>();
	ArrayList<String> desc=new ArrayList<String>();
	ArrayList<String> cat=new ArrayList<String>();
	ArrayList<Integer> prices=new ArrayList<Integer>();
    ListView list;
    LazyAdapterItems adapter;
    ProgressDialog dialog;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        final int value = getIntent().getExtras().getInt("CAT_ID");

        Thread thread = new Thread()
        {
            @Override
            public void run() {
            	String f ;
        		f = getData(String.valueOf("http://api.olitintl.com/SaleSucreAPI/api/index.php/getMenus/by/Category/id/" + value));
        		try {
        			parseJSON(f);
        			
        			adapter=new LazyAdapterItems(ItemsActivity.this, mStrings,tStrings,cStrings,iStrings,pStrings,gal,dStrings);
        			
        			runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        	setContentView(R.layout.main);
                        	list=(ListView)findViewById(R.id.list);  
                			list.setAdapter(adapter);
                	        TextView title=(TextView) findViewById(R.id.titleText);
                	        title.setText(getIntent().getExtras().getString("title"));
                	        ImageButton locationMenu=(ImageButton) findViewById(R.id.locationImageButton);
                			locationMenu.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(ItemsActivity.this, LocationsActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									ItemsActivity.this.startActivity(intent);
									ItemsActivity.this.finish();
								}
							});
                			
                			ImageButton notificationsMenu=(ImageButton) findViewById(R.id.notificationsImageButton);
                			notificationsMenu.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(ItemsActivity.this, NotificationActivity.class);
									startActivity(intent);
									ItemsActivity.this.finish();
								}
							});
                			
                			ImageButton MoreMenu=(ImageButton) findViewById(R.id.moreImageButton);
                			MoreMenu.setOnClickListener(new OnClickListener() {
                				@Override
                				public void onClick(View v) {
                					Intent intent = new Intent(ItemsActivity.this, MoreActivity.class);
                					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                					startActivity(intent);
                					ItemsActivity.this.finish();
                				}
                			});
                			
                        }
                    });
        			
        		} catch (Exception e) {
        			e.printStackTrace();
        		}	

            }
        };
        thread.start();
        
        
		
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
		    	prices.add(row.getInt("Price"));
		    } catch (Exception e){
		    	prices.add(0);
		    }
		    try{
		    	JSONObject category=row.getJSONObject("CategoryObject");
		    	cat.add(category.getString("Name"));
		    } catch (Exception e){
		    	cat.add("");
		    }
		    
		    try{
		    	JSONObject description=row.getJSONObject("GalleryObject");
		    	desc.add(description.getString("Description"));
		    } catch (Exception e){
		    	desc.add("");
		    }
		    
		    
		    try{
		    	JSONObject image=row.getJSONObject("GalleryObject");
			    JSONArray imagesArr=image.getJSONArray("Images");
			    String imageName=imagesArr.getJSONObject(0).getString("Name");
			    imgs.add("http://api.olitintl.com/SaleSucreAPI/api/" + imageName);
			    ArrayList<String> imagesArrayList = new ArrayList<String>();
			    for(int ii = 0, count = imagesArr.length(); ii< count; ii++)
			    {
			        try {
			            JSONObject jsonObject = imagesArr.getJSONObject(ii);
			            imagesArrayList.add("http://api.olitintl.com/SaleSucreAPI/api/" + jsonObject.getString("Name").toString());

			        }
			        catch (JSONException e) {
			        	imagesArrayList.add("");
			        }
			    }
	            gal.add(imagesArrayList);
//			    gal.add("http://api.olitintl.com/SaleSucreAPI/api/" + imageName);	
		    }catch(Exception e){
		    	imgs.add("");
		    	gal.add(new ArrayList<String>());
		    }
		    
		}
    	
    	mStrings= new String[imgs.size()];
    	mStrings=imgs.toArray(mStrings);
    	
    	gallery= gal;
//    	gallery=gal.toArray(gallery);
    	
    	tStrings= new String[names.size()];
    	tStrings=names.toArray(tStrings);
    	
    	cStrings= new String[cat.size()];
    	cStrings=cat.toArray(cStrings);
    	
    	dStrings= new String[desc.size()];
    	dStrings=desc.toArray(dStrings);
    	
    	iStrings= new Integer[ids.size()];
    	iStrings=ids.toArray(iStrings);
    	
    	pStrings= new Integer[prices.size()];
    	pStrings=prices.toArray(pStrings);
    	
    	
	}
    
}
