package com.apps.myturn.Models;

public class NewsModel {
    String heading;
    String image;
    String url;

    public NewsModel(String heading, String image, String url) {
        this.heading = heading;
        this.image = image;
        this.url = url;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
