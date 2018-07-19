package com.dpu.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GetAPIClient {

	public static String callGetAPI(String requestURL, HashMap<String, String> headers) throws Exception {
		
		StringBuilder apiResponse = null;
		
		try {			
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type",	"application/json");
			if(headers != null && headers.containsKey("authorization")){
				conn.setRequestProperty("Authorization", "Bearer "+headers.get("authorization"));
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			apiResponse = new StringBuilder();
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				while ((line = rd.readLine()) != null) {
					apiResponse.append(line);
				}
			} catch (IOException e) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				String line = "";
				while ((line = rd.readLine()) != null) {
					apiResponse.append(line);
				}
			}
		} catch (UnsupportedEncodingException e) {
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		
		return apiResponse != null ? apiResponse.toString() : null;
	}

}