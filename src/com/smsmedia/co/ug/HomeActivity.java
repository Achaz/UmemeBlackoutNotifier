package com.smsmedia.co.ug;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class HomeActivity extends Activity{

	String user;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setHeader(getString(R.string.HomeActivityTitle), false, true);
		
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
	   
	public void onButtonClicker(View v)
    {
    	Intent intent;
    	
    	user = PreferenceConnector.readString(getApplicationContext(), PreferenceConnector.USERNAME, null);
    	
    	switch (v.getId()) {
    	
		case R.id.main_btn_eclair:
			
			if(user != null){
				
			intent = new Intent(this, AddLog.class);
			intent.putExtra("username", user);
			startActivity(intent);
			
			}else{
				
	    		 Echo("No User Details", "Set Up Username?"); 
	    		 Toast.makeText(getApplicationContext(), "You cannot add a place without your username set up", Toast.LENGTH_SHORT).show();
	    	  }
			break;

		case R.id.main_btn_froyo:
			
			intent = new Intent(this, ViewLog.class);
			startActivity(intent);
			break;
			
		
			
		case R.id.main_btn_honeycomb:
			
			intent = new Intent(this, AboutApp.class);
			startActivity(intent);
			break;
			
		
		case R.id.updates:
			
			intent=new Intent(this,Updates.class);
			startActivity(intent);
			break;
		
		default:
			break;
		}
    	
    }
	public void Echo(String title, String body){
		
		final AlertDialog.Builder toast = new AlertDialog.Builder(this);
		toast.setTitle(title);
		toast.setMessage(body);
        toast.setPositiveButton("Yes", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), Settings.class));
				onPause();
			}
		});
		toast.setNegativeButton("No", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		toast.show();
		
	}
	public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible)
    {
    		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
    		View inflated = stub.inflate();
          
    		TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
    		txtTitle.setText(title);
    		
    		Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
    		if(!btnHomeVisible)
    			btnHome.setVisibility(View.INVISIBLE);
    		
    		Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
    		if(!btnFeedbackVisible)
    			btnFeedback.setVisibility(View.INVISIBLE);
    		
    }

}
