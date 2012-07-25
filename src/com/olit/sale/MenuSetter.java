package com.olit.sale;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.olit.sale.R;

public class MenuSetter {
	public void setMenuItems(final Activity a,Integer notFunctioning){
		if (!(notFunctioning==R.id.moreLinearLayout)){
			LinearLayout MoreMenu=(LinearLayout) a.findViewById(R.id.moreLinearLayout);
			MoreMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent( a.getApplicationContext(), MoreActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
				a.finish();
				}
			});
			ImageButton MoreImage=(ImageButton) a.findViewById(R.id.moreImageButton);
			MoreImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), MoreActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
		}
		
		
		if (!(notFunctioning==R.id.notificationLinearLayout)){
			
			LinearLayout notificationsMenu=(LinearLayout) a.findViewById(R.id.notificationLinearLayout);
			notificationsMenu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), NotificationActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
			
			ImageButton notificationsImage=(ImageButton) a.findViewById(R.id.notificationsImageButton);
			notificationsImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), NotificationActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
		}
		
		
		if (!(notFunctioning==R.id.locationLinearLayout)){
			
			LinearLayout locationMenu=(LinearLayout) a.findViewById(R.id.locationLinearLayout);
			locationMenu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), LocationsActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
			
			ImageButton locationImage=(ImageButton) a.findViewById(R.id.locationImageButton);
			locationImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), LocationsActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
		}
		
		
		if (!(notFunctioning==R.id.menuLinearLayout)){
			
			LinearLayout menuMenu=(LinearLayout) a.findViewById(R.id.menuLinearLayout);
			menuMenu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
			
			ImageButton menuImage=(ImageButton) a.findViewById(R.id.menuImageButton);
			menuImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(a.getApplicationContext(), MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.startActivity(intent);
					a.finish();
				}
			});
		}
	}
}
