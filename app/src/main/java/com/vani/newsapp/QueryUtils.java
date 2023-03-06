package com.vani.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String REQUEST_URL="https://newsapi.org/v2/everything?q=tesla&from=2022-12-27&sortBy=publishedAt&apiKey=75084496253c48999731173a5055428a";
    private static final String LOG_TAG =QueryUtils.class.getSimpleName();


    private QueryUtils(){
    }

    public static List<News> extractNews(String newsJSON){
        Log.d(LOG_TAG, "Beginning of extractNews method");
        if(TextUtils.isEmpty(newsJSON)) {
            Log.d(LOG_TAG, "TextUtils is empty");
            return null;
        }

        List<News> news=new ArrayList<>();

        try{
            Log.d(LOG_TAG, "Inside try block of jsonobject");
            JSONObject baseJsonResponse=new JSONObject(newsJSON);
            JSONArray newsArray=baseJsonResponse.getJSONArray("results");

            for(int i=0;i<newsArray.length();i++){
                Log.d(LOG_TAG, "Inside for loop");

                //Log.d(LOG_TAG, "Json value are extracting");
                JSONObject currentNews=newsArray.getJSONObject(i);
                Log.d(LOG_TAG, "get json object");
                //JSONObject source=currentNews.getJSONObject("properties");
                Log.d(LOG_TAG, "Properties");
                String title=currentNews.getString("title");
                Log.d(LOG_TAG, "place");
                String imageUrl=currentNews.getString("image_url");
                String date=currentNews.getString("pubDate");
                String url=currentNews.getString("link");
                Log.d(LOG_TAG, "url");

                News cnews=new News(title,imageUrl,date, url);
                Log.d(LOG_TAG, "New news object is created");
                news.add(cnews);
                Log.d(LOG_TAG, "News is added");


            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Problem in parsing the news");
        }
        return news;
    }

    private static URL createUrl(String stringUrl){
        Log.d(LOG_TAG, "CreateUrl method called");
        URL url=null;
        try{
            Log.d(LOG_TAG, "Inside try block of createUrl");
            url=new URL(stringUrl);
        }catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error with creating url",exception);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws Exception {
        Log.d(LOG_TAG, "MakehttpRequest method begins");
        String jsonResponse="";
        if(url==null){
            Log.d(LOG_TAG, "Inside if of makeHttpRequest");
            return jsonResponse;
        }

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            Log.d(LOG_TAG, "openConnection method");
            urlConnection.setRequestMethod("GET");
            Log.d(LOG_TAG, "setRequestMethod");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            Log.d(LOG_TAG, "setReadTimeout");
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            Log.d(LOG_TAG, "setConnectTimeout");
            urlConnection.connect();
            Log.d(LOG_TAG, "connect method");

            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                Log.d(LOG_TAG, "Input Stream "+inputStream);
                jsonResponse=readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error response code: "+urlConnection.getResponseCode());
            }

        }catch(IOException e){
            Log.e(LOG_TAG, "Problem retrieving the news",e);
        }finally{
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws Exception{
        Log.d(LOG_TAG, "Read from stream method begins");
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            Log.d(LOG_TAG, "Input Stream is not null");
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> fetchNews(String requestUrl) throws Exception {
        Log.d(LOG_TAG, "Beginning of fetchNews method");
        /*try {
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }*/

        URL url=createUrl(requestUrl);
        Log.d(LOG_TAG, "Value of url inside fetch news "+url);
        String jsonResponse=null;
        try{
            Log.d(LOG_TAG, "Inside the try block of jsonResponse");
            jsonResponse=makeHttpRequest(url);

        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request",e);
        }
        List<News> c2news=extractNews(jsonResponse);
        return c2news;
    }
}
