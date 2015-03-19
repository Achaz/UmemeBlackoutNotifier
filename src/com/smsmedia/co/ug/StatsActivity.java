package com.smsmedia.co.ug;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.smsmedia.co.ug.library.NewJSONParser;
import com.smsmedia.co.ug.library.StatsParser;

public class StatsActivity extends ListActivity {
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	StatsParser jsonParser = new StatsParser();

	ArrayList<HashMap<String, String>> albumsList;

	// albums JSONArray
	JSONArray albums = null;

	// albums JSON url
	//private static final String URL_ALBUMS = "http://192.168.2.124/android_login_api/include/get_all_places.php?format=json";
	 private static final String URL_ALBUMS="http://cipher256.com/binITproject/android_login_api/include/get_all_places.php?format=json";
	// ALL JSON node names
	private static final String TAG_ID = "pid";
	private static final String TAG_NAME = "locality";
	private static final String TAG_SONGS_COUNT = "count";
	private static final String TAG_BINS = "posts";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(StatsActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

		// Hashmap for ListView
		albumsList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		new LoadAlbums().execute();
		
		// get listview
		ListView lv = getListView();
		
		/**
		 * Listview item click listener
		 * TrackListActivity will be lauched by passing album id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				
				
                Intent i = new Intent(getApplicationContext(), Details.class);
				
				// send album id to tracklist activity to get list of songs under that album
				String album_id = ((TextView) view.findViewById(R.id.album_name)).getText().toString();
				String temp=album_id.replaceAll(" ", "");
				i.putExtra("album_name", temp);				
				
				startActivity(i);
				
				
			}
		});		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.layout.menu, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		
		case R.id.menu_addlog:
			
			Intent itemintent = new Intent(this,AddLog.class);
			startActivity(itemintent);
			
			return true;
		
		case R.id.menu_viewLog:
			
			Intent itemintent1 = new Intent(this,ViewLog.class);
			startActivity(itemintent1);
			
			return true;
			
		case R.id.menu_settings:
			
			Intent itemintent3 = new Intent(this,StatsActivity.class);
			startActivity(itemintent3);
			
			return true;
			
		case R.id.menu_aboutApp:
		 
			Intent itemintent4 = new Intent(this,AboutApp.class);
			startActivity(itemintent4);
			
			return true;
		case R.id.menu_dashboard:
			 
			Intent itemintent5 = new Intent(this,HomeActivity.class);
			startActivity(itemintent5);
			
			return true;
			
		case R.id.menu_popup:
			
			Intent itemintent6 = new Intent(this,Settings.class);
			startActivity(itemintent6);
			
			return true;
			
		case R.id.menu_map:
			
			Intent itemintent7=new Intent(this,MapsActivityTest.class);
			startActivity(itemintent7);
			
			return true;
			
		case R.id.menu_update:
			
			Intent itenIntent8=new Intent(this, Updates.class);
			startActivity(itenIntent8);
			
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		
		}
	   
    }

	/**
	 * Background Async Task to Load all Albums by making http request
	 * */
	class LoadAlbums extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(StatsActivity.this);
			pDialog.setMessage("Loading Places Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			//List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			NewJSONParser jParser = new NewJSONParser();
	        @SuppressWarnings("static-access")
			JSONObject json = jParser.getJSONfromURL(URL_ALBUMS);

			// Check your log cat for JSON reponse
			Log.d("Albums JSON: ", "> " + json);

			
				
				if (json != null) {
					
					try{
						
						albums = json.getJSONArray(TAG_BINS);
					// looping through All albums
					for (int i = 0; i < albums.length(); i++) {
						
						
						JSONObject c = albums.getJSONObject(i);
						String s = c.getString("post");
						
						JSONObject jObject = new JSONObject(s);
						// Storing each json item values in variable
						String id = jObject.getString(TAG_ID);
						String name = jObject.getString(TAG_NAME);
						String songs_count =jObject.getString(TAG_SONGS_COUNT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_SONGS_COUNT, songs_count);

						// adding HashList to ArrayList
						albumsList.add(map);
					   }
					}
					catch (JSONException e) {
			            Toast.makeText(getApplicationContext(), "No Data Gotten\n "+e.getMessage(), Toast.LENGTH_LONG).show();
			           // finish();
			        }
				
					
				}else{
					Toast.makeText(getApplicationContext(), "Failure to connect to Servers", Toast.LENGTH_LONG).show();
				}

			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							StatsActivity.this, albumsList,
							R.layout.place_list_item, new String[] { TAG_ID,
									TAG_NAME, TAG_SONGS_COUNT }, new int[] {
									R.id.album_id, R.id.album_name, R.id.songs_count });
					
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}
