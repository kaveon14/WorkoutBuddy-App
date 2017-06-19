package com.example.kaveon14.workoutbuddy.DataBase.DefaultData;

import android.content.Context;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class DefaultWorkouts {

    private String fileName;
    private Context myContext;

    public DefaultWorkouts(Context context, String fileName) {
        myContext = context;
        this.fileName = fileName;
    }

    private Scanner openFileWithScanner(String fileName) throws IOException {
        return new Scanner(new DataInputStream(myContext.getAssets().open(fileName)));
    }
// TODO parse the workout names
    public Map<String,String> getSubWorkoutData() throws IOException {
        Map<String,String> defaultWorkouts = new Hashtable<>();
        String line = "";String scannerLine;
        Scanner scan = openFileWithScanner(fileName);
        while(scan.hasNext()) {
            scannerLine = scan.nextLine();
            if(scannerLine.equalsIgnoreCase("@//")) {
                line = line + System.lineSeparator();
                Scanner mapKey = new Scanner(line);
                String nameOfWorkout = mapKey.next();
                defaultWorkouts.put(nameOfWorkout,line.substring(nameOfWorkout.length()));
                line = "";
            } else {
                line = line + scannerLine + System.lineSeparator();
            }
        }
        scan.close();
        return defaultWorkouts;
    }
}
