package com.mohamed.ezz.nearestmasjids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyResult {

    @SerializedName("data")
    @Expose
    private ArrayList<Masjid> data;

    public ArrayList<Masjid> getMasjidList() {
        return data;
    }

    public void setMasjidList(ArrayList<Masjid> masjidList) {
        this.data = masjidList;
    }
}
