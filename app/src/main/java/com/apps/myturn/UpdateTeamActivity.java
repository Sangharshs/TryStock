package com.apps.myturn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myturn.Adapters.DummyStockAdapter;
import com.apps.myturn.Adapters.StockAdapter;
import com.apps.myturn.Fragments.BalanceSummeryFragment;
import com.apps.myturn.Models.JoinedMatchModel;
import com.apps.myturn.Models.StockModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;
import static com.apps.myturn.Api.IMG;

public class UpdateTeamActivity extends AppCompatActivity implements StockListListner{

    RecyclerView recyclerView1,recyclerView2;
    SweetAlertDialog pDialog;
    StockListListner stockListListner;
    StockAdapter stockAdapter;
    StockModel stockModel;
    int stock_Limit;
    ArrayList<StockModel> list1;
    ArrayList<StockModel> list2;
    String buy_sell_Value;
    List<StockModel> selectedStocksList;
    ArrayList<StockModel> stockModelList;
    Button updateButton;
    String user_id,match_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);

        stockListListner = this;

        recyclerView1 = findViewById(R.id.update_stocks_recyclerview1);
        recyclerView2 = findViewById(R.id.update_stocks_recyclerview2);
        updateButton  = findViewById(R.id.buttonUpdate);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setClickable(false);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager1);

        String jsonData = getIntent().getStringExtra("STOCK_DATA");

        Gson gson = new Gson();

        Type listUserType = new TypeToken<List<StockModel>>(){}.getType();

        list1 = gson.fromJson(jsonData, listUserType);

        for (int i = 0; i < list1.size(); i++) {
            DummyStockAdapter adapter = new DummyStockAdapter(list1,stockListListner,getApplicationContext());

            recyclerView1.setClickable(false);
            recyclerView1.setAdapter(adapter);
        }

        stock_Limit = list1.size();

        String cid = getIntent().getStringExtra("cid");

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

//                if(response.isSuccessful()) {
                stockModel = new StockModel();
                stockModelList = response.body();

                stockAdapter = new StockAdapter(stockModelList, stockListListner, getApplicationContext());
                recyclerView2.setAdapter(stockAdapter);
                pDialog.dismiss();

                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("courses", null);

                Type type = new TypeToken<ArrayList<StockModel>>() {
                }.getType();

                selectedStocksList = gson.fromJson(json, type);
                if (selectedStocksList == null) {
                    selectedStocksList = new ArrayList<>();
                }



                   updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (stockAdapter.getSelectedStockModelList().size() == stock_Limit) {

                            ArrayList<StockModel> selectedStocksList = stockAdapter.getSelectedStockModelList();
                            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            Gson gson = new Gson();
                            String json = gson.toJson(selectedStocksList);
                            editor.putString("courses", json);
                            editor.apply();
                            upadte_team(json);
                            StringBuilder stockShowNames = new StringBuilder();
                            for (int i = 0; i < selectedStocksList.size(); i++) {
                                if (i == 0) {
                                    Log.e("LIST_1",String.valueOf(selectedStocksList));
                                    String company_name = selectedStocksList.get(i).company_name;
                                } else {

                                }
                            }
                        } else if (stockAdapter.getSelectedStockModelList().size() < stock_Limit) {
                            Toast.makeText(UpdateTeamActivity.this, "Select " +String.valueOf(stock_Limit )+ " Stocks.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateTeamActivity.this, "You can select just " +String.valueOf(stock_Limit)+ " stocks", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<StockModel>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(UpdateTeamActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void upadte_team(String json) {
        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        user_id = preferences.getString("user_id","");
        match_id = getIntent().getStringExtra("id");


        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<JoinedMatchModel> call = api.update_team(match_id,user_id,json);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        call.enqueue(new Callback<JoinedMatchModel>() {
            @Override
            public void onResponse(Call<JoinedMatchModel> call, Response<JoinedMatchModel> response) {

                if(response.isSuccessful()){
                    pDialog.dismissWithAnimation();
                    Toast.makeText(UpdateTeamActivity.this, "Team Update Success.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateTeamActivity.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinedMatchModel> call, Throwable t) {
                Toast.makeText(UpdateTeamActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismissWithAnimation();
            }
        });



    }

    @Override
    public void onStockShowAction(Boolean isSelected) {

    }

    @Override
    public void buysellValue(String v) {
        buy_sell_Value = v;
    }

    public class simpleAdapter extends RecyclerView.Adapter<simpleAdapter.viewho>{
        List<StockModel> stockModelList;

        public simpleAdapter(List<StockModel> stockModelList) {
            this.stockModelList = stockModelList;
        }

        @NonNull
        @Override
        public viewho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
            return new simpleAdapter.viewho(v);
        }

        @Override
        public void onBindViewHolder(@NonNull viewho holder, int position) {
            holder.bind(stockModelList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UpdateTeamActivity.this, stockModelList.get(position).getBuy_sell_value().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }



        @Override
        public int getItemCount() {
            return stockModelList.size();
        }

        public class viewho extends RecyclerView.ViewHolder {
            RadioGroup radioGroup;
            RadioButton buyButton, sellButton;
            CardView cardViewBackground;
            RoundedImageView roundedImageView;
            TextView company_Name, stock_price, current_value;
            ImageView up_arrow, down_arrow;
            public viewho(@NonNull View itemView) {
                super(itemView);
                radioGroup = itemView.findViewById(R.id.radioGroup);
                buyButton = itemView.findViewById(R.id.buyButton);
                sellButton = itemView.findViewById(R.id.sellButton);
                cardViewBackground = itemView.findViewById(R.id.cardBackGround);
                roundedImageView = itemView.findViewById(R.id.roundedImageView);
                company_Name = itemView.findViewById(R.id.company_name);
                stock_price = itemView.findViewById(R.id.stock_price);
                current_value = itemView.findViewById(R.id.up_down_value);
                up_arrow = itemView.findViewById(R.id.up_arrow_imageView);
                down_arrow = itemView.findViewById(R.id.down_arrow_imageView);
            }

            public void bind(StockModel s) {
                company_Name.setText(s.company_name);
                stock_price.setText(s.current_stock_price);
                current_value.setText(s.up_down_indicator);
                buy_sell_Value = s.buy_sell_value;

                Glide.with(itemView.getContext()).load(IMG + s.getCompanyLogo()).into(roundedImageView);

                sellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s.buy_sell_value = buy_sell_Value = "Sell";
                        Toast.makeText(UpdateTeamActivity.this, "Value Change", Toast.LENGTH_SHORT).show();
                    }
                });

                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s.buy_sell_value = buy_sell_Value = "Buy";
                        Toast.makeText(UpdateTeamActivity.this, "Value Change", Toast.LENGTH_SHORT).show();
                    }
                });
                if(buy_sell_Value!=null) {
                    if (buy_sell_Value.equals("Buy")) {
                        buyButton.setChecked(true);
                    } else if (buy_sell_Value.equals("Sell")) {
                        sellButton.setChecked(true);
                    }
                }


            }
        }
    }
}