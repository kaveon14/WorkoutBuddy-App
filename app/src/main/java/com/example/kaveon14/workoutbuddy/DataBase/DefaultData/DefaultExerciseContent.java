package com.example.kaveon14.workoutbuddy.DataBase.DefaultData;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class DefaultExerciseContent {

    private Context exContext;
    private final String EXERCISE_NAMES = "ExerciseNames.txt";
    private final String EXERCISE_DESCRIPTIONS = "ExerciseDescriptions.txt";

    public DefaultExerciseContent(Context context) {
        exContext = context;
    }

    public List<String> getExerciseNames() {
        List<String> fileData = new ArrayList<>();
        AssetManager assetManager = exContext.getAssets();
        try {
            InputStream inputStream = assetManager.open(EXERCISE_NAMES);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.add(line);
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(fileData);
        return fileData;
    }

    public Hashtable<String,String> getExerciseDescriptions() {
        Hashtable<String,String> descriptions = new Hashtable<>();
        String line = "";String scannerLine;
        try {
            Scanner scan = openFileWithScanner(EXERCISE_DESCRIPTIONS);
            while (scan.hasNext()) {
                scannerLine = scan.nextLine();
                if (scannerLine.equalsIgnoreCase("@//")) {
                    line = line + System.lineSeparator();
                    Scanner mapKey = new Scanner(line);
                    String exerciseName = mapKey.nextLine();
                    descriptions.put(exerciseName, line.substring(exerciseName.length()));
                    line = "";
                } else {
                    line = line + scannerLine + System.lineSeparator();
                }
            }
            scan.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return descriptions;
    }

    private Scanner openFileWithScanner(String fileName) throws IOException {
        return new Scanner(new DataInputStream(exContext.getAssets().open(fileName)));
    }
}
