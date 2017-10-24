package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    public String sendGetRequest(String requestURL) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            System.out.println("WTF");
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public String sendPostRequest(String requestURL,HashMap<String,String> postDataParams) {
        String response = "";
        try {
            URL url = new URL(requestURL);
            response = getServerResponse(url,postDataParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getServerResponse(URL url,HashMap<String,String> postDataParams) throws IOException {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = getConnection(url,"POST");

        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        writer.write(getPostDataString(postDataParams));

        writer.flush();
        writer.close();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            builder = new StringBuilder();
            String response;
            //Reading server response
            while ((response = br.readLine()) != null) {
                builder.append(response);
            }
        }
        return builder.toString();
    }

    private HttpURLConnection getConnection(URL url,String method) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
