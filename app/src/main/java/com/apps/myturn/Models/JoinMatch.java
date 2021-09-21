package com.apps.myturn.Models;

public class JoinMatch {
    String matchId;
    String matchName;
    String stocks;
    String stocks_buy_sell_value;
    String users_phone_number;
    String user_name;

    public JoinMatch(String matchId, String matchName, String stocks, String stocks_buy_sell_value, String users_phone_number, String user_name) {
        this.matchId = matchId;
        this.matchName = matchName;
        this.stocks = stocks;
        this.stocks_buy_sell_value = stocks_buy_sell_value;
        this.users_phone_number = users_phone_number;
        this.user_name = user_name;
    }


    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getStocks_buy_sell_value() {
        return stocks_buy_sell_value;
    }

    public void setStocks_buy_sell_value(String stocks_buy_sell_value) {
        this.stocks_buy_sell_value = stocks_buy_sell_value;
    }

    public String getUsers_phone_number() {
        return users_phone_number;
    }

    public void setUsers_phone_number(String users_phone_number) {
        this.users_phone_number = users_phone_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
