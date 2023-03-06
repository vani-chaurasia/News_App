package com.vani.newsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    Handler handler=new Handler();
    ProgressDialog progressDialog;
    public NewsAdapter(Context context, List<News> news) {
        super(context,0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        News currentNews=getItem(position);

        TextView title=(TextView)listItemView.findViewById(R.id.title);
        title.setText(currentNews.getmTitle());

        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText(String.valueOf(currentNews.getmDate()));

        ImageView imageView=(ImageView)listItemView.findViewById(R.id.imageView);
        String url="https://images.news18.com/ibnlive/uploads/2023/01/supporters-of-npp-candidate-from-meghalayas-ranikor-constituency-martin-m-danggo-join-bjp.jpg";
        new NewsImage(currentNews.getmImageUrl(),imageView).start();


        return listItemView;
    }


    //Converting imageurl to image
    class NewsImage extends Thread{


        Handler handler=new Handler();
        String URL;
        Bitmap bitmap;
        ImageView imageView;

        NewsImage(String URL,ImageView imageView){
            Log.d("QueryUtils", "Inside NewsImage Constructor");
            this.URL=URL;
            this.imageView=imageView;
        }

        @Override
        public void run () {


            InputStream inputStream = null;
            try {
                Log.d("QueryUtils", "Try block of InputStream");
                Log.d("QueryUtils", "Url is "+URL);
                inputStream = new URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    Log.d("QueryUtils", "ImageView is set");
                }
            });

        }
    }

}
