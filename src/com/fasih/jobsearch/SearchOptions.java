package com.fasih.jobsearch;

import android.app.Activity;
import android.os.Bundle;

public class SearchOptions extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_job);
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
