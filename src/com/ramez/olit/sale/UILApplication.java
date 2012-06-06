package com.ramez.olit.sale;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;

public class UILApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "3cNLknqbAqH5DYlGMDcCcbix0nxikrz5aB8E1dHL", "W5IPPoUuS7EMMXuIsr70PFqhfVKkvHto4yXaDlK4"); 

		
//		ParseUser.enableAutomaticUser();
//		ParseACL defaultACL = new ParseACL();
//
//		ParseACL.setDefaultACL(defaultACL, true);
		
		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.threadPoolSize(3)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCacheSize(1500000) // 1.5 Mb
			.discCacheSize(50000000) // 50 Mb
			.httpReadTimeout(10000) // 10 s
			.denyCacheImageMultipleSizesInMemory()
			.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().enableLogging(); // Not necessary in common
	}
}