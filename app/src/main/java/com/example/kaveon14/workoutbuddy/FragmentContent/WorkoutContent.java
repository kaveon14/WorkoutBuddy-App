package com.example.kaveon14.workoutbuddy.FragmentContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.kaveon14.workoutbuddy.WorkoutNames.standardWorkouts;
import static com.example.kaveon14.workoutbuddy.WorkoutNames.workoutNames;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WorkoutContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<WorkoutItem> ITEMS = new ArrayList<WorkoutItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, WorkoutItem> ITEM_MAP = new HashMap<String, WorkoutItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(WorkoutItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static WorkoutItem createDummyItem(int position) {
        System.out.println("wtf: "+workoutNames.get(position-1));
        System.out.println("pos: "+position);
        return new WorkoutItem(String.valueOf(position), workoutNames.remove(position-1), makeDetails(position));
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
    public static class WorkoutItem {
        public final String id;
        public final String content;
        public final String details;

        public WorkoutItem(String id, String content, String details) {
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
