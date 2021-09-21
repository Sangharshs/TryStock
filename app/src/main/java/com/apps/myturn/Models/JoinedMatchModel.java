package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinedMatchModel {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("user_id")
    @Expose
    public String user_id;

    @SerializedName("match_id")
    @Expose
    public String match_id;

    @SerializedName("match_title")
    @Expose
    public String match_title;

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("joined_users")
    @Expose
    public String joined_user;

    @SerializedName("match_name")
    @Expose
    public String match_name;

    @SerializedName("entry_fee")
    @Expose
    public String entry_fee;

    @SerializedName("prize_pool")
    @Expose
    public String prize_pool;

    @SerializedName("stock_data")
    @Expose
    public String stock_data;

    @SerializedName("category_id")
    @Expose
    public String category_id;
}
