package com.example.nyuad;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class MyAlarmManager extends BroadcastReceiver {
    Context _context;
    @Override
    public void onReceive(Context context, Intent intent) {
       _context= context;
       
       Uri server = Uri.parse("http://alert2sign.mimix.me/");
       java.net.URI juri;
	try {
		juri = new java.net.URI(server.toString());
		this.getHttpResponse(juri);
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
   }
    
    public void getHttpResponse(URI uri) {
    	Log.d("sup", "Going to make a get request");
    	StringBuilder response = new StringBuilder();
    	try {
    		HttpGet get = new HttpGet();
    		get.setURI(uri);
    		DefaultHttpClient httpClient = new DefaultHttpClient();
    		HttpResponse httpResponse = httpClient.execute(get);
    		if (httpResponse.getStatusLine().getStatusCode() == 200) {
    			Log.d("demo", "HTTP Get succeeded");

    			HttpEntity messageEntity = httpResponse.getEntity();
    			InputStream is = messageEntity.getContent();
    			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    			String line;
    			while ((line = br.readLine()) != null) {
    				response.append(line);
    			}
    		}
    	} catch (Exception e) {
    		Log.e("demo", e.getMessage());
    	}
    	Log.d("demo", "Done with HTTP getting");
 	    MainActivity ma = new MainActivity();
		ma.createNotification("SUP");
 	    ma.translate(response.toString());
    }
}  