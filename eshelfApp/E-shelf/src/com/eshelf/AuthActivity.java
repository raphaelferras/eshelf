package com.eshelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eshelf.util.Common;

public class AuthActivity extends Activity {

	public static final String CLIENT_ID = "8254651353206319";
	public static String TOKEN = "8254651353206319";

	/*
	 * onPageFinished: http://developers.mercadolibre.com/#
	 * access_token=APP_USR-
	 * 8254651353206319-032204-aa0701a4b216286b50e1284a14868796__N_H__
	 * -134905586& expires_in=10800& user_id=134905586&
	 * domains=developers.mercadolibre.com
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);

		WebView webview = (WebView) findViewById(R.id.mywebview);
		webview.loadUrl("https://auth.mercadolivre.com.br/authorization?response_type=token&client_id="
				+ CLIENT_ID);
		webview.setWebViewClient(new WebViewClient() {

			public void onPageStarted(WebView view, String url) {
				// dismiss the indeterminate progress dialog
				Log.d("PH", "onPageStarted: " + url);
				if (url.startsWith(Common.CORRECTBEGIN_AUTH)) {
					Log.d("PH", "onPageStarted: Logou!");
				}
			}

			public void onPageFinished(WebView view, String url) {
				// dismiss the indeterminate progress dialog
				Log.d("PH", "onPageFinished: " + url);
				if (url.startsWith(Common.CORRECTBEGIN_AUTH)) {
					Log.d("PH", "onPageFinished: Logou!");

					String aux = url;
					aux = aux.substring(Common.URL_BASE.length());
					Log.d("PH", "onPageFinished:NO_BASE " + aux);

					if (aux.startsWith(Common.ACCESS_TOKEN_NAME)) {
						aux = aux.substring(Common.ACCESS_TOKEN_NAME.length());
						int i = aux.indexOf('&');
						Common.getInstance().accessToken = aux.substring(0, i);
						Log.d("PH", Common.getInstance().accessToken);

						Intent intent = new Intent(getApplicationContext(),
								MenuActivity.class);
						startActivity(intent);

						/*
						 * ServiceRequest teste = new
						 * ServiceRequest(Common.SV_MY_INFO){ public void
						 * work(){ Log.d("PH", "Deu certo"); } };
						 * teste.execute();
						 */
					}

				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_auth, menu);
		return true;
	}
}
