package com.fasih.jobsearch;
//http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=austin%2C+tx&sort=&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasih.jobsearch.GrabLocation.LocationResult;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Base extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		Button proceed = (Button)findViewById(R.id.contBTN);
		proceed.setVisibility(View.GONE);
		
		TextView tv = (TextView)findViewById(R.id.tV);
		ConnectivityManager cMan = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (!nInfo.isAvailable())
			nInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (nInfo.isConnected())
			tv.setText("Connected"); 
		else
			tv.setText("Not Connected");
		String city = "";

		try {
			city = getLocation(Locale.getDefault(),getBaseContext(),true,false,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv.append("\n"+city);
		if(nInfo.isConnected() && city != ""){
			proceed.setVisibility(View.VISIBLE);
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_base, menu);
		return true;
	}
	public String getLocation(Locale locale, Context context, boolean city, boolean postal, boolean state_prov) throws IOException{
		Geocoder gC = new Geocoder(context,locale);
		GrabLocation myLocation = new GrabLocation();
		myLocation.getConnectionInfo(this,locationResult);
		Location location = myLocation.locMan.getLastKnownLocation(myLocation.getWorkingProvider());
		
		if (city){
			return (gC.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getSubAdminArea());
		}
		else if (postal)
			return (gC.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getPostalCode());
		else if (state_prov)
			return (gC.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getAdminArea());
		else
			return "nothing";
	}
	public final class MyLocList implements LocationListener
	{
		public double latitude;
		public double longitude;
		public void onLocationChanged(Location arg0) {
			latitude = arg0.getLatitude();
			longitude = arg0.getLongitude();

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

	LocationResult locationResult = new LocationResult(){
		@Override
		public void gotLocation(Location location){
			
		}
	};


}
