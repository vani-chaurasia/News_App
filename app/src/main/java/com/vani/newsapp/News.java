package com.vani.newsapp;

public class News {

    private String mTitle;
    private String mImageUrl;
    private String mDate;
    private String mUrl;

    News(String title, String imageUrl,String date, String url){
        mTitle=title;
        mImageUrl=imageUrl;
        mDate=date;
        mUrl=url;
    }

    public String getmDate() {
       return mDate;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmUrl() {
        return mUrl;
    }
}
