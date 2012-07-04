package com.ramez.olit.sale;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

public class ImagePagerActivity extends BaseActivity {

	private ViewPager pager;

	private DisplayImageOptions options;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_image_pager_new);
        LinearLayout MLL=(LinearLayout) findViewById(R.id.menuLinearLayout);
		MLL.setBackgroundResource(R.drawable.tabbaritem);
		
		Bundle bundle = getIntent().getExtras();
		String[] imageUrls = bundle.getStringArray(Extra.IMAGES);
		int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);
		String names = getIntent().getExtras().getString("names");
		TextView name=(TextView) findViewById(R.id.names);
		TextView pricetag=(TextView) findViewById(R.id.pricetag);
		pricetag.setText(getIntent().getExtras().getString("price"));
		TextView category=(TextView) findViewById(R.id.category);
		category.setText(getIntent().getExtras().getString("cat"));
		TextView description=(TextView) findViewById(R.id.description);
		description.setText(getIntent().getExtras().getString("desc"));
		TextView Title=(TextView) findViewById(R.id.titleText);
		Title.setText(names);
		
		MenuSetter m=new MenuSetter();
		m.setMenuItems(ImagePagerActivity.this,R.id.menuLinearLayout);
		
		String subCat;
		if (getIntent().getExtras().getString("cat").length() > 14){
			subCat=getIntent().getExtras().getString("cat").substring(0, 14) + "...";
		}else{
			subCat=getIntent().getExtras().getString("cat");
		}
		subCat=getIntent().getExtras().getString("cat");
		category.setText(subCat);
		
		name.setText(names);
		
		
		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUrl(R.drawable.stub)
			.cacheOnDisc()
			.decodingType(DecodingType.MEMORY_SAVING)
			.build();

		ImagePagerAdapter pagerAdapter=new ImagePagerAdapter(imageUrls);
		pager = (ViewPager) findViewById(R.id.pager);
		
		Display display = getWindowManager().getDefaultDisplay();
		
		int width = (int) (display.getWidth()  * 0.85);
		int height = (int) (width / 1.875);
		pager.setLayoutParams(new android.widget.LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height));
		
		
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(pagerPosition);

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				LinearLayout dotter=(LinearLayout) findViewById(R.id.dotter);
				for (int p=0;p<dotter.getChildCount();p++){
					ImageView dot=(ImageView) dotter.getChildAt(p);
					dot.setImageResource(R.drawable.dotdark);
				}
				ImageView dot=(ImageView) dotter.getChildAt(arg0);
				dot.setImageResource(R.drawable.dotlight);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		for (int p=0;p<pagerAdapter.getCount();p++){
			ImageView dot=new ImageView(this);
			if (p==0){
				dot.setImageResource(R.drawable.dotlight);
			}else{
				dot.setImageResource(R.drawable.dotdark);
			}
			
			dot.setPadding(3, 0, 3, 0);
			LinearLayout dotter=(LinearLayout) findViewById(R.id.dotter);
			dot.setId(p);
			dotter.addView(dot);
		}
	
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(View view, final int position) {
			final FrameLayout imageLayout = (FrameLayout) inflater.inflate(R.layout.item_pager_image, null);
			final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			
			
			
			
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			imageLoader.displayImage(images[position], imageView, options, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted() {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed() {
					spinner.setVisibility(View.GONE);
					imageView.setImageResource(R.drawable.stub);
				}

				@Override
				public void onLoadingComplete() {
					spinner.setVisibility(View.GONE);
					BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
					Display display = getWindowManager().getDefaultDisplay();
					
					int width = (int) (display.getWidth()  * 0.90);
					int height;
					
					if(display.getHeight()==320 || display.getHeight()<320){
			    		height = (int) (width / 1.875);		    		
			    	}else{
			    		height = (int) (width / 1.64);
			    	}
					
					
					pager.setLayoutParams(new android.widget.LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height));

					
					imageView.setImageBitmap(Bitmap.createScaledBitmap(ImageHelper.getRoundedCornerBitmap(drawable.getBitmap(),20),width,height,true));
					
					
//					LinearLayout dotter=(LinearLayout) findViewById(R.id.dotter);
//					ImageView dot=(ImageView) dotter.getChildAt(0);
//					dot.setImageResource(R.drawable.dotlight);
				}
			});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}

	}
}
