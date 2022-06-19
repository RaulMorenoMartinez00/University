package com.pmdm.university.entidad;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class University {

    @SerializedName("web_pages")
    private List<String> webPages = null;

    private String name;

    public List<String> getWebPages() {

        return webPages;
    }

    public void setWebPages(List<String> webPages) {

        this.webPages = webPages;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
