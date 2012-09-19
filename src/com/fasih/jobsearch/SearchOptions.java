package com.fasih.jobsearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchOptions extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_job);
		Bundle localityDataBundle = getIntent().getExtras();
		String city = localityDataBundle.getString("city");
		Button searchBTN = (Button)findViewById(R.id.searchBTN);
		searchBTN.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//if ()
			}
		});
		//setContentView(R.layout.search_options);
		/* What to populate listview with
		 * q -> Query 
		 * l -> Location
		 * leave radius at default 25
		 * st -> Site Type?
		 * jt -> Job Type
		 * start -- default
		 * limit -- default
		 * userIP *REQUIRED*
		 * */
	}
}
