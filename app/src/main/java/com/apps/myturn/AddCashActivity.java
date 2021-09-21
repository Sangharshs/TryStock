package com.apps.myturn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.Result;
import com.apps.myturn.Models.UserModel;
import com.cashfree.pg.CFPaymentService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;
import static com.apps.myturn.Api.CASH_FREE_APP_ID;
import static com.apps.myturn.Api.CURRENCY;
import static com.apps.myturn.Api.PAYMENT_MODE;
import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;

public class AddCashActivity extends AppCompatActivity{

    ImageButton imageButton;
    RecyclerView recyclerView;
    List<Amount> amountList;
    TextInputEditText textInputEditText;
    int selectedAmount;
    Button submitAmount;
    SharedPreferences preferences;
    FirebaseAuth m;
    String userName,email,phone_number;
    Call<Res> call;
    String id;
    int orderAmount,wallet_amount,winning_amount,bonus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash);

        imageButton = findViewById(R.id.goBackButton);
        recyclerView = findViewById(R.id.amountRecyclerView);
        textInputEditText = findViewById(R.id.cash_amount_edit_text);
        submitAmount = findViewById(R.id.submitAmount);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        amountList = new ArrayList<>();

        amountList.add(new Amount(50));
        amountList.add(new Amount(100));
        amountList.add(new Amount(200));
        amountList.add(new Amount(300));
        amountList.add(new Amount(400));
        amountList.add(new Amount(500));
        amountList.add(new Amount(600));
        amountList.add(new Amount(700));
        amountList.add(new Amount(800));
        amountList.add(new Amount(900));
        amountList.add(new Amount(1000));

        Amount_Adapter a = new Amount_Adapter(amountList,null);
        recyclerView.setAdapter(a);


        submitAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textInputEditText.getText().toString().equals("")){
                    orderAmount = Integer.parseInt(textInputEditText.getText().toString());
                       startPayment();
                }else{
                    textInputEditText.setError("Enter Amount (Required)");
                }
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startPayment() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thecakeswala.in/") // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        String uniqueID = UUID.randomUUID().toString();

        preferences = getSharedPreferences("USER",MODE_PRIVATE);
        userName  = preferences.getString("user_name","");
        email     = preferences.getString("user_email","");
        id = preferences.getString("user_id","");
        wallet_amount = preferences.getInt("wallet_amount",0);
        bonus    = preferences.getInt("bonus",0);
        winning_amount = preferences.getInt("winning_amount",0);

        m = FirebaseAuth.getInstance();

        orderAmount = Integer.parseInt(textInputEditText.getText().toString());
        call = api.getToken(uniqueID, orderAmount);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMassage() != null) {
                        if (response.body().getMassage().equals("Token generated")) {
                            String token = response.body().getCftoken();
                            HashMap<String, String> params = new HashMap<>();
                            params.put(PARAM_APP_ID,CASH_FREE_APP_ID);
                            params.put(PARAM_ORDER_ID, uniqueID);
                            params.put(PARAM_ORDER_AMOUNT, String.valueOf(orderAmount));
                            params.put(PARAM_ORDER_CURRENCY, CURRENCY);
                            params.put(PARAM_CUSTOMER_NAME, userName);
                            params.put(PARAM_CUSTOMER_EMAIL, email);
                            params.put(PARAM_CUSTOMER_PHONE, m.getCurrentUser().getPhoneNumber());
                            params.put(CFPaymentService.PARAM_NOTIFY_URL, "https://thecakeswala.com/webhook.php");
                            CFPaymentService.getCFPaymentServiceInstance().doPayment(AddCashActivity.this, params, response.body().getCftoken(), PAYMENT_MODE, "#FFFFFF","#FF000000",true);
                            pDialog.dismissWithAnimation();
                        } else {
                            Log.e("CASGPG", response.body().toString());
                            Toast.makeText(getApplicationContext(), response.body().getMassage().toString(), Toast.LENGTH_SHORT).show();
                            pDialog.dismissWithAnimation();
                        }
                    } else {
                        pDialog.dismissWithAnimation();
                        Toast.makeText(getApplicationContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(getApplicationContext(), response.body().getMassage() + "Fails", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismissWithAnimation();
            }
        });
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
                            update_user_wallet_cash(id,orderAmount);
                            store_user_offline();
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


    private void update_user_wallet_cash(String id, int orderAmount) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<Res> call = api.update_Added_money(id,orderAmount);

//        Toast.makeText(this, String.valueOf(id+"  "+orderAmount), Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddCashActivity.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    Log.e("RES",response.body().getMassage());
                    startActivity(new Intent(AddCashActivity.this,MainActivity.class));
//                    SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//
//                    editor.putInt("wallet_amount",wallet_amount+orderAmount);
//                                        editor.apply();
                 // Toast.makeText(AddCashActivity.this, "Amount Added Successfully In Your Wallet.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddCashActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
//                Toast.makeText(AddCashActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.e("TAG",t.getMessage());
            }
        });

    }

    public void store_user_offline(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String num = mAuth.getCurrentUser().getPhoneNumber() ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<UserModel>> user =  apiInterface.get_user(num);

        user.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("number",num);
                    editor.putString("user_name",response.body().get(0).getFullname());
                    editor.putString("user_email",response.body().get(0).getEmail());
                    editor.putString("user_id",response.body().get(0).getId());
                    editor.putString("refer_code",response.body().get(0).getRefer_code());
                    editor.putInt("wallet_amount",response.body().get(0).getWallet_amount());
                    editor.putInt("winning_amount",response.body().get(0).getWinning_amount());
                    editor.putInt("bonus",response.body().get(0).getBonus());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }    public class Amount{
        int Amount;

        public Amount(int amount) {
            Amount = amount;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int amount) {
            Amount = amount;
        }
    }

    public class Amount_Adapter extends RecyclerView.Adapter<Amount_Adapter.viewHolder>{
        List<Amount> amountList;
        AmountListner amountListner;

        public Amount_Adapter(List<Amount> amountList,AmountListner amountListner) {
            this.amountList = amountList;
            this.amountListner = amountListner;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.amount_item,parent,false);
            return new viewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
                 holder.textView.setText(String.valueOf(amountList.get(position).getAmount()));
                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          selectedAmount = amountList.get(position).getAmount();
                          textInputEditText.setText(String.valueOf(selectedAmount));
                      }
                  });

        }

        @Override
        public int getItemCount() {
            return amountList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.amountNumberTextview);
            }
        }
    }
}