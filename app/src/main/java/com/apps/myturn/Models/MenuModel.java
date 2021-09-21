package com.apps.myturn.Models;

public class MenuModel {
    int image;
    String btnName;
    String url;

    public MenuModel(int image, String btnName, String url) {
        this.image = image;
        this.btnName = btnName;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
