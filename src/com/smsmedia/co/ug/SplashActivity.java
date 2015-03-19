package com.smsmedia.co.ug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{
	
 private static String TAG=SplashActivity.class.getName();
 private static long SLEEP_TIME=5;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		
		IntentLauncher launcher=new IntentLauncher();
		launcher.start();
	}
	private class IntentLauncher extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				
				Thread.sleep(SLEEP_TIME*1000);
				
			}catch(Exception e){
				
				Log.e(TAG,e.getMessage());
			}
			
			Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
			SplashActivity.this.startActivity(intent);
			SplashActivity.this.finish();
			
		}
		
		
		
	}
	

}
