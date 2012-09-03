package com.fasih.jobsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.os.Looper;
import android.util.JsonReader;
import android.widget.Toast;

public class GrabAndParse extends Activity implements Callable<String> {
	public String URL = "http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=austin%2C+tx&sort=&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	public String data = "";
	public String city = "";

	public GrabAndParse(String initURL,String postal,String endURL){
		this.URL = initURL + postal+ endURL;
	}
	public GrabAndParse(){
	}
	public void setRequestURL(String URL){
		this.URL = URL;
	}
	public void setRequestURL(String initURL,String postal, String endURL){
		this.URL = initURL+postal+endURL;
	}
	public void setMapsCity(String x){
		this.city = x;
	}
	public String getMapsCity(){
		return this.city;
	}
	public String getCityFromJSON(final String postal_zip_code,final String initURL,final String finURL){
		Thread thread = new Thread(new Runnable(){
			public void run() {
				String mapsURL = initURL + postal_zip_code + finURL;
				System.out.println(mapsURL);
				HttpGet request = new HttpGet(mapsURL);
				DefaultHttpClient httpClient = new DefaultHttpClient();
				JSONArray jsArray;
				JSONObject obj;
				try{
					HttpResponse response = httpClient.execute(request);
					HttpEntity responseEntity = response.getEntity();
					
					String test = EntityUtils.toString(responseEntity);
					JSONObject res = new JSONObject(test);
					JSONArray mapsData = res.getJSONArray("results");

					JSONObject test1 = mapsData.getJSONObject(0);
					JSONArray jArr = test1.getJSONArray("address_components");
					for(int i=0;i<jArr.length();i++){
						if (jArr.getJSONObject(i).getJSONArray("types").getString(0).equals("locality")){
							setMapsCity(jArr.getJSONObject(i).getString("long_name"));
							return;
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		});
		thread.start();
		if (!thread.isAlive())
			return city;
		else
			return "";
	}
	public String getJSON() {

		Thread t = new Thread(new Runnable(){
			public void run(){
				//Looper.prepare();
				ArrayList<String> json = new ArrayList<String>();
				HttpGet request = new HttpGet(URL);        
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String theString = new String("");
				JSONArray jsArray;

				//JSONArray jsaPersons = null ;

				try {
					HttpResponse response = httpClient.execute(request);
					HttpEntity responseEntity = response.getEntity();
					// Read response data into buffer
					//char[] buffer = new char[(int)responseEntity.getContentLength()];
					InputStream stream = responseEntity.getContent();

					// InputStreamReader reader = new InputStreamReader(stream);
					BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

					/*String test = EntityUtils.toString(responseEntity);
					JSONObject res = new JSONObject(test);
					JSONArray jobs = res.getJSONArray("results");
					JSONObject obj = jobs.getJSONObject(0);
					String check = obj.getString("jobtitle");
					String check2 = obj.getString("company");
					System.out.println(check+":"+check2);

					*/
					StringBuilder builder = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						json.add(line);
					}
					stream.close();
					//theString = builder.toString();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        
			}
		});
		t.start();
		return "";
	}
	public String call() throws Exception {
		//String mapsURL = initURL + postal_zip_code + finURL;
		String mapsURL = this.URL;
		System.out.println(mapsURL);
		HttpGet request = new HttpGet(mapsURL);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		JSONArray jsArray;
		JSONObject obj;
		try{
			HttpResponse response = httpClient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			
			String test = EntityUtils.toString(responseEntity);
			JSONObject res = new JSONObject(test);
			JSONArray mapsData = res.getJSONArray("results");

			JSONObject test1 = mapsData.getJSONObject(0);
			JSONArray jArr = test1.getJSONArray("address_components");
			for(int i=0;i<jArr.length();i++){
				if (jArr.getJSONObject(i).getJSONArray("types").getString(0).equals("locality")){
					return jArr.getJSONObject(i).getString("long_name");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Oops");
		}
		return "--";
		
	}
}

