package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class FileDownloadRequest {

    private static FileDownloadRequest fileDownloadRequest;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;//not needed
    private Context context;

    public FileDownloadRequest(Context context) {
        if(this.context == null) {
            this.context = context;
        }
        requestQueue = getRequestQueue();

        new ImageLoader(requestQueue,//not needed
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized FileDownloadRequest getInstance(Context context) {
        if(fileDownloadRequest ==null) {
            fileDownloadRequest = new FileDownloadRequest(context);
        }
        return fileDownloadRequest;
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}