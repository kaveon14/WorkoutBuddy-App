package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.content.Context;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import com.example.kaveon14.workoutbuddy.R;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ExerciseImages {

    private Context context;
    protected static Map<String,String> EXERCISE_IMAGE_MAP = new Hashtable<>();
    private final R.mipmap mipmapResources = new R.mipmap();
    private final Class<R.mipmap> mipmapClass = R.mipmap.class;
    private final Field[] mipmapFields = mipmapClass.getDeclaredFields();

    public ExerciseImages() {

    }

    public ExerciseImages(Context context) {
        this.context = context;
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

    private void getImageAndItemNameForMap(int imagePosition) throws IllegalAccessException {
        String itemName;
        ExerciseTable exerciseTable = new ExerciseTable(context);
        List<String> EXERCISE_LIST = exerciseTable.getColumn(COLUMN_EXERCISES);
        String uneditedImageName;String editedImageName;
        for(int x=1;x<EXERCISE_LIST.size();x++) {
            uneditedImageName = mipmapFields[imagePosition].getName();
            editedImageName = uneditedImageName.replace("_", " ");
            itemName = EXERCISE_LIST.get(x).replace("-", " ");
            matchImageAndItemToMap(imagePosition,editedImageName,itemName);
        }
    }

    private void  matchImageAndItemToMap(int imageId,String imageName,String exName) throws IllegalAccessException {
        if(compareItemAndImageName(imageName,exName)) {
            addImagesToMap(exName,imageId);
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
