package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchDetailsModel {

    @SerializedName("rank_name")
    @Expose
    String rank_name;
    @SerializedName("winning_amount")
    @Expose
    String wining_amount;

    public MatchDetailsModel(String rank_name, String wining_amount) {
        this.rank_name = rank_name;
        this.wining_amount = wining_amount;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public String getWining_amount() {
        return wining_amount;
    }

    public void setWining_amount(String wining_amount) {
        this.wining_amount = wining_amount;
    }
}
