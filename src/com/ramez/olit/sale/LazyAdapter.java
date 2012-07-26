package com.ramez.olit.sale;


import com.ramez.olit.sale.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private Integer[] ids;
    private String[] images;
    private String[] names;
    private String[] desc;
    private static LayoutInflater inflater=null;
    public ImageLoaderMain imageLoader; 
    
    public LazyAdapter(Activity a, String[] d,String[] t,String[] ds,Integer[] iids) {
        activity = a;
        images=d;
        names=t;
        desc=ds;
        ids=iids;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoaderMain(activity.getApplicationContext());
//        imageLoader.clearCache();
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
            vi = inflater.inflate(R.layout.item, null);

        TextView text=(TextView)vi.findViewById(R.id.text);
        TextView desctxt=(TextView)vi.findViewById(R.id.desc);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text.setText(names[position]);
        desctxt.setText(desc[position]);
        imageLoader.DisplayImage(images[position], image);
        vi.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Intent intent = new Intent(activity, ItemsActivity.class);
            	  intent.putExtra("CAT_ID", ids[position]);
            	  intent.putExtra("title", names[position]);
            	  activity.startActivity(intent);
    			
    		}
    	});
        return vi;
    }
}