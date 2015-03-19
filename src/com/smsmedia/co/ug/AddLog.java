package com.smsmedia.co.ug;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddLog extends Activity{

	
	Button add_bin;
	EditText bin_name, bin_desc,tvtime,tvdate,account_number;
	String lat,lon,altitude;
	String email;
	private TimePicker timePicker1;
	private DatePicker dpResult;
	TextView user, tvresponse;
	String text;
	
	
	Context  ctx;
	
	
	
	private int hour;
	private int minute;
	private int year;
	private int month;
	private int day;
	
	//private DatePicker dpResult;
	GPSTracker gps;	
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bin);
		ctx = AddLog.this;
		Intent in = getIntent();
	    email = in.getStringExtra("username");
	    tvresponse  = (TextView)findViewById(R.id.responseTv);
		user = (TextView)findViewById(R.id.LOGGED_USER);
        text    = "";
        user.setText("Welcome "+email+"!");
		user.setTextColor(Color.MAGENTA);
		
		add_bin = (Button)findViewById(R.id.ADD_BIN_BTN);
		bin_name = (EditText)findViewById(R.id.BIN_NAME);
		account_number=(EditText)findViewById(R.id.account_number);
		bin_desc = (EditText)findViewById(R.id.complaint);
		timePicker1 = (TimePicker) findViewById(R.id.time);
		//result = (TextView)findViewById(R.id.dateIndicator);
		dpResult = (DatePicker) findViewById(R.id.editdate);
		
		add_bin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gps = new GPSTracker(getApplicationContext());

				// check if GPS location can get
				if (gps.canGetLocation()) {
					
					Geocoder gCoder=new Geocoder(getApplicationContext());
					
					List<Address> address;
					
					try {
						address = gCoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
						
						if(address!=null&&address.size()>0){
							
							String Street=address.get(0).getThoroughfare ();
							
							lat=Double.toString(gps.getLatitude());
							lon=Double.toString(gps.getLongitude());
							altitude=Double.toString(gps.getAltitude());
							String bname = bin_name.getText().toString();
							String desc = bin_desc.getText().toString();
							String acc=account_number.getText().toString();
							
							final Calendar c = Calendar.getInstance();
							hour = c.get(Calendar.HOUR_OF_DAY);
							minute = c.get(Calendar.MINUTE);
							
							timePicker1.setCurrentHour(hour);
							timePicker1.setCurrentMinute(minute);
							
							TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
							
							String getSimNumber = telemamanger.getLine1Number();
							
                             if(bname.equalsIgnoreCase("")){
								
								Toast.makeText(getApplicationContext(), "Some fields are missing", Toast.LENGTH_LONG).show();
								
							  }else{
							
							Toaster("Bin Reciept","Name: "+bname+"\nDescription: "+desc+"\nLatitude: "+gps.getLatitude()+"\nLongitude: "+gps.getLongitude()+"\nPlace/Street: "+Street+"\nPhoneNumber:"+getSimNumber+"Account Number:"+acc);
							
							  }
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
				} else {
					
					gps.showSettingsAlert();
					
					
				}
				
				
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
			
		
			
		case R.id.menu_update:
			
			Intent itenIntent8=new Intent(this, Updates.class);
			startActivity(itenIntent8);
			
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		
		}
	   
    }
     public void Toaster(String title, String body){
		
		final AlertDialog.Builder toast = new AlertDialog.Builder(this);
		toast.setTitle(title);
		toast.setMessage(body);
		toast.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			     Geocoder gCoder=new Geocoder(getApplicationContext());
			        List<Address> address;
			        try {
			        	
			        	address = gCoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
			        	
						if(address!=null&&address.size()>0){
							
							String Street=address.get(0).getThoroughfare ();
							
							lat=Double.toString(gps.getLatitude());
							lon=Double.toString(gps.getLongitude());
			                String bname = bin_name.getText().toString();
							String desc = bin_desc.getText().toString();
							String acc=account_number.getText().toString();
							final Calendar c = Calendar.getInstance();
							hour = c.get(Calendar.HOUR_OF_DAY);
							minute = c.get(Calendar.MINUTE);
							
							//final Calendar c = Calendar.getInstance();
							year = c.get(Calendar.YEAR);
							month = c.get(Calendar.MONTH);
							day = c.get(Calendar.DAY_OF_MONTH);
							//String post_date=result.getText().toString();
							String post_date=month + 1+"/"+day+"/"+year;
							
							String post_time=(pad(hour))+":"+(pad(minute));
							
							dpResult.init(year, month, day, null);
							
							timePicker1.setCurrentHour(hour);
							timePicker1.setCurrentMinute(minute);
							
							TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
							
							String getSimNumber = telemamanger.getLine1Number();
							
							Intent in=new Intent(getApplication(),InsertPlace.class);
							
							in.putExtra("bin_name", getSimNumber);
							in.putExtra("place_name", Street);
							in.putExtra("bin_description", desc);
							in.putExtra("bin_latitude", lat);
							in.putExtra("bin_longitude", lon);
							in.putExtra("post_time", post_time);
							in.putExtra("post_date", post_date);
							in.putExtra("username", email);
							in.putExtra("user_place_name", bname);
							in.putExtra("account_number", acc);
							startActivity(in);
							
						}
			            
			        }catch (IOException e) {
			            // TODO Auto-generated catch block
			        }
				
			}
		});
		toast.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		toast.show();
	}
	private static String pad(int c) {
		
		if (c >= 10)
			
		   return String.valueOf(c);
		
		else
			
		   return "0" + String.valueOf(c);
	}
	

}