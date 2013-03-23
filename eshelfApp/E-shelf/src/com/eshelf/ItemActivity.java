package com.eshelf;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshelf.services.ServicePost;
import com.eshelf.services.ServiceRequest;
import com.eshelf.util.Common;

public class ItemActivity extends Activity {
	String mProductID;

	TextView mLoading;
	RelativeLayout mItensDetails;
	TextView mTitle;
	Button btFavorite;
	Button btMenu;
	Button btOtherPic;
	ImageView mImageView;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		mItensDetails = (RelativeLayout) findViewById(R.id.item_detail);
		mLoading = (TextView) findViewById(R.id.loading);
		mTitle = (TextView) findViewById(R.id.title);
		btFavorite = (Button) findViewById(R.id.favorite);
		btMenu = (Button) findViewById(R.id.go_menu);
		btOtherPic = (Button) findViewById(R.id.try_again);
		mImageView = (ImageView) findViewById(R.id.first_image);
		
		Bundle extras = getIntent().getExtras();

		mProductID = extras.getString("productID", "");
		mProductID.trim();
		Log.d("PH", "(" + mProductID + ")");
		ServiceRequest teste = new ServiceRequest(Common.SV_ITEM_DETAILS + "/"
				+ mProductID, Common.NO_TOKEN) {
			public void work(String result) {
				parserJson(result);
			}

		};
		teste.execute();

		btFavorite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ServicePost favorite = new ServicePost(Common.SV_ADD_BOOKARK, Common.WITH_TOKEN,"{\"item_id\":\"" + mProductID +  "\"}") {
					public void work(String result) {
						Log.d("PH","Favoritei!?");
					}
					
				};
				favorite.execute();
			}
		});
		btMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						QRCodeReader.class);
				startActivity(intent);
			}
		});
		btOtherPic.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						QRCodeReader.class);
				startActivity(intent);
			}
		});
		
	}

	private void parserJson(String result) {
		Log.d("PH", "UNBELIVIBLE!" + result);
		JSONObject teste= null;
		try {
			teste = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mLoading.setVisibility(View.GONE);
		mItensDetails.setVisibility(View.VISIBLE);
		try {
			mTitle.setText(teste.getString("title"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new DownloadImageTask(mImageView)
			.execute(teste.getString("thumbnail"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			mImageView.setImageDrawable(LoadImageFromWebOperations(teste.getString("permalink")));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_item, menu);
		return true;
	}

}
