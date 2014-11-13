package com.lddm.game.android;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lddm.game.lddgame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		
		Toast.makeText(this, "Latitude: " + latitude, Toast.LENGTH_LONG).show();
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new lddgame(), config);
	}
}   
