package com.ramez.olit.sale;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class MapsActivity extends MapActivity {
	private MapController myMapController;
	private PopupWindow pw;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        ImageButton MainMenu=(ImageButton) findViewById(R.id.menuImageButton);
		MainMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MapsActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				MapsActivity.this.startActivity(intent);
				MapsActivity.this.finish();
			}
		});
		
		
		ImageButton notificationsMenu=(ImageButton) findViewById(R.id.notificationsImageButton);
		notificationsMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MapsActivity.this, NotificationActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				MapsActivity.this.finish();
			}
		});
		
		ImageButton MoreMenu=(ImageButton) findViewById(R.id.moreImageButton);
		MoreMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MapsActivity.this, MoreActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				MapsActivity.this.finish();
			}
		});
		
		
        final MapView mapsView = (MapView) findViewById(R.id.mapsView);
        final List<Overlay> mapOverlays = mapsView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.red);
        final MapsItemizedOverlay itemizedoverlay = new MapsItemizedOverlay(drawable, this);
        TextView T=(TextView) findViewById(R.id.titleText);
        T.setText(getIntent().getExtras().getString("District"));
        
        TextView T1=(TextView) findViewById(R.id.district);
        T1.setText(getIntent().getExtras().getString("District"));
        
        TextView T2=(TextView) findViewById(R.id.desc);
        T2.setText(getIntent().getExtras().getString("Street"));
        
        ImageButton callButon=(ImageButton) findViewById(R.id.callImageButton);
        callButon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initiatePopupWindow();
				
			}
		});
        
        GeoPoint point = new GeoPoint((int) (getIntent().getExtras().getFloat("Longitude") * 1e6), (int) (getIntent().getExtras().getFloat("Latitude") * 1e6));
        
        OverlayItem overlayitem = new OverlayItem(point, getIntent().getExtras().getString("District"), getIntent().getExtras().getString("Street"));
        mapOverlays.clear();        
        
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);

        
        myMapController = mapsView.getController();
        myMapController.animateTo(point);
        myMapController.setZoom(17);
        
        
        
	}

	
	
	private void initiatePopupWindow() {
	    try {
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) MapsActivity.this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.phonelayout,
	                (ViewGroup) findViewById(R.id.popup_element));
	        // create a 300px width and 470px height PopupWindow
	        
	        Display display = getWindowManager().getDefaultDisplay();
	        
	        	        
	        pw = new PopupWindow(layout, display.getWidth(), 470, true);
	        // display the popup in the center
	        pw.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
	 
	        
	        ArrayList<String> Phones= getIntent().getExtras().getStringArrayList("Phones");
	        

	        if (Phones.size()>0){
	        	Button B1=(Button) layout.findViewById(R.id.button1);
	        	B1.setText(Phones.get(0).toString());
	        }
	        if (Phones.size()>1){
	        	Button B2=(Button) layout.findViewById(R.id.Button01);
	        	B2.setText(Phones.get(1).toString());
	        }
	        
	        
//	        mResultText = (TextView) layout.findViewById(R.id.server_status_text);
	        Button cancelButton = (Button) layout.findViewById(R.id.cancelButton);
//	        makeBlack(cancelButton);
	        cancelButton.setOnClickListener(cancel_button_click_listener);
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	 
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
	    public void onClick(View v) {
	        pw.dismiss();
	    }
	};
	
	
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
