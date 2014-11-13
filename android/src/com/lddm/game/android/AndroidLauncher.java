package com.lddm.game.android;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lddm.game.lddgame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		
		Toast.makeText(this, "Latitude: " + latitude, Toast.LENGTH_LONG).show();
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new lddgame(), config);*/
		int satt = 0;
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		lm.getGpsStatus(null).getTimeToFirstFix();
		for(GpsSatellite sat : lm.getGpsStatus(null).getSatellites())
		{
			
			satt++;
		}

		Toast.makeText(this, "Satelites GPS: " + satt, Toast.LENGTH_LONG).show();
		
		RelativeLayout layout = new RelativeLayout(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View gameView = initializeForView(new lddgame(satt));
        layout.addView(gameView);
        //Games.Leaderboards.submitScore(getApiClient(), LEADERBOARD_ID, 1337);
		
	

		    // Add the AdView to the view hierarchy. The view will have no size
		    // until the ad is loaded.
		    //LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		    //layout.addView(adView);

		    RelativeLayout.LayoutParams adParams = 
		            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		        // Hook it all up
		        setContentView(layout);
		    
	}
}   
