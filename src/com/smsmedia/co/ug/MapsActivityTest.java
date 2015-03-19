package com.smsmedia.co.ug;

import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smsmedia.co.ug.library.OtherJSONParser;

import android.os.Bundle;
import android.widget.Toast;

public class MapsActivityTest extends FragmentActivity{
	
	private GoogleMap mMap;
	private static final String TAG_BINS = "posts";
	private static String url="http://cipher256.com/binITproject/android_login_api/include/view_all_logs.php?format=json";
	private static final String TAG_LAT = "latitude";
    private static final String TAG_LON = "longitude";
    private static final String TAG_DESC = "description";
    private static final String TAG_USERNAME="username";
    ArrayList<HashMap<String, String>> binList;
    
    
    String lat = "";
    String lon = "";
    String b_desc = "";
    String usr="";
    JSONArray bins = null;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		setUpMapIfNeeded();
	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
		setUpMapIfNeeded();
	}
	
	private void setUpMapIfNeeded() {
		
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            
            CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(0.313611100000000000,32.581111100000044000));
            
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
            
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
            	
                setUpMap();
            }
        }
    }
	@SuppressWarnings("static-access")
	private void setUpMap() {
	
	binList  = new ArrayList<HashMap<String, String>>();
    OtherJSONParser jParser = new OtherJSONParser();
    JSONObject json = jParser.getJSONfromURL(url);
    
    if(json!=null){
    	
    	try{
    		
    		bins = json.getJSONArray(TAG_BINS);
    		
    		for(int i = 0; i < bins.length(); i++){
    			
                JSONObject c = bins.getJSONObject(i);
                String s = c.getString("post");
                JSONObject jObject = new JSONObject(s);
                
                lat=jObject.getString(TAG_LAT);
                lon= jObject.getString(TAG_LON);
                b_desc=jObject.getString(TAG_DESC);
                usr=jObject.getString(TAG_USERNAME);
                
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put(TAG_LAT, lat);
                map.put(TAG_LON, lon);
                map.put(TAG_DESC, b_desc);
                map.put(TAG_USERNAME, usr);
                
                binList.add(map);
                
                for(HashMap<String, String> hashMap:binList){
                	
                	mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(hashMap.get(TAG_LAT)) ,Double.parseDouble(hashMap.get(TAG_LON)))).title(hashMap.get(TAG_USERNAME)).snippet(hashMap.get(TAG_DESC)));
               
               }
           }
    		
    	}catch (JSONException e) {
    		
            Toast.makeText(this, "No Data Gotten\n "+e.getMessage(), Toast.LENGTH_LONG).show();
            // finish();
         }
    	
    }else{
	   
      Toast.makeText(this, "Failure to connect to Servers", Toast.LENGTH_LONG).show();
      
   		}
    

	}

}