package com.smsmedia.co.ug;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smsmedia.co.ug.library.NewJSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Details extends ListActivity{

	
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	
 
    // JSON Node names
    private static final String TAG_BINS = "posts";
    private static final String TAG_ID = "pid";
    private static final String TAG_LOC="locality";
    private static final String TAG_TIME = "post_time";
    private static final String TAG_DATE="post_date";
    private static final String TAG_USERNAME="username";
    private static final String TAG_PNAME = "phone_number";
    private static final String TAG_LAT = "latitude";
    private static final String TAG_LON = "longitude";
    private static final String TAG_DESC = "complaint";
    
    TextView name, desc,post_time,locality;
    String  album_name;
    String text;
    ArrayList<HashMap<String, String>> tracksList;
    
    String id = "";
    String pst_time = "";
    String pst_date="";
    String b_desc = "";
    String lat = "";
    String lon = "";
    String place="";
    String loc="";
    String usr="";
    String msg="";
    String local="";
    
    JSONArray bins = null;
    //NewJSONParser jParser = new NewJSONParser();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Intent i = getIntent();
		local = i.getStringExtra("album_name");
	    //local=album_name;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		text="";
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(Details.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
		
		
		
		// Loading tracks in Background Thread
		new LoadTracks().execute();
		
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				
				
				
				
			}
		});	
		
		
		
	}
	
	class LoadTracks extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Details.this);
			pDialog.setMessage("Loading Places Please Wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			
		}

		@Override
		protected String doInBackground(String... args) {
			
			
			tracksList= new ArrayList<HashMap<String, String>>();
			// url to make request
		    String url = "http://cipher256.com/binITproject/android_login_api/include/selector.php?format=json&locality="+local;
			//params.add(new BasicNameValuePair(TAG_LOC, album_name));
			 NewJSONParser jParser = new NewJSONParser();
		     @SuppressWarnings("static-access")
		    JSONObject json = jParser.getJSONfromURL(url);
			//GsonParser jsonParser = new GsonParser();
			
			//JSONObject json = jsonParser.makeHttpRequest(url, "GET",params);
			Log.d("Albums JSON: ", "> " + json);
			if(json != null)
			{
				try {
					bins = json.getJSONArray(TAG_BINS);
					
					
					for(int i = 0; i < bins.length(); i++){
		                JSONObject c = bins.getJSONObject(i);
		                String s = c.getString("post");
		                JSONObject jObject = new JSONObject(s);
		                // Storing each json item in variable
		                id = jObject.getString(TAG_ID);
		                pst_time = jObject.getString(TAG_TIME);
		                b_desc = jObject.getString(TAG_DESC);
		                lat=jObject.getString(TAG_LAT);
		                lon= jObject.getString(TAG_LON);
		                place = jObject.getString(TAG_PNAME);
		                loc=jObject.getString(TAG_LOC);
		                pst_date=jObject.getString(TAG_DATE);
		                usr=jObject.getString(TAG_USERNAME);
		                
		                // msg="#"+usr+"posted:"+"\n"+b_desc+"@"+loc+"Logged on"+pst_date+"at"+pst_time;
		                // creating new HashMap
		                HashMap<String, String> map = new HashMap<String, String>();
		 
		                // adding each child node to HashMap key => value
		                map.put(TAG_ID, id);
		               // map.put(TAG_MSG, msg);
		                map.put(TAG_TIME, "at"+ " "+pst_time);
		                map.put(TAG_DESC, b_desc);
		                map.put(TAG_PNAME, "PhoneNumber:"+place);
		                map.put(TAG_LAT, lat);
		                map.put(TAG_LON, lon);
		                map.put(TAG_LOC, "@"+loc);
		                map.put(TAG_DATE, "Logged on:"+pst_date);
		                map.put(TAG_USERNAME, "#"+usr+" "+"posted:");
		               // map.put(TAG_ALTITUDE, alt);
		 
		                // adding HashList to ArrayList
		                tracksList.add(map);
		            }
					
					
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					ListAdapter adapter = new SimpleAdapter(
							Details.this, tracksList,
							R.layout.single_place_list, new String[] {TAG_USERNAME,TAG_PNAME,TAG_DESC,TAG_LOC,TAG_TIME,TAG_DATE}, new int[] { R.id.notifier,R.id.phoneNumber,R.id.description,R.id.locality,R.id.time,R.id.date});
					// updating listview
					setListAdapter(adapter);
					
					// Change Activity Title with Album name
					setTitle(local);
					
				}
				
			});
			
		}
		
		
		
		
	}
	
	

}
