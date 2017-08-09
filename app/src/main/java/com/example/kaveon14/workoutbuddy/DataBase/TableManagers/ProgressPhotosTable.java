package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.Context;

import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.TABLE_NAME;

public class ProgressPhotosTable extends TableManager {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ProgressPhotosTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        searchTable(TABLE_NAME);
        setSearchableColumns(new String[]{COLUMN_DATE});
    }




}
