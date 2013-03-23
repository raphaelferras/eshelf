package com.eshelf;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.eshelf.util.Common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity implements Runnable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new ScheduledThreadPoolExecutor(1).schedule(this, 1, TimeUnit.SECONDS);
	}


	public void run() {
		if(Common.getInstance().accessToken.length() == 0){
			Intent intent = new Intent(this, AuthActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
		}
		finish();
	}
    
}
