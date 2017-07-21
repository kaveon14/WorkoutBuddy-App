package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;

import java.util.Calendar;

public class CalendarManager {

        protected Context context;

    public CalendarManager(Context context) {
        this.context = context;

    }

    public static void createCalendarOnStart() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Calendars.ACCOUNT_NAME, "calendar@workout.com");
        contentValues.put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.workout");
        contentValues.put(CalendarContract.Calendars.NAME, "Workout Calendar");
        contentValues.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Workout Calendar");
        contentValues.put(CalendarContract.Calendars.CALENDAR_COLOR, "232323");
        contentValues.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.CAL_ACCESS_OWNER);
        contentValues.put(CalendarContract.Calendars.OWNER_ACCOUNT, "androidUser@workout.com");
        contentValues.put(CalendarContract.Calendars.ALLOWED_REMINDERS, "METHOD_ALERT");
        contentValues.put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "TYPE_OPTIONAL," +
                " TYPE_REQUIRED, TYPE_RESOURCE");
        contentValues.put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "AVAILABILITY_BUSY" +
                ", AVAILABILITY_FREE, AVAILABILITY_TENTATIVE");

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        uri = uri.buildUpon().appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "calendar@workout.com")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.workout").build();
        MainActivity.activity.getContentResolver().insert(uri, contentValues);
    }









}
