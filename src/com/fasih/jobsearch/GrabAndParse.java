package com.fasih.jobsearch;

import java.util.concurrent.Callable;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.app.Activity;

public class GrabAndParse extends Activity implements Callable<String> {
	public String URL = "http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=austin%2C+tx&sort=&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	public String data = "";
	public String city = "";

	public GrabAndParse(String initURL,String postal,String endURL){
		this.URL = initURL + postal+ endURL;
	}
	public void setRequestURL(String URL){
		this.URL = URL;
	}
	public void setRequestURL(String initURL,String postal, String endURL){
		this.URL = initURL+postal+endURL;
	}
	public String call() throws Exception {
		String mapsURL = this.URL;
		System.out.println(mapsURL);
		HttpGet request = new HttpGet(mapsURL);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			String test = EntityUtils.toString(responseEntity);
			return test;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Oops");
		}
		return "--";

	}
}