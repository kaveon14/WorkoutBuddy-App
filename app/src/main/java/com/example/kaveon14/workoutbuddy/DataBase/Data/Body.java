package com.example.kaveon14.workoutbuddy.DataBase.Data;

import java.util.Date;

public class Body {

    private String unitOfMeasurement;
    private String weightUofMeasurement;
    private String weight;
    private String chestSize;
    private String backSize;
    private String armSize;
    private String forearmSize;
    private String waistSize;
    private String quadSize;
    private String calfSize;
    private String dateForDisplaying;
    private Date dateForSorting;

    public Body(String date,String weight,String chest,String back,String arm,String forearm,
                String waist,String quad, String calf) {
        System.out.println("date: "+date);
        dateForSorting = new Date(date);
        this.weight = weight;
        dateForDisplaying = date;
        chestSize = chest;
        backSize = back;
        armSize = arm;
        forearmSize = forearm;
        waistSize = waist;
        quadSize = quad;
        calfSize = calf;
    }

    public Body(String unitOfMeasurement) {
        if(unitOfMeasurement.equalsIgnoreCase("cm")||unitOfMeasurement.equalsIgnoreCase("inches")) {
            this.unitOfMeasurement = unitOfMeasurement;
        } else {
            throw new Error(("Unit of measurement Error!!"));
        }
    }

    public void setDateLogged(Date dateForSorting,String dateForDisplaying) {//reformat so user never
        this.dateForSorting = dateForSorting;//uses dateForSorting but it is still returned
        this.dateForDisplaying = dateForDisplaying;
    }

    public Date getDate() {
        return dateForSorting;
    }

    public String getStringDate() {
        return dateForDisplaying;
    }

    public String getWeight() {
        return weight;
    }

    public String getChestSize() {
        return chestSize;
    }

    public String getBackSize() {
        return backSize;
    }

    public String getArmSize() {
        return armSize;
    }

    public String getForearmSize() {
        return forearmSize;
    }

    public String getWaistSize() {
        return waistSize;
    }

    public String getQuadSize() {
        return quadSize;
    }

    public String getCalfSize() {
        return calfSize;
    }
}
