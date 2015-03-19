package com.smsmedia.co.ug;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {
    Button setup, logout;
    EditText email;
    TextView status;
    String emailAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		 
		emailAd = PreferenceConnector.readString(getApplicationContext(), PreferenceConnector.USERNAME, null);
		
		email = (EditText)findViewById(R.id.USER_EMAIL);
		setup = (Button)findViewById(R.id.SETUP);
		status = (TextView)findViewById(R.id.E_STATUS);
		
		
		if(emailAd != null){
			status.setText("Logged in as: "+emailAd);
			status.setTextColor(Color.GREEN);
		}else{
			status.setTextColor(Color.RED);
		}
		
		setup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailAdd = email.getText().toString();			
				if(emailAdd != null){
					PreferenceConnector.writeString(getApplicationContext(), PreferenceConnector.USERNAME, emailAdd);
					startActivity(new Intent(getApplicationContext(), HomeActivity.class));
					finish(); 
				}else{
					Toast.makeText(getApplicationContext(), "No email entered", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		logout = (Button)findViewById(R.id.LOGOUT);
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PreferenceConnector.getEditor(getApplicationContext()).remove(PreferenceConnector.USERNAME).commit();
				startActivity(new Intent(getApplicationContext(), Settings.class));
				finish();
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
}
