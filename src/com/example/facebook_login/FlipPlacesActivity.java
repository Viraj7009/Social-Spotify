package com.example.facebook_login;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facebook_login.gallery.SimpleImageActivity;
import com.squareup.picasso.Picasso;

@SuppressWarnings("deprecation")
public class FlipPlacesActivity extends Activity{
	
	public int currentimageindex=0;
	private ProgressDialog pDialog;
  ImageView imageView, imageView1, imageView2, imageView3;
  ImageButton openmap ;
  double lat, longi;
    
  List<String> splitter;
  
  private TextView dispData, titleView,category;
	private int pics[]={R.drawable.antartica1, R.drawable.antartica2, R.drawable.antartica3,
	        R.drawable.antartica4, R.drawable.antartica5,R.drawable.antartica6, R.drawable.antartica7,
	        R.drawable.antartica8, R.drawable.antartica9
	        };
	
	
//  ArrayList<Bitmap> imagepics = new ArrayList<Bitmap>();
//  ArrayList<ImageView> imagearray = new ArrayList<ImageView>();
//	ViewFlipper viewFlipper;
//	ImageSwitcher iSwitcher;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_listview);
        dispData = (TextView)findViewById(R.id.textView1);
//        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        
        Bundle bundle = getIntent().getExtras();
        titleView = (TextView)findViewById(R.id.textView2);
        category = (TextView)findViewById(R.id.textView3);

        String title = bundle.getString("Title");
        String thumbnailurl = bundle.getString("URL");
        String year = bundle.getString("Category");
        String rating = bundle.getString("Checkins");
        String genre = bundle.getString("Description");
        String images = bundle.getString("Images");
        
        final List<String> splitter = Arrays.asList(images.split(","));
//        Log.d("Splitter", splitter.toString());
        for(int i=0;i<splitter.size();i++){
        	
        	Log.d("Splitter", splitter.get(i).toString());
        }
        imageView = (ImageView)findViewById(R.id.ImageView01);
        Picasso.with(getBaseContext()).load(splitter.get(0).toString()).into(imageView);
        imageView1 = (ImageView)findViewById(R.id.ImageView02);
        Picasso.with(getBaseContext()).load(splitter.get(1).toString()).into(imageView1);
        imageView2 = (ImageView)findViewById(R.id.ImageView03);
        Picasso.with(getBaseContext()).load(splitter.get(2).toString()).into(imageView2);
        imageView3 = (ImageView)findViewById(R.id.ImageView04);
        Picasso.with(getBaseContext()).load(splitter.get(3).toString()).into(imageView3);
        
        imageView.setOnClickListener(new OnClickListener() {
            

			@Override
            public void onClick(View v) {
            	Intent intent = new Intent(FlipPlacesActivity.this, SimpleImageActivity.class);
            	intent.putExtra("Selected_image", splitter.get(0).toString());
            	startActivity(intent);
            }
        });
        
        imageView1.setOnClickListener(new OnClickListener() {
            

			@Override
            public void onClick(View v) {
            	Intent intent = new Intent(FlipPlacesActivity.this, SimpleImageActivity.class);
            	intent.putExtra("Selected_image1", splitter.get(1).toString());
            	startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new OnClickListener() {
    

	@Override
    public void onClick(View v) {
    	Intent intent = new Intent(FlipPlacesActivity.this, SimpleImageActivity.class);
    	intent.putExtra("Selected_image2", splitter.get(2).toString());
    	startActivity(intent);
    }
});

        imageView3.setOnClickListener(new OnClickListener() {
    

	@Override
    public void onClick(View v) {
    	Intent intent = new Intent(FlipPlacesActivity.this, SimpleImageActivity.class);
    	intent.putExtra("Selected_image3", splitter.get(3).toString());
    	startActivity(intent);
    }
});

        lat=bundle.getDouble("Latitude");
        longi=bundle.getDouble("Longitude");
        titleView.setText(title);
        category.setText(year);
        

        dispData.setMovementMethod(new ScrollingMovementMethod());
        dispData.setText(genre);
        final String uri = String.format("geo:%f,%f?z=%d&q=%f,%f (%s)",lat, longi, 15, lat, longi, title);
        openmap = (ImageButton) findViewById(R.id.openMap);
        openmap.setOnClickListener(new View.OnClickListener()
        {
        public void onClick(View arg0){
        Intent i = new
        Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(i);
        pDialog = new ProgressDialog(FlipPlacesActivity.this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...Please Wait");
		pDialog.show();
        }
        });

        
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    
    public class ImageAdapter extends BaseAdapter {

        private Context ctx;
        int imageBackground;
       
        public ImageAdapter(Context c) {
            ctx = c;
            TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }

        @Override
        public int getCount() {
           
            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {
           
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
           
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(ctx);
//            iv.setImageResource((Integer)imagepics.get(arg0));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(250,120));
            iv.setBackgroundResource(imageBackground);
            return iv;
        }

    }
    
    public void onClick(View v) {
        
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
      }
    
    @Override
    public void onResume() {
        super.onResume();
    
        
    }

    @Override
    public void onPause() {
        super.onPause();
    
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
      
    }

    public void openOnMap(){
    	openmap = (ImageButton)findViewById(R.id.openMap);
    	String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%d&q=%f,%f (%s)",40.6944, -73.9865, 10, 40.6944, 73.9865, "NYU Poly");
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    	getBaseContext().startActivity(intent);
    }



}
