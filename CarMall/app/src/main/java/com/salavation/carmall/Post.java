package com.salavation.carmall;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post implements Serializable {

    public static final String POST_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    public static final String DATA_INTENT = "Data_To_Details";

    @PrimaryKey(autoGenerate = true)
    int postId;

    @ColumnInfo
    String title;

    @ColumnInfo
    String price;

    @Ignore
    List<Integer> imgUris = new ArrayList<>();

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @ColumnInfo
    String images;

    @ColumnInfo
    String model;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Integer> getImgUris() {
        return imgUris;
    }

    public void setImgUris(List<Integer> imgUris) {
        this.imgUris = imgUris;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ColumnInfo
    String make;

    @ColumnInfo
    String location;

    @ColumnInfo
    String content;

}
