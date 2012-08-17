package com.fasih.jobsearch;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
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
        	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_base, menu);
        return true;
    }

    
}
