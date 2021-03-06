package com.smsmedia.co.ug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class DashActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
    
    /**
     * Home button click handler
     * @param v
     */
    public void btnHomeClick(View v)
    {
    	Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    	intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    	
    }
    
    /**
     * Feedback button click handler
     * @param v
     */
    public void btnFeedbackClick(View v)
    {
    	Intent intent = new Intent(getApplicationContext(), Settings.class);
    	startActivity(intent);
    }
	

}
