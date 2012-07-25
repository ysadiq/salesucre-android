package com.olit.sale;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.olit.sale.R;


public class LocationsLazyAdapter extends BaseAdapter {
	
	private Activity activity;
    private Integer[] ids;
    private String[] Streets;
//    private String[] Streets2;
//    private String[] AddressComments;
    private Float[] Longitudes;
    private Float[] Latitudes;
    private String[] DistrictNames;
    private String[] Cities;
    private ArrayList<ArrayList<String>> Phones;
    
    private static LayoutInflater inflater=null;
    public ImageLoaderMain imageLoader; 
    
    public LocationsLazyAdapter(Activity a, Integer[] id,String[] Street,String[] Street2,String[] AddressComment,Float[] Longitude, Float[] Latitude,String[] DistrictName, String[] City, ArrayList<ArrayList<String>> Phone) {
        activity = a;
        
        ids=id;
        Streets=Street;
//        Streets2=Street2;
//        AddressComments=AddressComment;
        Longitudes=Longitude;
        Latitudes=Latitude;
        DistrictNames=DistrictName;
        Cities=City;
        Phones=Phone;
        
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoaderMain(activity.getApplicationContext());
    }

    
    public int getCount() {
        return ids.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item2, null);

        TextView text=(TextView)vi.findViewById(R.id.text);
        TextView desctxt=(TextView)vi.findViewById(R.id.desc);
        TextView pricestxt=(TextView)vi.findViewById(R.id.price);
        ImageView image=(ImageView)vi.findViewById(R.id.image);

        text.setText(DistrictNames[position]);
        String s=Streets[position];
        if(s.length()>30){
        	desctxt.setText(s.substring(0, 30) + "...");
        }else{
        	desctxt.setText(s);
        }
        
        pricestxt.setText(Cities[position]);
        image.setImageResource(R.drawable.salelogo);
        
//        imageLoader.DisplayImage("http://api.olitintl.com/SaleSucreAPI/api/image.php/?width=60&height=60&cropratio=1:1&image=" + images[position], image);
        vi.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Intent intent = new Intent(activity, MapsActivity.class);
    			
    			ArrayList<ArrayList<String>> phonesGroup=Phones;
    			
    			ArrayList<String> galleryArr = phonesGroup.get(position);
    			
    			String[] stockArr = new String[galleryArr.size()];
    		    stockArr = galleryArr.toArray(stockArr);
    		    
//    			galleryArr = gallery.toArray(galleryArr);
    		    
//    			intent.putExtra(Extra.IMAGES, stockArr);
//    			intent.putExtra("names", names[position]);
//    			intent.putExtra("price", String.valueOf(ps[position]) + " LE");
    			intent.putExtra("Street", Streets[position]);
    			intent.putExtra("District", DistrictNames[position]);
            	intent.putExtra("Longitude", Longitudes[position]);
            	intent.putExtra("Latitude", Latitudes[position]);
            	intent.putExtra("Phones", Phones.get(position));
            	
            	  activity.startActivity(intent);
    			
    		}
    	});
        return vi;
    }

}
