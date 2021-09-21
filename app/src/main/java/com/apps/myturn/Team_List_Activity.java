package com.apps.myturn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myturn.Adapters.StockAdapter;
import com.apps.myturn.Fragments.BalanceSummeryFragment;
import com.apps.myturn.Models.JoinMatch;
import com.apps.myturn.Models.MatchDetailsModel;
import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.StockModel;
import com.apps.myturn.Models.UserModel;
import com.bumptech.glide.Glide;
import com.cashfree.pg.CFPaymentService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;
import static com.apps.myturn.Api.IMG;

public class Team_List_Activity extends AppCompatActivity implements StockListListner{

    RecyclerView recyclerView;
    StockAdapter stockAdapter;
    List<StockModel> selectedStocksList;
    String time_remaining,match_title,match_subtitle,prize_pool,entry_fee,joined_users,total_users,match_date;
    View view;
    RoundedHorizontalProgressBar horizontalProgressBar;
    TextView match_title_TEXTView,date,prize_pool_textview,entry_fee_TEXTVIEW,joined_users_TEXTVIEW,total_users_TEXTVIEW;
    Button detailsButton,joinMatchButton,countinueBTN;
    String buy_sell_Value;
    FirebaseAuth mAuth;
    String user_id,user_name,user_email,match_id;
    ImageButton goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        mAuth = FirebaseAuth.getInstance();
        goback = findViewById(R.id.goBackButton);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        user_email = preferences.getString("user_email","");
        user_id = preferences.getString("user_id","");
        user_name = preferences.getString("user_name","");

        recyclerView = findViewById(R.id.selectedStocksRecyclerView);
        view = findViewById(R.id.match_item);
        countinueBTN = findViewById(R.id.buttonContinue);

        detailsButton = view.findViewById(R.id.detailsButton);
        joinMatchButton = view.findViewById(R.id.joinMatchButton);
        horizontalProgressBar = view.findViewById(R.id.progress_bar_1_horizontal);
        match_title_TEXTView = view.findViewById(R.id.match_name_TextView);
        date = view.findViewById(R.id.match_time_Textview);
        prize_pool_textview = view.findViewById(R.id.prizePool);
        entry_fee_TEXTVIEW = view.findViewById(R.id.entryFee);
        joined_users_TEXTVIEW = view.findViewById(R.id.joined_user);
        total_users_TEXTVIEW = view.findViewById(R.id.total_user);

        detailsButton = view.findViewById(R.id.detailsButton);
        joinMatchButton = view.findViewById(R.id.joinMatchButton);

        detailsButton.setVisibility(View.GONE);
        joinMatchButton.setVisibility(View.GONE);

        match_title = getIntent().getStringExtra("match_title");
        match_subtitle = getIntent().getStringExtra("match_subtitle");
        prize_pool = getIntent().getStringExtra("prize_pool");
        entry_fee  = getIntent().getStringExtra("entry_fee");
        joined_users = getIntent().getStringExtra("joined_users");
        total_users = getIntent().getStringExtra("total_users");
        match_date = getIntent().getStringExtra("time_remaining");
        match_id = getIntent().getStringExtra("id");

        match_title_TEXTView.setText(match_title);
        prize_pool_textview.setText(prize_pool);
        date.setText(match_date);
        entry_fee_TEXTVIEW.setText(entry_fee);
        joined_users_TEXTVIEW.setText(joined_users);
        total_users_TEXTVIEW.setText(total_users);
        horizontalProgressBar.setProgress(Integer.parseInt(joined_users));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("courses", null);
        Type type = new TypeToken<ArrayList<StockModel>>() {}.getType();

        selectedStocksList = gson.fromJson(json, type);
        if (selectedStocksList == null) {
            selectedStocksList = new ArrayList<>();
        }
        simpleAdapter simpleAdapter = new simpleAdapter(selectedStocksList);
        recyclerView.setAdapter(simpleAdapter);
        countinueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                // convert your list to json
                String jsonCartList = gson.toJson(selectedStocksList);
                Bundle intent = new Bundle();
                Log.e("LIST1",jsonCartList.toString());
                Log.d("DATA1",match_title+match_subtitle+prize_pool+total_users+joined_users);
                intent.putString("match_title",match_title);
                intent.putString("match_subtitle",match_subtitle);
                intent.putString("prize_pool",prize_pool);
                intent.putString("time_remaining",match_date);
                intent.putString("total_users",total_users);
                intent.putString("joined_users",joined_users);
                intent.putString("entry_fee",entry_fee);
                intent.putString("match_id",match_id);
                intent.putString("SEL_STOCK_LIST",jsonCartList);

                BalanceSummeryFragment balanceSummeryFragment = new BalanceSummeryFragment();
                balanceSummeryFragment.setArguments(intent);
                balanceSummeryFragment.show(getSupportFragmentManager(),balanceSummeryFragment.getTag());
            }
        });

    }

    @Override
    public void onStockShowAction(Boolean isSelected) {

    }

    @Override
    public void buysellValue(String v) {
     buy_sell_Value = v;
        Toast.makeText(this, v, Toast.LENGTH_SHORT).show();
    }

    class simpleAdapter extends RecyclerView.Adapter<simpleAdapter.viewho>{
        List<StockModel> stockModelList;

        public simpleAdapter(List<StockModel> stockModelList) {
            this.stockModelList = stockModelList;
        }

        @NonNull
        @Override
        public viewho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
            return new viewho(v);
        }

        @Override
        public void onBindViewHolder(@NonNull viewho holder, int position) {
            holder.bind(stockModelList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Team_List_Activity.this, stockModelList.get(position).getBuy_sell_value().toString(), Toast.LENGTH_SHORT).show();
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
            String id;
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
                id = s.id;

                Glide.with(itemView.getContext()).load(IMG + s.getCompanyLogo()).into(roundedImageView);

                sellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s.buy_sell_value = buy_sell_Value = "Sell";
                        Toast.makeText(Team_List_Activity.this, "Value Change", Toast.LENGTH_SHORT).show();
                    }
                });

                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s.buy_sell_value = buy_sell_Value = "Buy";
                        Toast.makeText(Team_List_Activity.this, "Value Change", Toast.LENGTH_SHORT).show();
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

    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d("CFLL1", "ReqCode : " + CFPaymentService.REQ_CODE);
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)

                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d("signature", key + " : " + bundle.getString(key));
                        Log.d("signature1", bundle.getString(key));
                        String string = bundle.getString(key);
                        String keyword = "SUCCESS";

                        Boolean found = Arrays.asList(string.split(" ")).contains(keyword);
                        if(found){
                            store_match_details();
                        }
                        String k1 = "CANCELLED";
                        Boolean f1 = Arrays.asList(string.split(" ")).contains(k1);
                        if(f1){
                          finish();
                            Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
    }

    public void store_match_details() {
        String number = mAuth.getCurrentUser().getPhoneNumber();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Gson gson = new Gson();
        // convert your list to json
        String jsonCartList = gson.toJson(selectedStocksList);
        Call<Res> call = apiInterface.store_joined_match(user_name,number,match_title,match_id,jsonCartList,user_id);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {

                 if(response.isSuccessful()){

                     Toast.makeText(Team_List_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                     new SweetAlertDialog(Team_List_Activity.this)
                             .setTitleText("You Joined Match Successfully.")
                             .show();
                     startActivity(new Intent(Team_List_Activity.this,MainActivity.class));

                 }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Toast.makeText(Team_List_Activity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update_wallet_amount(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Call<Res> call = apiInterface.update_wallet_money(user_id,Integer.parseInt(entry_fee_TEXTVIEW.getText().toString()),1);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
              if(response.isSuccessful()){
              }

            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {

            }
        });

    }





}