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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LocationsActivity extends Activity {
	private JSONArray jArray;
	LocationsLazyAdapter adapter;
	ListView list;
	
	private ArrayList<Integer> ids=new ArrayList<Integer>();
    private ArrayList<String> Streets=new ArrayList<String>();
    private ArrayList<String> Streets2=new ArrayList<String>();
    private ArrayList<String> AddressComments=new ArrayList<String>();
    private ArrayList<Float> Longitudes=new ArrayList<Float>();
    private ArrayList<Float> Latitudes=new ArrayList<Float>();
    private ArrayList<String> DistrictNames=new ArrayList<String>();
    private ArrayList<String> Cities=new ArrayList<String>();
    private ArrayList<ArrayList<String>> Phones=new ArrayList<ArrayList<String>>();
    
    private Integer[] mids;
    private String[] mStreets;
    private String[] mStreets2;
    private String[] mAddressComments;
    private Float[] mLongitudes;
    private Float[] mLatitudes;
    private String[] mDistrictNames;
    private String[] mCities;
    private ArrayList<ArrayList<String>> mPhones;
    
    
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.progress);
	        Thread thread = new Thread()
	        {
	            @Override
	            public void run() {
	            	String f ;
	        		f = getData(String.valueOf("http://api.olitintl.com/SaleSucreAPI/api/index.php/getStores/1"));
	        		try {
	        			parseJSON(f);
	        			adapter=new LocationsLazyAdapter(LocationsActivity.this, mids,mStreets,mStreets2,mAddressComments,mLongitudes,mLatitudes,mDistrictNames,mCities,mPhones);
	        			
	        			runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                        	setContentView(R.layout.main);
	                        	list=(ListView)findViewById(R.id.list);  
	                			list.setAdapter(adapter);
	                			TextView Title=(TextView) findViewById(R.id.titleText);
	                			Title.setText("Locations");
	                			LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
	                			MLL.setBackgroundDrawable(null);
	                			LinearLayout LLL=(LinearLayout) findViewById(R.id.locationLinearLayout);
	                			LLL.setBackgroundResource(R.drawable.tabbaritem);
	                			ImageButton MainMenu=(ImageButton) findViewById(R.id.menuImageButton);
	                			MainMenu.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(LocationsActivity.this, MainActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										LocationsActivity.this.startActivity(intent);
										LocationsActivity.this.finish();
									}
								});
	                			
	                			ImageButton notificationsMenu=(ImageButton) findViewById(R.id.notificationsImageButton);
	                			notificationsMenu.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(LocationsActivity.this, NotificationActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										LocationsActivity.this.startActivity(intent);
										LocationsActivity.this.finish();
									}
								});
	                			
	                			ImageButton MoreMenu=(ImageButton) findViewById(R.id.moreImageButton);
	                			MoreMenu.setOnClickListener(new OnClickListener() {
	                				@Override
	                				public void onClick(View v) {
	                					Intent intent = new Intent(LocationsActivity.this, MoreActivity.class);
	                					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                					startActivity(intent);
	                					LocationsActivity.this.finish();
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
//		           throw new Exception(response.getStatusLine().getReasonPhrase());
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
			    
			    
			    JSONObject AddressObject=row.getJSONObject("AddressObject");
			    
			    try{
			    	Streets.add(AddressObject.getString("Street"));
			    }catch (Exception e){
			    	Streets.add("");
			    }
			    
			    try{
			    	Streets2.add(AddressObject.getString("Street2"));			    	
			    }catch (Exception e){
			    	Streets2.add("");
			    }

			    try{
			    	AddressComments.add(AddressObject.getString("AddressComments"));
			    }catch(Exception e){
			    	AddressComments.add("");
			    }
			    
			    
			    JSONObject DistrictObject=AddressObject.getJSONObject("DistrictObject");
			    try{
			    	DistrictNames.add(DistrictObject.getString("Name"));
			    }catch(Exception e){
			    	DistrictNames.add("");
			    }
			    

			    JSONObject CityObject=DistrictObject.getJSONObject("CityObject");
			    Cities.add(CityObject.getString("Name"));
			    
			    JSONObject LocationObject=AddressObject.getJSONObject("LocationObject");
			    Longitudes.add((float) LocationObject.getDouble("Longitude"));
			    Latitudes.add((float) LocationObject.getDouble("Latitude"));
			    
			    JSONArray PhonesObjectArray=row.getJSONArray("ContactChannelObject");
			    	
			    ArrayList<String> Phone=new ArrayList<String>();
			    	for(int ii=0;ii<PhonesObjectArray.length();ii++){
			    	JSONObject PhoneObject = PhonesObjectArray.getJSONObject(ii);
			    	Phone.add(PhoneObject.getString("Description"));
			    }
			    
			    	Phones.add(Phone);
			    
//			    try{
//			    	desc.add(row.getString("Description"));
//			    } catch (Exception e){
//			    	desc.add("");
//			    }
//			    
//			    
//			    JSONObject image=row.getJSONObject("ImageObject");
//			    String imageName=image.getString("Name");
//			    imgs.add("http://api.olitintl.com/SaleSucreAPI/api/" + imageName);
//			}
//	    	
			mids= new Integer[ids.size()];
			mids=ids.toArray(mids);

			mStreets= new String[Streets.size()];
			mStreets=Streets.toArray(mStreets);
	    	
			mStreets2= new String[Streets2.size()];
			mStreets2=Streets2.toArray(mStreets2);
	    	
			mAddressComments= new String[AddressComments.size()];
			mAddressComments=AddressComments.toArray(mAddressComments);
	    	
			mLongitudes= new Float[Longitudes.size()];
			mLongitudes=Longitudes.toArray(mLongitudes);
			
			mLatitudes= new Float[Latitudes.size()];
			mLatitudes=Latitudes.toArray(mLatitudes);
			
			mDistrictNames= new String[DistrictNames.size()];
			mDistrictNames=DistrictNames.toArray(mDistrictNames);
			
			mCities= new String[Cities.size()];
			mCities=Cities.toArray(mCities);
			
			mPhones= Phones;
			}
		}
}
	    
