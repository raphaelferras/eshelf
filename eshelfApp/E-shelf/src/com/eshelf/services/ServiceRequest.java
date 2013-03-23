package com.eshelf.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.util.Log;

import com.eshelf.util.Common;

public class ServiceRequest {

	private final String mServiceName;
	private final int isTokenService;

	
	
	public ServiceRequest(String serviceName, int token) {
		mServiceName = serviceName;
		isTokenService = token;
	}

	public void execute() {
		new LongRunningGetIO().execute();
	}

	private class LongRunningGetIO extends AsyncTask<Void, Void, String> {

		protected String getASCIIContentFromEntity(HttpEntity entity)
				throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n > 0) {
				byte[] b = new byte[4096];
				n = in.read(b);
				if (n > 0)
					out.append(new String(b, 0, n));
			}
			return out.toString();
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.d("PH",mServiceName + ": doInBackground");
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			Log.d("PH",mServiceName + "(REQUEST) PQP: " + Common.getInstance().accessToken);

			Log.d("PH",Common.getInstance().accessToken);
			String url = Common.SV_BASE_URL + mServiceName;
			if( isTokenService  == Common.WITH_TOKEN){
				url = url+ "?access_token=" + Common.getInstance().accessToken;
			}
			Log.d("PH",mServiceName + " url: " + url);

			HttpGet httpGet = new HttpGet(url);
			String text = null;
			try {
				HttpResponse response = httpClient.execute(httpGet,
						localContext);
				HttpEntity entity = response.getEntity();
				text = getASCIIContentFromEntity(entity);
			} catch (Exception e) {
				return e.getLocalizedMessage();
			}
			return text;
		}

		protected void onPostExecute(String results) {
			if (results != null) {
				Log.d("PH", mServiceName + " results: " + results);
				work(results);
			} else Log.d("PH", "result is NULL");
		}
	}
	
	public void work(String result){
		
	}
}
