package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchModel {

    @SerializedName("time")
    @Expose
    String time;

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("match_title")
    @Expose
    String match_title;

    @SerializedName("match_subtitle")
    @Expose
    String match_subtitle;

    @SerializedName("prize_pool")
    @Expose
    String prize_pool;

    @SerializedName("entry_fee")
    @Expose
    String entry_fee;

    @SerializedName("joined_users")
    @Expose
    String joined_users;

    @SerializedName("total_users")
    @Expose
    String total_users;

    @SerializedName("id")
    @Expose
    String id;

    public MatchModel(String time, String date, String match_title, String match_subtitle, String prize_pool, String entry_fee, String joined_users, String total_users, String id) {
        this.time = time;
        this.date = date;
        this.match_title = match_title;
        this.match_subtitle = match_subtitle;
        this.prize_pool = prize_pool;
        this.entry_fee = entry_fee;
        this.joined_users = joined_users;
        this.total_users = total_users;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    public String getMatch_subtitle() {
        return match_subtitle;
    }

    public void setMatch_subtitle(String match_subtitle) {
        this.match_subtitle = match_subtitle;
    }

    public String getPrize_pool() {
        return prize_pool;
    }

    public void setPrize_pool(String prize_pool) {
        this.prize_pool = prize_pool;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getJoined_users() {
        return joined_users;
    }

    public void setJoined_users(String joined_users) {
        this.joined_users = joined_users;
    }

    public String getTotal_users() {
        return total_users;
    }

    public void setTotal_users(String total_users) {
        this.total_users = total_users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
