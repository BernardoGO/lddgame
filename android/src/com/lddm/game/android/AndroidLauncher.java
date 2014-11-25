package com.lddm.game.android;

import java.util.ArrayList;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
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

public class AndroidLauncher extends AndroidApplication  implements LocationListener {
	
	
	private LocationManager locationManager;
	  private LocationListener locationListener = new DummyLocationListener();
	  private GpsListener gpsListener = new GpsListener();
	  private Location location;
	  private GpsStatus gpsStatus;
	lddgame game;
	
	  public void openGame(int satt)
	  {
		  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		    gpsStatus = locationManager.getGpsStatus(null);
		    locationManager.addGpsStatusListener(gpsListener);
		    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2*1000, 0, locationListener);
		    
		    Toast.makeText(this, "Satelites GPS: " + satt, Toast.LENGTH_LONG).show();
			
			RelativeLayout layout = new RelativeLayout(this);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	        game = new lddgame(satt);
	        View gameView = initializeForView(game);
	        layout.addView(gameView);
		    
			    RelativeLayout.LayoutParams adParams = 
			            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
			                    RelativeLayout.LayoutParams.WRAP_CONTENT);
			        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

			        // Hook it all up
			        setContentView(layout);
	  }
	  public boolean gameStarted = false;
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
		
		/*
		int satt = 0;
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		lm.getGpsStatus(null).getTimeToFirstFix();
		
		for(GpsSatellite sat : lm.getGpsStatus(null).getSatellites())
		{
			
			satt++;
		}
		
		*/
		
		int satt = 0;
		openGame(satt);
		gameStarted = true;
        //Games.Leaderboards.submitScore(getApiClient(), LEADERBOARD_ID, 1337);
		
	

		    // Add the AdView to the view hierarchy. The view will have no size
		    // until the ad is loaded.
		    //LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		    //layout.addView(adView);

	    

		    
	}
	
	Boolean stop = false;
	 private void getSatData(){
		    Iterable<GpsSatellite> sats = gpsStatus.getSatellites();
		     
		    int satt = 0;
		    ArrayList<Float> Azimuths = new ArrayList<Float>();
		    ArrayList<Float> Elevations = new ArrayList<Float>();
		    
		    for(GpsSatellite sat : sats){
		      StringBuilder sb = new StringBuilder();
		      sb.append(sat.getPrn());
		      sb.append("\t");
		      sb.append(sat.getElevation());
		      sb.append("\t");
		      sb.append(sat.getAzimuth());
		      sb.append("\t");
		      sb.append(sat.getSnr());
		      
		      Azimuths.add(sat.getAzimuth());
		      Elevations.add(sat.getElevation());
		       /*
		      try {
		        new HttpLog().execute(sb.toString());
		      } catch (Exception e) {
		        Log.w("SatData Error",e.getMessage());
		      }*/
		      
		      satt++;
		    }
		    //Toast.makeText(this, "Satelites GPS: " + satt, Toast.LENGTH_LONG).show();
			if(gameStarted && game.gameStarted)
			{
		    game.setSattelites(satt, Azimuths, Elevations);
			if(satt > 8)
				stop = true;
				gpsStatus = locationManager.getGpsStatus(gpsStatus);
			}
		  }
	  @Override
	  public void onLocationChanged(Location location){ }
	  @Override
	  public void onProviderDisabled(String provider) { }
	  @Override
	  public void onProviderEnabled(String provider) { }
	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) { }
	   
	  
	class GpsListener implements GpsStatus.Listener{
	      @Override
	      public void onGpsStatusChanged(int event) {
	        if(!stop) getSatData();
	      }
	  }
	   
	  class DummyLocationListener implements LocationListener {
	    //Empty class just to ease instatiation
	      @Override
	      public void onLocationChanged(Location location) { }
	      @Override
	      public void onProviderDisabled(String provider) { }
	      @Override
	      public void onProviderEnabled(String provider) { }
	      @Override
	      public void onStatusChanged(String provider, int status, Bundle extras) { }
	  }
}   
