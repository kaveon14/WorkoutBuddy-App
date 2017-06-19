package com.example.kaveon14.workoutbuddy.DataBase.Data;
// TODO put this in different package
import android.content.Context;
import android.content.res.Resources;

import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_IMAGES;

import com.example.kaveon14.workoutbuddy.R;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ExerciseImages {

    private Context context;
    protected static Map<String,String> EXERCISE_IMAGE_MAP = new Hashtable<>();
    private final Class<R.mipmap> mipmapClass = R.mipmap.class;
    private final Field[] mipmapFields = mipmapClass.getDeclaredFields();

    public ExerciseImages() {

    }

    public ExerciseImages(Context context) {
        this.context = context;
        //addElementsToMipMap();//this has no use
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

    private void matchImageAndItemToMap(int imageId,String imageName,String exName) throws IllegalAccessException {
        if(compareItemAndImageName(imageName,exName)) {
            addImagesToMap(exName,imageId);
        }
    }

    private void addImagesToMap(String mapID,int fieldLocation) throws IllegalAccessException {
        final R.mipmap mipmapResources = new R.mipmap();
        String imageID = String.valueOf(mipmapFields[fieldLocation].getInt(mipmapResources));
        EXERCISE_IMAGE_MAP.put(mapID,imageID);
    }

    private boolean compareItemAndImageName(String itemName,String imageName) {
        return imageName.equalsIgnoreCase(itemName);
    }
//what is the use of this
    private void addElementsToMipMap() {
        final R.mipmap mipmapResources = new R.mipmap();
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