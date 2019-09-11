package com.mohamed.ezz.nearestmasjids.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Masjid {

    @Nullable
    @PrimaryKey()
    private Integer id;
    @ColumnInfo(name = "nameEnglish")
    private String name_en;
    @ColumnInfo(name = "nameArabic")
    private String name_ar;
    private String address;
    private String latitude;
    private String longitude;
    @ColumnInfo(name = "imagesUrl")
    private String images_url;
    private String distance;


    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImages_url() {
        return images_url;
    }

    public void setImages_url(String images_url) {
        this.images_url = images_url;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String fetchDistance() {
        return getDistance().substring(0,3) + "km";
    }

    public String fetchName() {
        if (getName_ar() != null && !getName_ar().equals(""))
            return getName_ar();
        else return getName_en();
    }
}
