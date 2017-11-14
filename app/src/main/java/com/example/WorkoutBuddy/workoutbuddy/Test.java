package com.example.WorkoutBuddy.workoutbuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Test {

    private static Test test;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;//not needed
    private Context context;

    public Test(Context context) {
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

    public static synchronized Test getInstance(Context context) {
        if(test==null) {
            test = new Test(context);
        }
        return test;
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
