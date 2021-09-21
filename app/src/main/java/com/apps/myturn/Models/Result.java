package com.apps.myturn.Models;

public class Result {
  public String points;
  public String rank;
  public String finished_match_id;
  public String user_id;
  public String stock_data;
  public String user_name;

   public Result(String points, String rank, String finished_match_id, String user_id, String stock_data,String user_name) {
      this.points = points;
      this.rank = rank;
      this.finished_match_id = finished_match_id;
      this.user_id = user_id;
      this.stock_data = stock_data;
      this.user_name = user_name;
   }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPoints() {
      return points;
   }

   public void setPoints(String points) {
      this.points = points;
   }

   public String getRank() {
      return rank;
   }

   public void setRank(String rank) {
      this.rank = rank;
   }

   public String getFinished_match_id() {
      return finished_match_id;
   }

   public void setFinished_match_id(String finished_match_id) {
      this.finished_match_id = finished_match_id;
   }

   public String getUser_id() {
      return user_id;
   }

   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }

   public String getStock_data() {
      return stock_data;
   }

   public void setStock_data(String stock_data) {
      this.stock_data = stock_data;
   }
}
