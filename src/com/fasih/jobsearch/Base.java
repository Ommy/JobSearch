package com.fasih.jobsearch;
//http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=austin%2C+tx&sort=&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasih.jobsearch.GrabLocation.LocationResult;

import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Base extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		TextView tV = (TextView)findViewById(R.id.tV);
		ConnectivityManager cMan = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		tV.setText(nInfo.isConnected()+"--");
		Geocoder gC = new Geocoder(getBaseContext(),Locale.getDefault());
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
			Context context = getApplicationContext();
			CharSequence text = location.getProvider();
			int duration = Toast.LENGTH_SHORT;
			boolean cityNullFlag = false;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			try {

				String city = gC.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
				String state_province = gC.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0).getAdminArea();
				String postal_zip = gC.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0).getPostalCode().replace(" ", "");
				Bundle b = new Bundle();
				b.putString("STATE_PROVINCE", state_province);
				b.putDouble("Longitude",location.getLongitude());
				b.putDouble("Latitude", location.getLatitude());
				if (city == null || city.equals(""))
					cityNullFlag = true;
				if (!cityNullFlag){
					service = Executors.newFixedThreadPool(1);        
					task    = service.submit(new GrabAndParse("http://maps.googleapis.com/maps/api/geocode/json?address=",postal_zip,"&sensor=false"));
					city 	= task.get();
				}
				if (cityNullFlag){
					JSONObject res = new JSONObject(city);
					JSONArray mapsData = res.getJSONArray("results");

					JSONObject test1 = mapsData.getJSONObject(0);
					JSONArray jArr = test1.getJSONArray("address_components");
					for(int i=0;i<jArr.length();i++){
						if (jArr.getJSONObject(i).getJSONArray("types").getString(0).equals("locality")){
							city = jArr.getJSONObject(i).getString("long_name");
						}
					}
				}
				b.putString("CITY", city);
				Intent i = new Intent(Base.this,SearchOptions.class);
				i.putExtras(b);
				startActivity(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	};
}

