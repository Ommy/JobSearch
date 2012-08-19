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
        {
        	tv.setTextColor(Color.GREEN);
        	tv.setText("Connected");
        }
        else
        {
        	tv.setTextColor(Color.RED);
        	tv.setText("Not Connected");
        }
        LocationManager lM = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Location userLocation = lM.getLastKnownLocation(lM.NETWORK_PROVIDER);
        if (userLocation != null)
        	tv.append("\n"+userLocation.toString());
        else
        	tv.append("No location available");
        Geocoder gC = new Geocoder(getBaseContext(),Locale.getDefault());
        try {
			List<Address> prox = gC.getFromLocation(userLocation.getLatitude(), userLocation.getLongitude(), 1);
			tv.append("\n"+prox.get(0).getPostalCode().replaceAll(" ", ""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			tv.append(":::::::");
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_base, menu);
        return true;
    }
    
    public String getCity(String postalCode)
    {
    	final String initURL = "http://maps.googleapis.com/maps/api/geocode/json?address=";
    	final String finURL = "&sensor=true";
    	String requestURL = initURL+postalCode+finURL;
    	return "";
    }

    
}
