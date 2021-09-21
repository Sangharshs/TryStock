package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameModel {
    @SerializedName("category_name")
    @Expose
    String game_title;
    @SerializedName("subtitle")
    @Expose
    String game_subtitle;
    @SerializedName("time")
    @Expose
    String game_date_time;
    @SerializedName("type")
    @Expose
    String game_tag;
    @SerializedName("category_id")
    @Expose
    String category_id;

    @SerializedName("total_users")
    @Expose
    String total_users;

    public String getTotal_users() {
        return total_users;
    }

    public void setTotal_users(String total_users) {
        this.total_users = total_users;
    }

    public String getGame_subtitle() {
        return game_subtitle;
    }

    public GameModel(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public GameModel(String game_subtitle, String game_title, String game_date_time, String game_tag) {
        this.game_subtitle = game_subtitle;
        this.game_title = game_title;
        this.game_date_time = game_date_time;
        this.game_tag = game_tag;

    }





    public String getGame_title() {
        return game_title;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }

    public String getGame_date_time() {
        return game_date_time;
    }

    public void setGame_date_time(String game_date_time) {
        this.game_date_time = game_date_time;
    }

    public String getGame_tag() {
        return game_tag;
    }

    public void setGame_tag(String game_tag) {
        this.game_tag = game_tag;
    }
}
