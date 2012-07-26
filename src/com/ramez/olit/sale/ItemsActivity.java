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
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemsActivity extends Activity {
	private JSONArray jArray;
	private Integer[] iStrings;
	private String[] mStrings;
	private String[] tStrings;
	private String[] cStrings;
	private String[] dStrings;
	private Integer[] pStrings;
//	private ArrayList<ArrayList<String>> gallery;
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
    
    int value;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.progress);
        setContentView(R.layout.main_new);
        LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
		MLL.setBackgroundResource(R.drawable.tabbaritem);
		
        value = getIntent().getExtras().getInt("CAT_ID");

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
//                        	setContentView(R.layout.main_new);
                        	
                        	RelativeLayout prog=(RelativeLayout) findViewById(R.id.progressView);
        	    			prog.setVisibility(View.GONE);
        	    			
                        	list=(ListView)findViewById(R.id.list);  
                			list.setAdapter(adapter);
                	        TextView title=(TextView) findViewById(R.id.titleText);
                	        title.setText(getIntent().getExtras().getString("title"));
                	        
                	        MenuSetter m=new MenuSetter();
                			m.setMenuItems(ItemsActivity.this,R.id.menuLinearLayout);
                			
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

	private JSONArray reverse(JSONArray a) throws JSONException{
        JSONArray _a = new JSONArray();
        for(int i = a.length()-1; i >= 0; i--){
                _a.put(a.getJSONObject(i));
        }
        return _a;
}
	
	
    private void parseJSON(String jString) throws Exception {
    	
		jArray = new JSONArray(jString);

		if(value==8) 
		{jArray=reverse(jArray); }
		
		
		for (int i = 0; i < jArray.length() ; i++) {
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
		    	Display display = getWindowManager().getDefaultDisplay();
		    	int width = (int) (display.getWidth()  * 0.90);
		    	int height;
		    	
		    	if(display.getHeight()==320 || display.getHeight()<320){
		    		height = (int) (width / 1.875);		    		
		    	}else{
		    		height = (int) (width / 1.64);
		    	}

				width=(int)Utils.convertDpToPixel(width,this);
				height=(int)Utils.convertDpToPixel(height,this);
				
				
		    	JSONObject image=row.getJSONObject("GalleryObject");
			    JSONArray imagesArr=image.getJSONArray("Images");
			    
			    try{
			    	String imageName=imagesArr.getJSONObject(0).getString("Name");//.replace("images/Menuitems/", "");			    	
			    	imgs.add("http://api.olitintl.com/SaleSucreAPI/api/imagehandler/getimage.php?width=60&height=60&oftype=2&image=" + imageName);			    
			    	
			    }catch(Exception e){
			    	if (imagesArr.length()>0){
//		            JSONObject jsonObject = imagesArr.getString(0);
		            imgs.add("http://api.olitintl.com/SaleSucreAPI/api/imagehandler/getimage.php?width=60&height=60&oftype=2&image=" + imagesArr.getString(0)); //.replace("images/Menuitems/", "")
			    	}else{
			    		JSONObject catObj = row.getJSONObject("CategoryObject");
			    		JSONObject imgObj=catObj.getJSONObject("ImageObject");
			    		imgs.add("http://api.olitintl.com/SaleSucreAPI/api/imagehandler/getimage.php?width=60&height=60&oftype=2&image=" + imgObj.getString("Name"));
//			    		imgs.add("");
			    	}
			    }


			    ArrayList<String> imagesArrayList = new ArrayList<String>();
			    for(int ii = 0, count = imagesArr.length(); ii< count; ii++)
			    {
			        try {
			            JSONObject jsonObject = imagesArr.getJSONObject(ii);
			            imagesArrayList.add("http://api.olitintl.com/SaleSucreAPI/api/imagehandler/getimage.php?width=" + width + "&height=" + height + "&oftype=1&gravity=southeast&image=" + jsonObject.getString("Name")); //.replace("images/Menuitems/", "")

			        }
			        catch (JSONException e) {
			        	imagesArrayList.add("");
			        }
			    }
			    if (imagesArr.length()<1){
			    	imagesArrayList.add("");
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
    	
//    	gallery= gal;
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
