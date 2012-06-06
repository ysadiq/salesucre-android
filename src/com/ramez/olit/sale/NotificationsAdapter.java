package com.ramez.olit.sale;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class NotificationsAdapter extends BaseAdapter {
	
	private Activity activity;

    private String[] msgs;
    private String[] dates;
    private String[] reads;
    
    
    private static LayoutInflater inflater=null;
    public ImageLoaderMain imageLoader; 
    
    public NotificationsAdapter(Activity a, String[] msg,String[] date,String[] read) {
        activity = a;
        msgs=msg;
        dates=date;
        reads=read;
       
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoaderMain(activity.getApplicationContext());
    }

    
    public int getCount() {
        return msgs.length;
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
            vi = inflater.inflate(R.layout.notificationitem, null);

        TextView text=(TextView)vi.findViewById(R.id.text);
        TextView dateitem=(TextView)vi.findViewById(R.id.dateitem);

        ImageView image=(ImageView)vi.findViewById(R.id.image);

        text.setText(msgs[position]);
        dateitem.setText(dates[position]);
        if (Integer.parseInt(reads[position].toString())==0){
        	image.setImageResource(R.drawable.isunread);
        }else{
        	image.setImageResource(R.drawable.isread);
        }
                

        return vi;
    }

}
