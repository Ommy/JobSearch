package com.fasih.jobsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class HttpClient {
	private static final String TAG = "HttpClient";
	
	public static JSONObject SendHttpPost(String URL,JSONObject jsonObjSend){
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPostReq = new HttpPost(URL);
			
			StringEntity se;
			se = new StringEntity(jsonObjSend.toString());
			httpPostReq.setEntity(se);
			httpPostReq.setHeader("Accept", "application/json");
			httpPostReq.setHeader("Content-type", "application/json");
			httpPostReq.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression
			
			long t = System.currentTimeMillis();
			HttpResponse resp = (HttpResponse) httpClient.execute(httpPostReq);
			Log.i(TAG,"HTTPResponse received in ["+(System.currentTimeMillis()-t)+"ms]");
			
			//get the response
			HttpEntity entity = resp.getEntity();
			if (entity != null){
				InputStream istream = entity.getContent();
				Header contentEncoding = resp.getFirstHeader("Content-Encoding");
				if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")){
					istream = new GZIPInputStream(istream);
				}
				String result = convertStreamToString(istream);
				istream.close();
				result = result.substring(1,result.length()-1);
				//transform string to JSON object
				JSONObject jsonObjRecv = new JSONObject(result);
				Log.i(TAG,"<JSONObject>\n"+jsonObjRecv.toString()+"\n</JSONObject>");
				return jsonObjRecv;
			}

		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static String convertStreamToString(InputStream istream) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		try{
			while((line=reader.readLine())!=null)
				sb.append(line+"\n");
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try {
				istream.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
