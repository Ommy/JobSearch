package com.fasih.jobsearch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

public class StepTwo extends Activity {

	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		GrabAndParse gP = new GrabAndParse();
		String JSON_TEXT = gP.getJSON();

	}
}
