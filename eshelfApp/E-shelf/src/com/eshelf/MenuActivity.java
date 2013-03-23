package com.eshelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.eshelf.util.Common;

public class MenuActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		final ImageButton bQRCode = (ImageButton) findViewById(R.id.qrcode);
		bQRCode.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						QRCodeReader.class);
				startActivity(intent);
			}
		});

		final ImageButton bLogOut = (ImageButton) findViewById(R.id.logout);
		bLogOut.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Common.getInstance().clear();
				Intent intent = new Intent(getApplicationContext(),
						AuthActivity.class);
				startActivity(intent);
			}
		});
		
		final ImageButton btItemsList = (ImageButton) findViewById(R.id.items_list);
		btItemsList.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ItemsListActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

}
