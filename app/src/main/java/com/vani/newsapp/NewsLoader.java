package com.vani.newsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>>{

    private String mUrl;
    private static final String LOG_TAG=NewsLoader.class.getName();

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }



    @Override
    protected void onStartLoading() {
        Log.d("QueryUtils", "Inside onStartLoading");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        Log.d("QueryUtils", "Inside loadInBackground");
        if(mUrl==null){
            Log.d(LOG_TAG, "mUrl is null");
            return null;
        }
        List<News> cnews= null;
        try {
            Log.d(LOG_TAG, "Inside try block of load"+mUrl);
            cnews = QueryUtils.fetchNews(mUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnews;
    }
}
