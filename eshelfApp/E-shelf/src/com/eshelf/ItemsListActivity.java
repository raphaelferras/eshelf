package com.eshelf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.eshelf.services.ServiceRequest;
import com.eshelf.util.Common;

public class ItemsListActivity extends Activity {
	ListView listView;
	ImageView mLoading;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list);
		listView = (ListView) findViewById(R.id.mylist);
		mLoading = (ImageView) findViewById(R.id.loading);

		mLoading.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		ServiceRequest teste = new ServiceRequest(Common.SV_BOOKMARKS,
				Common.WITH_TOKEN) {
			public void work(String result) {
				parserJson(result);
			}

		};
		teste.execute();
	}

	private void parserJson(String result) {
		JSONArray jObject;
		try {
			jObject = new JSONArray(result);

			String ids = "";
			for (int i = 0; i < jObject.length(); i++) {
				JSONObject menuObject = jObject.getJSONObject(i);

				// String idItem = menuObject.getString("item_id");
				if (i == 0) {
					ids += menuObject.getString("item_id");

				} else {
					ids += "," + menuObject.getString("item_id");
				}
			}

			ServiceRequest teste = new ServiceRequest(Common.SV_ITEM_DETAILS
					+ "?ids=" + ids, Common.NO_TOKEN) {
				public void work(String result) {
					parserJson2(result);
				}

			};
			teste.execute();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parserJson2(String result) {
		mLoading.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);
		JSONArray jObject;
		Pair<String, String>[] values = null;
		try {
			jObject = new JSONArray(result);
			values = new Pair[jObject.length()];
			for (int i = 0; i < jObject.length(); i++) {
				JSONObject menuObject = jObject.getJSONObject(i);

				// String idItem = menuObject.getString("item_id");
				values[i] = new Pair<String, String>(
						menuObject.getString("title"),
						menuObject.getString("thumbnail"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayAdapter<Pair<String, String>> adapter = new ItemsListAdapter(this,
				values);

		// Assign adapter to ListView
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_items_list, menu);
		return true;
	}

}
