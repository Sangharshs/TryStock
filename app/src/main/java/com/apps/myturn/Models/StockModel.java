package com.apps.myturn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockModel {
    @SerializedName("image")
    @Expose
    String companyLogo;

    @SerializedName("stock_name")
    @Expose
    public String company_name;

    @SerializedName("stock_price")
    @Expose
    public String current_stock_price;

    @SerializedName("up_down_value")
    @Expose
    public String up_down_indicator;

    public String buy_sell_value;

    public Boolean isSelected = false;

    public StockModel(String companyLogo, String company_name, String current_stock_price, String up_down_indicator, String buy_sell_value, Boolean isSelected, String id) {
        this.companyLogo = companyLogo;
        this.company_name = company_name;
        this.current_stock_price = current_stock_price;
        this.up_down_indicator = up_down_indicator;
        this.buy_sell_value = buy_sell_value;
        this.isSelected = isSelected;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    public String id;

    public String getBuy_sell_value() {
        return buy_sell_value;
    }

    public void setBuy_sell_value(String buy_sell_value) {
        this.buy_sell_value = buy_sell_value;
    }

    public StockModel(String companyLogo, String company_name, String current_stock_price, String up_down_indicator) {
        this.companyLogo = companyLogo;
        this.company_name = company_name;
        this.current_stock_price = current_stock_price;
        this.up_down_indicator = up_down_indicator;
    }

    public StockModel() {

    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCurrent_stock_price() {
        return current_stock_price;
    }

    public void setCurrent_stock_price(String current_stock_price) {
        this.current_stock_price = current_stock_price;
    }

    public String getUp_down_indicator() {
        return up_down_indicator;
    }

    public void setUp_down_indicator(String up_down_indicator) {
        this.up_down_indicator = up_down_indicator;
    }

}
