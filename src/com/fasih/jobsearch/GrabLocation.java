package com.fasih.jobsearch;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GrabLocation {
	LocationManager locMan;
	Timer timer;
	LocationResult locationResult;
	boolean gpsEnabled,networkEnabled;
	public boolean getConnectionInfo(Context context, LocationResult lR){
		locationResult = lR;
		if (locMan == null)
			locMan = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		networkEnabled = locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		gpsEnabled = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!networkEnabled && !gpsEnabled)
			return false;
		else if (networkEnabled)
			locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkListener);
		else
			locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
		timer = new Timer();
		timer.schedule(new GetLastLocation(), 20000);
		return true;
	}
	public String getWorkingProvider(){
		if(locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
			return LocationManager.NETWORK_PROVIDER;
		else if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER))
			return LocationManager.GPS_PROVIDER;
		else
			return null;
	}
	LocationListener networkListener = new LocationListener(){

		public void onLocationChanged(Location location) {
			timer.cancel();
			locationResult.gotLocation(location);
			locMan.removeUpdates(this);
			locMan.removeUpdates(networkListener);
		}
		public void onProviderDisabled(String provider) {
		}
		public void onProviderEnabled(String provider) {
		}
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};
	LocationListener gpsListener = new LocationListener(){

		public void onLocationChanged(Location location) {
			timer.cancel();
			locationResult.gotLocation(location);
			locMan.removeUpdates(this);
			locMan.removeUpdates(gpsListener);
		}
		public void onProviderDisabled(String provider) {
		}
		public void onProviderEnabled(String provider) {
		}
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};
	class GetLastLocation extends TimerTask{
		@Override
		public void run(){
			locMan.removeUpdates(gpsListener);
			locMan.removeUpdates(networkListener);
			
			Location netLoc = null, gpsLoc = null;
			if (gpsEnabled)
				gpsLoc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (networkEnabled)
				netLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (gpsLoc != null && netLoc != null){
				if (gpsLoc.getTime() > netLoc.getTime())
					locationResult.gotLocation(gpsLoc);
				else
					locationResult.gotLocation(netLoc);
			}
			if (gpsLoc == null && netLoc != null){
				locationResult.gotLocation(netLoc);
				return;
			}
			else if (gpsLoc != null){
				locationResult.gotLocation(gpsLoc);
				return;
			}
			locationResult.gotLocation(null);
		}
	}
	public static abstract class LocationResult{
		public abstract void gotLocation(Location loc);
	}
}
