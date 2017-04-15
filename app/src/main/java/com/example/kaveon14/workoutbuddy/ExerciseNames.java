package com.example.kaveon14.workoutbuddy;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExerciseNames extends MainActivity {

    private String finalFileName;
    private Context exContext;

    public ExerciseNames(Context context,String fileName) {
        exContext = context;
        finalFileName = fileName;
        InputStream inputStream = null;
        try {
            inputStream = exContext.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readFile() {
        List<String> fileLines = new ArrayList<>();
        AssetManager assetManager = exContext.getAssets();
        try {
            InputStream inputStream = assetManager.open(finalFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
                fileLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

}
