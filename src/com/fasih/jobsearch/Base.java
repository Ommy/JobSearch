package com.fasih.jobsearch;
//http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=austin%2C+tx&sort=&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.content.Intent;
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
		//proceed.setVisibility(View.GONE);

		TextView tv = (TextView)findViewById(R.id.tV);
		ConnectivityManager cMan = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (!nInfo.isAvailable())
			nInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (nInfo.isConnected())
			tv.setText("Connected"); 
		else
			tv.setText("Not Connected");
		GrabLocation grab = new GrabLocation();
		grab.getConnectionInfo(getBaseContext(), locationResult);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_base, menu);
		return true;
	}
	LocationResult locationResult = new LocationResult(){
		@Override
		public void gotLocation(Location location){
			final ExecutorService service;
			final Future<String> task;
			Geocoder gC = new Geocoder(getBaseContext(), Locale.getDefault());
			try {
				String city = gC.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
				String state_province = gC.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0).getAdminArea();
				String postal_zip = gC.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0).getPostalCode().replace(" ", "");
				int duration = Toast.LENGTH_LONG;


				Bundle b = new Bundle();
				b.putString("STATE_PROVINCE", state_province);

				if (city == null || city.equals("")){
					service = Executors.newFixedThreadPool(1);        
					task    = service.submit(new GrabAndParse("http://maps.googleapis.com/maps/api/geocode/json?address=",postal_zip,"&sensor=false"));
					city = task.get();
				}
				b.putString("CITY", city);
				Intent i = new Intent(Base.this,SearchOptions.class);
				i.putExtras(b);
				startActivity(i);
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};
}

