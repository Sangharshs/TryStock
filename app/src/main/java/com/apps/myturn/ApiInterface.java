package com.apps.myturn;

import com.apps.myturn.Models.GameModel;
import com.apps.myturn.Models.JoinMatch;
import com.apps.myturn.Models.JoinedMatchModel;
import com.apps.myturn.Models.MatchDetailsModel;
import com.apps.myturn.Models.MatchModel;
import com.apps.myturn.Models.MyTeam;
import com.apps.myturn.Models.NewsModel;
import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.Result;
import com.apps.myturn.Models.SliderItem;
import com.apps.myturn.Models.StockModel;
import com.apps.myturn.Models.UserModel;
import com.apps.myturn.Models.VerificationModel;
import com.google.android.gms.auth.api.phone.SmsCodeAutofillClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("cashfree.php")
    Call<Res> getToken (@Field("orderId") String orderId,
                        @Field("orderAmount") int orderAmount);

    @GET("categories")
    Call<List<GameModel>> gamesCategories();

    @GET("gn")
    Call<List<NewsModel>> getNews();

    @GET("subcategories/{id}")
    Call<List<MatchModel>> match_model_list(@Path("id")String id);

    @GET("stocklist/{id}")
    Call<ArrayList<StockModel>> stocks_list(@Path("id")String id);

    @GET("sliders")
    Call<ArrayList<SliderItem>> sliders_list();

    @GET("banner")
    Call<ArrayList<SliderItem>> banner();

    @GET("matchdetails/{id}")
    Call<List<MatchDetailsModel>> match_details_list(@Path("id")String id);

    @FormUrlEncoded
    @POST("store_user")
    Call<UserModel> register_user(@Field("fullname") String fullname,
                                  @Field("email") String email,
                                  @Field("phone")String phone,
                                  @Field("joined_refer_code")String referCode);

    @GET("user/{phone}")
    Call<List<UserModel>> get_user(@Path("phone")String number);

    @GET("check_user_exist/{phone}")
    Call<List<Res>> check_user_exist(@Path("phone") String number);

    @FormUrlEncoded
    @POST("store_joined_match")
    Call<Res> store_joined_match(@Field("user_name")String user_name,
                                       @Field("user_number") String user_number,
                                       @Field("match_name")String match_name,
                                       @Field("match_id") String match_id,
                                       @Field("stock_data")String stocks_data,
                                       @Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("store_free_joined_match")
    Call<Res> store_free_joined_matches(@Field("user_name")String user_name,
                                 @Field("user_number") String user_number,
                                 @Field("match_name")String match_name,
                                 @Field("match_id") String match_id,
                                 @Field("stock_data")String stocks_data,
                                 @Field("user_id")String user_id);

    @GET("upcoming/{user_id}")
    Call<List<JoinedMatchModel>> get_joined_matches(@Path("user_id")String user_id);

    @GET("live/{user_id}")
    Call<List<JoinedMatchModel>> get_lived_matches(@Path("user_id")String user_id);

    @GET("finished/{user_id}")
    Call<List<JoinedMatchModel>> get_completed_matches(@Path("user_id")String user_id);

    @FormUrlEncoded
    @POST("updateteam/{match_id}/{user_id}")
    Call<JoinedMatchModel> update_team(@Path("match_id") String match_id,
                                       @Path("user_id")  String user_id,
                                       @Field("stock_data") String stock_data);

    @FormUrlEncoded
    @POST("update_amount/{id}")
    Call<Res> update_Added_money(@Path("id")String id, @Field("wallet_amount") int wallet_amount);

    @FormUrlEncoded
    @POST("update_bonus")
    Call<UserModel> update_bonus(@Field("bonus") int bonus);

    @FormUrlEncoded
    @POST("use_wallet_money/{id}")
    Call<Res> use_wallet_money(@Path("id")String id, @Field("entry_amount") int wallet_amount);

    @FormUrlEncoded
    @POST("update_winning_amount/{id}")
    Call<UserModel> update_winning_amount(@Field("winning_amount")int winning_amount);

    @FormUrlEncoded
    @POST("update_winning_amount/{id}")
    Call<UserModel> update_user_account(@Field("winning_amount")int winning_amount);

    @FormUrlEncoded
    @POST("update_wallet_money/{id}")
    Call<Res> update_wallet_money(@Path("id")String id,@Field("entry_fee")int entry_fee,@Field("bonus") int bonus);

    @POST("get_verification_details/{user_id}")
    Call<List<VerificationModel>> get_verification_details(@Path("user_id")String id);

    @FormUrlEncoded
    @POST("verify_details/{id}")
    Call<Res> update_verification(@Path("id")String id, @Field("pan_name")String pan_name,
                                                @Field("pan_number")String pan_number,
                                                @Field("account_holder_name")String account_holder_name,
                                                @Field("account_number")String account_number,
                                                @Field("ifsc_code")String ifsc_code);

    @POST("refer_count/{refer_code}")
    Call<List<UserModel>> refer_list(@Path("refer_code")String refer_code);

    @POST("get_result/{match_id}")
    Call<List<Result>> get_result(@Path("match_id")String match_id);

    @POST("myteam/{match_id}/{user_id}")
    Call<List<MyTeam>> getTeamList(@Path("match_id")String match_id,@Path("user_id")String user_id);

}

