package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res {

    @SerializedName("message")
    @Expose
    String massage;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("cftoken")
    @Expose
    String cftoken;

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }
}
