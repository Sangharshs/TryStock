package com.apps.myturn;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit retrofit = null;
    public static final String BASE_URL= "https://thecakeswala.in/myturn/Webservices/";
    public static final String IMG = "https://thecakeswala.in/myturn/upload/category/";
    public static final String IMG1 = "https://thecakeswala.in/myturn/upload/";

    public static final String CASH_FREE_APP_ID= "909487e6cfc12f9ffdb1dca5184909";
    public static final String CURRENCY = "INR";
    public static final String PAYMENT_MODE = "TEST";

    public static ApiInterface getClient() {

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiInterface api = retrofit.create(ApiInterface.class);
        return api; // return the APIInterface object
    }

}
