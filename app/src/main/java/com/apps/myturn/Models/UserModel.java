package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("fullname")
    @Expose
    String fullname;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("phone")
    @Expose
    String phone;

    @SerializedName("refer_code")
    @Expose
    String refer_code;

    @SerializedName("wallet_amount")
    @Expose
    int wallet_amount;

    @SerializedName("winning_amount")
    @Expose
    int winning_amount;

    @SerializedName("bonus")
    @Expose
    int bonus;

    @SerializedName("id")
    @Expose
    String id;

    public UserModel(String fullname, String email, String phone, String refer_code, int wallet_amount, int winning_amount, int bonus, String id) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.refer_code = refer_code;
        this.wallet_amount = wallet_amount;
        this.winning_amount = winning_amount;
        this.bonus = bonus;
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }

    public int getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(int wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public int getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(int winning_amount) {
        this.winning_amount = winning_amount;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
