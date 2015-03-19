package com.smsmedia.co.ug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;



public class EditPlace extends Activity{
	
	private TimePicker timePicker1;
	private DatePicker dpResult;
	Button edit_bin;
	EditText bin_name, bin_desc,EditTime,editdate,account;
	private int hour;
	private int minute;
	private int year;
	private int month;
	private int day;
	Context  ctx;
	String id, p_name, p_desc,p_locname, bin_lat, bin_lon, bin_alt, place, text,usr,usr_place,acc;
	GPSTracker gps;
	
	String pst_time = "";
    String pst_date="";
    
	AlertDialogManager alert = new AlertDialogManager();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bin);
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
		
		Intent in = getIntent();
		p_name = in.getStringExtra("phone_number");
		p_desc = in.getStringExtra("complaint");
		p_locname=in.getStringExtra("locality");
		bin_lat = in.getStringExtra("latitude");
		bin_lon = in.getStringExtra("longitude");
		usr=in.getStringExtra("username");
		id = in.getStringExtra("pid");
		place = in.getStringExtra("place_name");
		acc=in.getStringExtra("account_number");
		
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		
		timePicker1.setCurrentHour(hour);
		timePicker1.setCurrentMinute(minute);
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dpResult.init(year, month, day, null);
		edit_bin.setText("Edit Post");
		bin_name.setText(place);
		account.setText(acc);
		bin_desc.setText(p_desc);
		Toaster("Are you sure you want to edit?", "ID: "+id+"\nNAME: "+ p_name+"\nDESC: "+ p_desc+"\nLAT: "+ bin_lat+"\nLON: "+bin_lon+"\nPLACE: "+ place+"\nAccount Number:"+acc);
	}
	public void setUpViews(){
		
		edit_bin = (Button)findViewById(R.id.ADD_BIN_BTN);
		bin_name = (EditText)findViewById(R.id.BIN_NAME);
		account=(EditText)findViewById(R.id.account_number);
		bin_desc=(EditText)findViewById(R.id.complaint);
		timePicker1 = (TimePicker) findViewById(R.id.time);
		dpResult = (DatePicker) findViewById(R.id.editdate);
	}
	
	public void setUpListeners(){
		
		edit_bin.setOnClickListener(editBinListener);
	}

    
	View.OnClickListener editBinListener = new View.OnClickListener() {
		
		public void onClick(View arg0) {
			
			gps = new GPSTracker(getApplicationContext());

			// check if GPS location can get
	 if (gps.canGetLocation()) {
		
		bin_lat=Double.toString(gps.getLatitude());
		bin_lon=Double.toString(gps.getLongitude());
		bin_alt=Double.toString(gps.getAltitude());
		p_name = bin_name.getText().toString();
		String temp = bin_desc.getText().toString().replace(" ", ""); 
		p_desc = temp;
	
		Geocoder gCoder=new Geocoder(getApplicationContext());
		List<Address> address;
	     try{
				address = gCoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
				
				if(address!=null&&address.size()>0){
					
					place=address.get(0).getThoroughfare ();				
					
					ToastReciept("Edit Reciept", "ID: "+id+"\nNAME: "+ bin_name.getText().toString()+"Account Number:"+account.getText().toString()+
							     "\nDESC: "+ bin_desc.getText().toString()+"\nLAT: "+ bin_lat+
							     "\nLON: "+bin_lon+"\nPLACE: "+ place);
					
				}
				else{
					
					Toast.makeText(getApplicationContext(), "Cannot get Street name", Toast.LENGTH_LONG).show();
				}
			
	        }catch(IOException e){
	        	
	        	Toast.makeText(getApplicationContext(), "Cannot fetch place information", Toast.LENGTH_LONG).show();		
	        }
	     
	  }else{
		// Can't get user's current location
		alert.showAlertDialog(EditPlace.this, "GPS Status",
				"Couldn't get location information. Please enable GPS",
				false);
		// stop executing code by return
		return;
	}					
			
		}
	};
	
    public void ToastReciept(String title, String body){
		
		final AlertDialog.Builder toast = new AlertDialog.Builder(this);
		toast.setTitle(title);
		toast.setMessage(body);
		toast.setPositiveButton("Continue", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try {
					
					postData();
					
				} catch (JSONException e) {
					
					// TODO Auto-generated catch block
					Toast.makeText(getBaseContext(), "Unable to edit post", Toast.LENGTH_LONG).show();
					
				}
				
				startActivity(new Intent(getApplicationContext(), ViewLog.class));
				finish();
			}
		});
		
		toast.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		toast.show();
	}
	
	public void Toaster(String title, String body){
		
		final AlertDialog.Builder toast = new AlertDialog.Builder(this);
		toast.setTitle(title);
		toast.setMessage(body);
		
		toast.setPositiveButton("Yes", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		toast.setNegativeButton("No", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ViewLog.class));
				finish();
			}
		});
		
		toast.show();
	}

	public void postData() throws JSONException{  
		
		try {
			     String post_date=month + 1+"/"+day+"/"+year;
			     String post_time=(pad(hour))+":"+(pad(minute));			
		     	 HttpClient httpclient = new DefaultHttpClient();
		     	
		     	usr = PreferenceConnector.readString(getApplicationContext(), PreferenceConnector.USERNAME, null);
				HttpPost httppost = new HttpPost("http://cipher256.com/binITproject/android_login_api/include/edit_place?pid="+id+"&name="+p_name+"&complaint="
		     	+bin_desc.getText().toString().replace(" ", "")+"&locality="+p_locname+"&latitude="+bin_lat+"&longitude="+bin_lon+"&post_time="+post_time+"&post_date="+post_date+"&username"+usr+"&account_number="+account.getText().toString()+"&place_name="+bin_name.getText().toString());
			
				HttpResponse response = httpclient.execute(httppost);

				// for JSON:
				if(response != null)
				{
					InputStream is = response.getEntity().getContent();

					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					StringBuilder sb = new StringBuilder();

					String line = null;
					
					try {
						while ((line = reader.readLine()) != null) {
							
							sb.append(line + "\n");
						}
						
					} catch (IOException e) {
					
    					e.printStackTrace();
						
					} finally {
						
					try {
							
							is.close();
							
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					}
					
					text = sb.toString();
				}
				
				JSONObject res = new JSONObject(text);
				String output = res.getString("message");
				//tv.setText(text);
				Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
				Intent in=new Intent(this,ViewLog.class);
				startActivity(in);
				
		  }catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
	      }
		catch (IOException e) {
	    		// TODO Auto-generated catch block
	   	}
	}
	private static String pad(int c) {
		
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
}
