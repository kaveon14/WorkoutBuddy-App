package com.example.kaveon14.workoutbuddy.DataBase.Data;

import java.util.Date;

public class Body {

    private String weight;
    private String chestSize;
    private String backSize;
    private String armSize;
    private String forearmSize;
    private String waistSize;
    private String quadSize;
    private String calfSize;
    private String dateForDisplaying;
    private long rowId;

    public Body setDate(String date) {
        dateForDisplaying = date;
        return this;
    }

    public Body setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public Body setChestSize(String chestSize) {
        this.chestSize = chestSize;
        return this;
    }

    public Body setBackSize(String backSize) {
        this.backSize = backSize;
        return this;
    }

    public Body setArmSize(String armSize) {
        this.armSize = armSize;
        return this;
    }

    public Body setForearmSize(String forearmSize) {
        this.forearmSize = forearmSize;
        return this;
    }

    public Body setWaistSize(String waistSize) {
        this.waistSize = waistSize;
        return this;
    }

    public Body setQuadSize(String quadSize) {
        this.quadSize = quadSize;
        return this;
    }

    public Body setCalfSize(String calfSize) {
        this.calfSize = calfSize;
        return this;
    }

    public Body setRowID(long rowId) {
        this.rowId = rowId;
        return this;
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

    public long getRowId() {
        return rowId;
    }
}
