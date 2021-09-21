package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.myturn.Adapters.StockAdapter;
import com.apps.myturn.Models.StockModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class JoinMatchActivity extends AppCompatActivity implements StockListListner {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    ArrayList<StockModel> stockModelList;
    StockAdapter stockAdapter;
    Button continueBTN;
    String cid,match_name,time_remaining,match_title,match_subtitle,prize_pool,entry_fee,joined_users,total_users,id;
    StockListListner stockListListner;
    SweetAlertDialog pDialog;
    StockModel stockModel;
    int stock_Limit;
    ImageButton goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_match);

        goback = findViewById(R.id.goBackButton);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        continueBTN = findViewById(R.id.buttonContinue);

        recyclerView = findViewById(R.id.stockListRecyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        stockListListner = this;
        stockModelList = new ArrayList<>();

        match_name = getIntent().getStringExtra("match_name");
        time_remaining = getIntent().getStringExtra("time_remaining");
        match_title = getIntent().getStringExtra("match_title");
        match_subtitle = getIntent().getStringExtra("match_subtitle");
        prize_pool = getIntent().getStringExtra("prize_pool");
        entry_fee = getIntent().getStringExtra("entry_fee");
        joined_users = getIntent().getStringExtra("joined_users");
        stock_Limit = getIntent().getIntExtra("stock_limit",0);
        total_users = getIntent().getStringExtra("total_users");
        id = getIntent().getStringExtra("id");
        cid = getIntent().getStringExtra("cid");

        String jsonData = getIntent().getStringExtra("STOCK_DATA");
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<ArrayList<StockModel>> call = api.stocks_list(cid);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<ArrayList<StockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StockModel>> call, Response<ArrayList<StockModel>> response) {

                stockModel = new StockModel();

                stockModelList = response.body();

                StockAdapter stockAdapter = new StockAdapter(stockModelList,stockListListner,getApplicationContext());
                recyclerView.setAdapter(stockAdapter);
                pDialog.dismiss();

                continueBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (stockAdapter.getSelectedStockModelList().size() == stock_Limit) {

                            ArrayList<StockModel> selectedStocksList = stockAdapter.getSelectedStockModelList();

                            Intent intent = new Intent(JoinMatchActivity.this,Team_List_Activity.class);
                            intent.putExtra("match_name",match_name);
                            intent.putExtra("match_title",match_title);
                            intent.putExtra("match_subtitle",match_subtitle);
                            intent.putExtra("prize_pool",prize_pool);
                            intent.putExtra("time_remaining",time_remaining);
                            intent.putExtra("joined_users",joined_users);
                            intent.putExtra("entry_fee",entry_fee);
                            intent.putExtra("total_users",total_users);
                            intent.putExtra("id",id);
                            intent.putExtra("cid",cid);

                            startActivity(intent);

                            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            Gson gson = new Gson();
                            String json = gson.toJson(selectedStocksList);
                            editor.putString("courses", json);
                            editor.apply();


                            StringBuilder stockShowNames = new StringBuilder();
                            for (int i = 0; i < selectedStocksList.size(); i++) {
                                if (i == 0) {
                                    Log.e("LIST_1",String.valueOf(selectedStocksList));
                                    String company_name = selectedStocksList.get(i).company_name;
                                } else {

                                }
                            }
                        }
                        else if (stockAdapter.getSelectedStockModelList().size() < stock_Limit) {
                            Toast.makeText(JoinMatchActivity.this, "Select " +String.valueOf(stock_Limit )+ " Stocks.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(JoinMatchActivity.this, "You can select just " +String.valueOf(stock_Limit )+ " stocks", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call<ArrayList<StockModel>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(JoinMatchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStockShowAction(Boolean isSelected) {
        if (isSelected) {
            continueBTN.setVisibility(View.VISIBLE);
        } else {
            continueBTN.setVisibility(View.GONE);
        }
    }

    @Override
    public void buysellValue(String v) {

    }
}