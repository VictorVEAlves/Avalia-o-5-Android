package com.example.avaliacao_5_android;

import android.content.Context;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class CustomVolleyRequestQueue {

    private static CustomVolleyRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private CustomVolleyRequestQueue(Context ctx){
        this.mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized CustomVolleyRequestQueue getInstance(Context context){
        if(mInstance == null){
            mInstance = new CustomVolleyRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            DiskBasedCache cache = new DiskBasedCache(mCtx.getCacheDir(), 10*1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

}
