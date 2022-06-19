package com.pmdm.university.entidad;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UniversityDetail {

    public String name;
    @SerializedName("web_pages")
    private String url;
    private String imageUrl;
    private String description;

    public String getName(){return name;}
    public void setName(String name) {this.name = name;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
