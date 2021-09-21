package com.apps.myturn.Models;

import retrofit2.http.Field;
import retrofit2.http.Path;

public class VerificationModel {
    String id;
    String pan_name;
    String pan_number;
    String account_holder_name;
    String account_number;
    String ifsc_code;

    public VerificationModel(String id, String pan_name, String pan_number, String account_holder_name, String account_number, String ifsc_code) {
        this.id = id;
        this.pan_name = pan_name;
        this.pan_number = pan_number;
        this.account_holder_name = account_holder_name;
        this.account_number = account_number;
        this.ifsc_code = ifsc_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPan_name() {
        return pan_name;
    }

    public void setPan_name(String pan_name) {
        this.pan_name = pan_name;
    }

    public String getPan_number() {
        return pan_number;
    }

    public void setPan_number(String pan_number) {
        this.pan_number = pan_number;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }
}
