package com.smsmedia.co.ug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class InsertPlace extends Activity {

	TextView tv;
	String text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply);
		
		tv 	= (TextView)findViewById(R.id.textview);
		text 	= "";

		try {
			
			postData();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
		}
	}
		public void postData() throws JSONException{  
			
		try {
				Intent i=getIntent();
				String bname=i.getStringExtra("bin_name");
				String temp = i.getStringExtra("bin_description").replaceAll(" ", "");
				String desc=temp;
				String lat=i.getStringExtra("bin_latitude");
				String lon=i.getStringExtra("bin_longitude");
				String place_name=i.getStringExtra("place_name").replaceAll(" ", "");
				String place=place_name;
				String post_time=i.getStringExtra("post_time");
				String post_date=i.getStringExtra("post_date");
				String username=i.getStringExtra("username");
				String user_place_name=i.getStringExtra("user_place_name");
				String account_number=i.getStringExtra("account_number");
				
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost=new HttpPost("http://cipher256.com/binITproject/android_login_api/include/insert.php?name="+bname+"&complaint="+desc+"&longitude="+lon+"&latitude="+lat+"&post_time="+post_time+"&post_date="+post_date+"&username="+username+"&locality="+place+"&user_place_name="+user_place_name+"&account_number="+account_number);
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
					JSONObject res = new JSONObject(text);
					String output = res.getString("message");
					
					Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
					
					Intent in=new Intent(this,ViewLog.class);
					startActivity(in);
					//finish();
				}else{
					
		     Toast.makeText(this, "Failure to connect to Servers", Toast.LENGTH_LONG).show();
		  }
//				tv.setText(text);
		}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
	    		// TODO Auto-generated catch block
	   	}
	}
	
	public void Toaster(String title, String body){
		
		final AlertDialog.Builder toast = new AlertDialog.Builder(this);
		toast.setTitle(title);
		toast.setMessage(body);
		toast.show();
	} 
	

}
