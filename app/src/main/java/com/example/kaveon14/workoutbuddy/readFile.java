package com.example.kaveon14.workoutbuddy;

import android.content.Context;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class readFile {

    private String fileName;
    private Context myContext;

    public readFile(Context context,String fileName) {
        myContext = context;
        this.fileName = fileName;
    }

    private Scanner openFileWithScanner(String fileName) throws IOException {
        return new Scanner(new DataInputStream(myContext.getAssets().open(fileName)));
    }

    public Map<String,String> readFile() throws IOException {
        Map<String,String> defaultWorkouts = new Hashtable<>();
        String line = "";String scannerLine;
        Scanner scan = openFileWithScanner(fileName);
        while(scan.hasNext()) {
            scannerLine = scan.nextLine();
            if(scannerLine.equalsIgnoreCase("@//")) {
                line = line + System.lineSeparator();
                Scanner mapKey = new Scanner(line);
                String nameOfWorkout = mapKey.next();
                defaultWorkouts.put(nameOfWorkout,line.substring(8));
                line = "";
            } else {
                line = line + scannerLine + System.lineSeparator();
            }
        }
        return defaultWorkouts;
    }
}
