package com.olit.sale;


import java.util.ArrayList;



import com.olit.sale.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class LazyAdapterItems extends BaseAdapter {
    
    private Activity activity;
//    private Integer[] ids;
    private String[] images;
    private ArrayList<ArrayList<String>> gallery;
    private String[] names;
    private String[] desc;
    private String[] cat;
    private Integer[] ps;
    private static LayoutInflater inflater=null;
    public ImageLoaderMain imageLoader; 
    
    public LazyAdapterItems(Activity a, String[] d,String[] t,String[] ct,Integer[] iids,Integer[]prices, ArrayList<ArrayList<String>> gal,String[] descs) {
        activity = a;
        images=d;
        gallery=gal;
        names=t;
        cat=ct;
//        ids=iids;
        ps=prices;
        desc=descs;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoaderMain(activity.getApplicationContext());
    }

    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item2, null);

        TextView text=(TextView)vi.findViewById(R.id.text);
        TextView desctxt=(TextView)vi.findViewById(R.id.desc);
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        TextView pricestxt=(TextView)vi.findViewById(R.id.price);
        text.setText(names[position]);
        desctxt.setText(cat[position]);
        pricestxt.setText(String.valueOf(ps[position]) + " LE");
        imageLoader.DisplayImage(images[position], image);
        vi.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Intent intent = new Intent(activity, ImagePagerActivity.class);
    			
    			ArrayList<ArrayList<String>> galleryGroup=gallery;
    			
    			ArrayList<String> galleryArr = galleryGroup.get(position);
    			
    			String[] stockArr = new String[galleryArr.size()];
    		    stockArr = galleryArr.toArray(stockArr);
    		    
//    			galleryArr = gallery.toArray(galleryArr);
    		    
    			intent.putExtra(Extra.IMAGES, stockArr);
    			intent.putExtra("names", names[position]);
    			intent.putExtra("price", String.valueOf(ps[position]) + " LE");
    			intent.putExtra("cat", cat[position]);
    			intent.putExtra("desc", desc[position]);
//            	  intent.putExtra(Extra.IMAGE_POSITION, ids[position]);
            	  activity.startActivity(intent);
    			
    		}
    	});
        return vi;
    }
}