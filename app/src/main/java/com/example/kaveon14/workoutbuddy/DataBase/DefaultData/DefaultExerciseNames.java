package com.example.kaveon14.workoutbuddy.DataBase.DefaultData;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultExerciseNames {

    private String fileName;
    private Context exContext;

    public DefaultExerciseNames(Context context,String fileName) {
        exContext = context;
        this.fileName = fileName;
    }

    public List<String> readFileSorted() {
        List<String> fileData = new ArrayList<>();
        AssetManager assetManager = exContext.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(fileData);
        return fileData;
    }
}
