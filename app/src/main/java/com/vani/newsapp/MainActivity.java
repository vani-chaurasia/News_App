package com.vani.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter madapter;
    private static final String REQUEST_URL="https://newsdata.io/api/1/news?country=in&apikey=pub_16166e4c36d93eb9f5839bd1a045573cf0685";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager=getLoaderManager();
        Log.d("QueryUtils", "Get loader manager");
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        Log.d("QueryUtils", "Loader Init");

        ListView NewsListView=(ListView)findViewById(R.id.List);
        madapter= new NewsAdapter(this, new ArrayList<News>());
        NewsListView.setAdapter(madapter);
        Log.d("QueryUtils", "Set Adapter");

        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                News currentNews = madapter.getItem(i);
                Uri newsUri = Uri.parse(currentNews.getmUrl());
                Log.d("QueryUtils", "Inside set On Item Click Listener");
                Intent intent=new Intent(Intent.ACTION_VIEW,newsUri);
                startActivity(intent);
            }
        });


    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.d("QueryUtils", "Inside onCreateLoader");

        return new NewsLoader(this,REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        Log.d("QueryUtils", "Inside onLoadFinished");

        madapter.clear();

        if(news!=null && !news.isEmpty()){
            Log.d("QueryUtils", "Inside if of onLoadFinished");
            madapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.d("QueryUtils", "Inside onLoaderReset");
         madapter.clear();
    }
}