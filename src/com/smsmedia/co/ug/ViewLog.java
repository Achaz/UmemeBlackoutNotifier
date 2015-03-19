package com.smsmedia.co.ug;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smsmedia.co.ug.library.NewJSONParser;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
public class ViewLog extends Activity{
 
    // url to make request
    
    private static String url= "http://cipher256.com/binITproject/android_login_api/include/view_all_logs.php?format=json";
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
    private static final String TAG_PLACE="place_name";
    private static final String TAG_ACC="account_number";
    private static final String TAG_REPLY="umeme_reply";
    
    TextView name, desc,post_time,locality,response;
	EditText search_box;
	ImageView go_button, expand_button;
	ListView bins_l;
	Animation rotate_list = null;
	ArrayList<HashMap<String, String>> binList;
	
	//HashMap Values
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
    String usr_place="";
    String usr_acc="";
    String usr_reply="";
    
    int pos = 0;
    // contacts JSONArray
    JSONArray bins = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_places);
 
       build();
       
       
 
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
	
	 public void build(){
		 
		setUpViews();
		setUpListeners();
		loadBinList();
       
	}
	
	public void setUpViews(){
		
		bins_l = (ListView)findViewById(R.id.placelist);
		name = (TextView)findViewById(R.id.locality);
		desc = (TextView)findViewById(R.id.description);
		post_time=(TextView)findViewById(R.id.time);
		locality=(TextView)findViewById(R.id.notifier);
		response=(TextView)findViewById(R.id.response);
		expand_button = (ImageView)findViewById(R.id.BIN_LIST_EXPAND);
	}
	
	public void setUpListeners(){
		//go_button.setOnClickListener(goListener);
	}
	
	public void setUpAnimations(){
		//rotate_list = AnimationUtils.loadAnimation(this, R.anim.listloader_anim);	
	}
	
	public void loadBinList(){
		//bins_l.startAnimation(rotate_list);
		
		// Hashmap for ListView
        binList = new ArrayList<HashMap<String, String>>();
       
        // Creating JSON Parser instance
        NewJSONParser jParser = new NewJSONParser();
        @SuppressWarnings("static-access")
		JSONObject json = jParser.getJSONfromURL(url);
        
//        Toaster("JSON FROM DB",json.toString());
        
        
     if(json != null){
    	 
       try {
        
            bins = json.getJSONArray(TAG_BINS);
            // looping through All Bins
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
                usr_place=jObject.getString(TAG_PLACE);
                usr_acc=jObject.getString(TAG_ACC);
                usr_reply=jObject.getString(TAG_REPLY);
                // msg="#"+usr+"posted:"+"\n"+b_desc+"@"+loc+"Logged on"+pst_date+"at"+pst_time;
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
 
                // adding each child node to HashMap key => value
                map.put(TAG_ID, id);
               // map.put(TAG_MSG, msg);
                map.put(TAG_TIME, "at"+ " "+pst_time);
                map.put(TAG_DESC, b_desc);
                map.put(TAG_PNAME, place);
                map.put(TAG_LAT, lat);
                map.put(TAG_LON, lon);
                map.put(TAG_PLACE, usr_place);
                map.put(TAG_LOC, "@"+loc);
                map.put(TAG_DATE, "Posted on:"+pst_date);
                map.put(TAG_USERNAME, "#"+usr+" "+"posted:");
                map.put(TAG_ACC, usr_acc);
                map.put(TAG_REPLY, "Umeme Says:"+usr_reply);
               // map.put(TAG_ALTITUDE, alt);
 
                // adding HashList to ArrayList
                binList.add(map);
            }
            
        } catch (JSONException e) {
        	
            Toast.makeText(this, "No Data Gotten\n "+e.getMessage(), Toast.LENGTH_LONG).show();
           // finish();
        }
       
  }else{
	  
     Toast.makeText(this, "Failure to connect to Servers", Toast.LENGTH_LONG).show();
  }//0782 132866
    /**
     * Updating parsed JSON data into ListView
     * */
    ListAdapter adapter = new SimpleAdapter(this, binList, R.layout.list_item, new String[] {TAG_USERNAME,TAG_DESC,TAG_LOC,TAG_PLACE,TAG_TIME,TAG_DATE,TAG_REPLY}, new int[] { R.id.notifier, R.id.description, R.id.locality , R.id.place , R.id.time, R.id.date,R.id.response});
    bins_l.setAdapter(adapter);

        
        bins_l.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
        		pos = position;
        		String choice[] = {"Update","Back"};

        		
        		AlertDialog.Builder opt = new AlertDialog.Builder(ViewLog.this);	
				   opt.setItems(choice, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch(which){
						
						
						case 0:
							
							Intent in=new Intent(getApplication(),EditPlace.class);
							in.putExtra(TAG_LAT, binList.get(pos).get(TAG_LAT));
							in.putExtra(TAG_LON, binList.get(pos).get(TAG_LON));
							in.putExtra(TAG_PNAME, binList.get(pos).get(TAG_PNAME));
							in.putExtra(TAG_DESC, binList.get(pos).get(TAG_DESC));
						    in.putExtra(TAG_LOC, binList.get(pos).get(TAG_LOC));
							in.putExtra(TAG_ID, binList.get(pos).get(TAG_ID));
							in.putExtra(TAG_TIME, binList.get(pos).get(TAG_TIME));
							in.putExtra(TAG_DATE, binList.get(pos).get(TAG_DATE));
							in.putExtra(TAG_USERNAME, binList.get(pos).get(TAG_USERNAME));
							in.putExtra(TAG_PLACE, binList.get(pos).get(TAG_PLACE));
							in.putExtra(TAG_ACC, binList.get(pos).get(TAG_ACC));
							startActivity(in);
							break;
							
						case 1:	
							dialog.cancel();
							break;
						}
						
					}
				});
				opt.setTitle("Options");
				   
				opt.show();
        	
			
        	}
		});
	}
	
	View.OnClickListener goListener = new View.OnClickListener() {
			
			public void onClick(View arg0) {
			   Toast.makeText(getApplicationContext(), "Re-Loading list", Toast.LENGTH_LONG).show();	
			}
	};
		
	public void Toaster(String title, String body){
		final AlertDialog.Builder toast = new AlertDialog.Builder(this);
		toast.setTitle(title);
		toast.setMessage(body);
		toast.show();
	} 		
 
}