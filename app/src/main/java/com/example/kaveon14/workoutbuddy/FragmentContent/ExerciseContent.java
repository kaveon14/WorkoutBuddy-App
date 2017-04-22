package com.example.kaveon14.workoutbuddy.FragmentContent;

import com.example.kaveon14.workoutbuddy.MainActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ExerciseContent {//change name (this one is for exercises)
    /**
     * An array of sample (dummy) items.
     */
    public static final List<ExerciseItem> ITEMS = new ArrayList<ExerciseItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ExerciseItem> EXERCISE_ITEM_MAP = new HashMap<String, ExerciseItem>();

    private static final int Row_Count = 40;

    static {
        // Add some sample items.
        for (int i = 1; i <= Row_Count; i++) {
            try {
                addItem(createDummyItem(i));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addItem(ExerciseItem item) {
        ITEMS.add(item);
        EXERCISE_ITEM_MAP.put(item.id, item);
    }
    

    private static ExerciseItem createDummyItem(int position) throws IOException {
        return new ExerciseItem(String.valueOf(position), MainActivity.exerciseNames.remove(position-1) , makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ExerciseItem {
        public final String id;
        public final String content;
        public final String details;
        public ExerciseItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
//store R.mimpmap number in txt file in order with exercises
//do the above once, most likely the old fashioned way
//take the ints and put them in a map
//just like the exercise descriptions being set