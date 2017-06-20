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

    public ExerciseImages() {

    }

    public ExerciseImages(Context context) {
        this.context = context;
    }

    public void setImageMap() {
        try {
            setElementsToImageMap();
        } catch(IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void setElementsToImageMap() throws IllegalAccessException {
        final Class<R.mipmap> mipmapClass = R.mipmap.class;
        final Field[] mipmapFields = mipmapClass.getDeclaredFields();
        int increment=0,maxImages = mipmapFields.length;
        while(increment<maxImages) {
            getImageAndItemNameForMap(increment);
            increment++;
        }
    }

    private void getImageAndItemNameForMap(int imagePosition) throws IllegalAccessException {
        List<String> EXERCISE_LIST = new ExerciseTable(context).getColumn(COLUMN_EXERCISES);
        final Class<R.mipmap> mipmapClass = R.mipmap.class;
        final Field[] mipmapFields = mipmapClass.getDeclaredFields();
        String imageName = mipmapFields[imagePosition].getName().replace("_"," ");
        for(String exerciseName : EXERCISE_LIST) {
            matchImageAndItemToMap(imagePosition,imageName,exerciseName.replace("-"," "));
        }
    }

    private void matchImageAndItemToMap(int imageId,String imageName,String exName) throws IllegalAccessException {
        if(compareItemAndImageName(imageName,exName)) {
            addImagesToMap(exName,imageId);
        }
    }

    private void addImagesToMap(String mapID,int fieldLocation) throws IllegalAccessException {
        final R.mipmap mipmapResources = new R.mipmap();
        final Class<R.mipmap> mipmapClass = R.mipmap.class;
        final Field[] mipmapFields = mipmapClass.getDeclaredFields();
        String imageID = String.valueOf(mipmapFields[fieldLocation].getInt(mipmapResources));
        EXERCISE_IMAGE_MAP.put(mapID,imageID);
    }

    private boolean compareItemAndImageName(String itemName,String imageName) {
        return imageName.equalsIgnoreCase(itemName);
    }
}