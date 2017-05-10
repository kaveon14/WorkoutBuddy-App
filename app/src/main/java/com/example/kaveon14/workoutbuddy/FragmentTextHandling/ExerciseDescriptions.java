package com.example.kaveon14.workoutbuddy.FragmentTextHandling;


import android.content.Context;
import com.example.kaveon14.workoutbuddy.FragmentContent.ExerciseContent;
import com.example.kaveon14.workoutbuddy.Fragments.BlankExerciseFragment;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ExerciseDescriptions {

    private Context exContext;

    public ExerciseDescriptions(Context context) {
        exContext = context;
    }

    public void setExerciseDescriptions() {
        try {
            setDescriptionForEachExercise();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private Scanner openFileWithScanner(String fileName) throws IOException {
        return new Scanner(new DataInputStream(exContext.getAssets().open(fileName)));
    }

    private void addDescriptionsToMap(String id,String data) {
        BlankExerciseFragment.exercisesDescriptions.put(id,data);
    }

    private void getDataFromExerciseFile(String fileName) throws IOException {
        String line = "";String scannerLine;int increment = 0;
        Scanner scan = openFileWithScanner(fileName);
        while(scan.hasNext()) {
            scannerLine = scan.nextLine();
            if(scannerLine.equalsIgnoreCase("@//")) {
                increment++;
                line = line + System.lineSeparator();
                addDescriptionsToMap(String.valueOf(increment),line);
                line = "";
            } else {
                line = line + scannerLine + System.lineSeparator();
            }
        }
    }

    private void setDescriptionForEachExercise() throws IOException {
        for(int x=1;x<ExerciseContent.EXERCISE_ITEM_MAP.size();x++) {
            getDataFromExerciseFile("ExerciseDescriptions.txt");//change to match exercise images
        }
    }
}
