package com.fasih.jobsearch;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class Base extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		TextView tv = (TextView)findViewById(R.id.tV);
		ConnectivityManager cMan = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (nInfo.isConnected())
			tv.setText("Connected"); 
		else
			tv.setText("Not Connected");
		String city = "CITY";
		try {
			city = getLocation(Locale.getDefault(),getBaseContext(),true,false,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv.append("\n"+city);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_base, menu);
		return true;
	}
	public String getLocation(Locale locale, Context context, boolean city, boolean postal, boolean state_prov) throws IOException{
		LocationManager locMan = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locList = new MyLocList();
		Geocoder gC = new Geocoder(context,locale);
		Location gpsLocation = locMan.getLastKnownLocation(locMan.GPS_PROVIDER);
		locMan.requestLocationUpdates(locMan.NETWORK_PROVIDER, 500, 200, locList);
		Location networkLocation = locMan.getLastKnownLocation(locMan.NETWORK_PROVIDER);
		List<Address> addresses = gC.getFromLocation(networkLocation.getLatitude(), networkLocation.getLongitude(), 2);

		
		if (city){
			return (gC.getFromLocation(networkLocation.getLatitude(), networkLocation.getLongitude(), 1).get(0).getSubAdminArea());
		}
		else if (postal)
			return (gC.getFromLocation(networkLocation.getLatitude(), networkLocation.getLongitude(), 1).get(0).getPostalCode());
		else if (state_prov)
			return (gC.getFromLocation(networkLocation.getLatitude(), networkLocation.getLongitude(), 1).get(0).getAdminArea());
		else
			return "";
	}
	public final class MyLocList implements LocationListener
	{

		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub

		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	public String getCity(String postalCode)
	{
		final String initURL = "http://maps.googleapis.com/maps/api/geocode/json?address=";
		final String finURL = "&sensor=true";
		String requestURL = initURL+postalCode+finURL;
		return "";
	}



}
