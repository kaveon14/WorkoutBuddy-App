package com.example.WorkoutBuddy.workoutbuddy;

import android.app.VoiceInteractor;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;

import java.net.URL;

//TODO SEnd file name and to php url

public class VolleyTest {

    MainActivity mainActivity;
    String url = "http://10.100.69.171/WorkoutBuddy_Scripts/FileDownload.php?file_name=JPEG_20171103_154733_1895954797.jpg";


    public VolleyTest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void test() {
        RequestQueue requestQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());
        ImageView.ScaleType type = ImageView.ScaleType.CENTER;
        Bitmap.Config config = Bitmap.Config.ALPHA_8;

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                System.out.println("WTF: "+response.getByteCount());
            }
        },0,0,null,null);



        requestQueue.add(request);
    }
}
