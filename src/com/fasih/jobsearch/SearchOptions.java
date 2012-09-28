package com.fasih.jobsearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchOptions extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_job);
		Bundle localityDataBundle = getIntent().getExtras();
		//get locality data from JSON response that was put into the bundle
		String city = localityDataBundle.getString("CITY");
		String province_or_state = localityDataBundle.getString("STATE_OR_PROVINCE");
		double longitude = localityDataBundle.getDouble("Longitude");
		double latitude = localityDataBundle.getDouble("Latitude");
		
		//initialize some of the textfields and buttons
		Button searchBTN = (Button)findViewById(R.id.searchBTN);
		//Button locateBTN = (Button)findViewById(R.id.Location);
		final EditText jobType = (EditText)findViewById(R.id.JobType);
		EditText locationInput = (EditText)findViewById(R.id.Location);
		locationInput.setText(city);
		EditText includeTheseWords = (EditText)findViewById(R.id.includeWords);
		EditText avgSalary = (EditText)findViewById(R.id.avgSalary);
		Spinner jobTypeSpinner = (Spinner)findViewById(R.id.jobTypeSpinner);
		
		//Button locate
		searchBTN.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String job = jobType.getText().toString();
				System.out.println(job);
			}
		});
		/*locateBTN.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});*/
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
