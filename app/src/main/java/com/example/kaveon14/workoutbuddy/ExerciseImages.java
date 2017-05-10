package com.example.kaveon14.workoutbuddy;

import android.content.Context;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;
import static com.example.kaveon14.workoutbuddy.FragmentContent.ExerciseContent.EXERCISE_ITEM_MAP;

public class ExerciseImages {

    private Context exContext;
    public static Map<String,String> EXERCISE_IMAGE_MAP = new Hashtable<>();
    private final R.mipmap mipmapResources = new R.mipmap();
    private final Class<R.mipmap> mipmapClass = R.mipmap.class;
    private final Field[] mipmapFields = mipmapClass.getDeclaredFields();

    public ExerciseImages(Context context) {
        exContext = context;
        addElementsToMipMap();
    }

    public void setImageMap() {
        try {
            setElementsToImageMap();
        } catch(IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void setElementsToImageMap() throws IllegalAccessException {
        int increment=0,maxImages = mipmapFields.length;
        while(increment<maxImages) {
            getImageAndItemNameForMap(increment);
            increment++;
        }
    }

    private void getImageAndItemNameForMap(int mainLoop) throws IllegalAccessException {
        String itemName;
        String uneditedImageName;String editedImageName;
        for(int x=1;x<EXERCISE_ITEM_MAP.size();x++) {
            uneditedImageName = mipmapFields[mainLoop].getName();
            editedImageName = uneditedImageName.replace("_", " ");
            itemName = EXERCISE_ITEM_MAP.get(String.valueOf(x)).toString().replace("-", " ");
            matchImageAndItemToMap(x,mainLoop,editedImageName,itemName);
        }
    }

    private void  matchImageAndItemToMap(int mapID,int mainLoop,String string1,String string2) throws IllegalAccessException {
        String map_id = String.valueOf(mapID);
        if(compareItemAndImageName(string1,string2)) {
            addImagesToMap(map_id,mainLoop);
        }
    }

    private void addImagesToMap(String mapID,int fieldLocation) throws IllegalAccessException {
        String imageID = String.valueOf(mipmapFields[fieldLocation].getInt(mipmapResources));
        EXERCISE_IMAGE_MAP.put(mapID,imageID);
    }

    private boolean compareItemAndImageName(String itemName,String imageName) {
        return imageName.equalsIgnoreCase(itemName);
    }

    private void addElementsToMipMap() {
        for (int i = 0, max = mipmapFields.length; i < max; i++) {
            final int resourceId;
            try {
                resourceId = mipmapFields[i].getInt(mipmapResources);
                EXERCISE_IMAGE_MAP.put(String.valueOf(i+1),String.valueOf(resourceId));
            } catch (Exception e) {
                continue;
            }
        }
    }
}
