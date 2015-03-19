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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Updates extends ListActivity{
	
	// Connection detector
	ConnectionDetector cd;
		
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
		
	// Progress Dialog
	private ProgressDialog pDialog;

	private static final String TAG_BINS = "posts";
    private static final String TAG_ID = "id";
    private static final String TAG_REGION="REGION";
    private static final String TAG_PLANNEDWORK="PLANNEDWORK";
    private static final String TAG_DATE="DATE";
    private static final String TAG_SUBSTATION="SUBSTATION";
    
    TextView region,plannedwork,date,substation;
    
    String text;
    ArrayList<HashMap<String, String>> tracksList;
    
    JSONArray bins = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		text="";
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(Updates.this, "Internet Connection Error",
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
			pDialog = new ProgressDialog(Updates.this);
			pDialog.setMessage("Loading Updates Please Wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			
		}
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			tracksList= new ArrayList<HashMap<String, String>>();
			// url to make request
		    String url = "http://cipher256.com/binITproject/android_login_api/include/view_all_updates.php?format=json";
			//params.add(new BasicNameValuePair(TAG_LOC, album_name));
			 NewJSONParser jParser = new NewJSONParser();
		     @SuppressWarnings("static-access")
		    JSONObject json = jParser.getJSONfromURL(url);
			//GsonParser jsonParser = new GsonParser();
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
			                String id = jObject.getString(TAG_ID);
			                String m_region = jObject.getString(TAG_REGION);
			                String m_plannedwork = jObject.getString(TAG_PLANNEDWORK);
			                String m_date=jObject.getString(TAG_DATE);
			                String m_substation=jObject.getString(TAG_SUBSTATION);
			                
			                HashMap<String, String> map = new HashMap<String, String>();
			 
			                // adding each child node to HashMap key => value
			                map.put(TAG_ID, id);
			               // map.put(TAG_MSG, msg);
			                map.put(TAG_REGION, m_region);
			                map.put(TAG_PLANNEDWORK,m_plannedwork);
			                map.put(TAG_DATE,m_date);
			                map.put(TAG_SUBSTATION, m_substation);
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
							Updates.this, tracksList,
							R.layout.single_item, new String[] {TAG_REGION,TAG_PLANNEDWORK,TAG_DATE,TAG_SUBSTATION}, new int[] { R.id.region,R.id.message,R.id.message_date,R.id.substation});
					// updating listview
					setListAdapter(adapter);
					
					
					
				}
				
			});
			super.onPostExecute(result);
		}
		
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
	

}
