package com.apps.myturn.Fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.apps.myturn.ApiInterface;
import com.apps.myturn.MainActivity;
import com.apps.myturn.Models.JoinMatch;
import com.apps.myturn.Models.MatchModel;
import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.StockModel;
import com.apps.myturn.Models.UserModel;
import com.apps.myturn.R;
import com.apps.myturn.Team_List_Activity;
import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.ui.gpay.GooglePayStatusListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.apps.myturn.Api.BASE_URL;
import static com.apps.myturn.Api.CASH_FREE_APP_ID;
import static com.apps.myturn.Api.CURRENCY;
import static com.apps.myturn.Api.PAYMENT_MODE;
import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_BANK_CODE;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_CVV;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_HOLDER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_MM;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_NUMBER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_YYYY;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_OPTION;
import static com.cashfree.pg.CFPaymentService.PARAM_UPI_VPA;
import static com.cashfree.pg.CFPaymentService.PARAM_WALLET_CODE;


public class BalanceSummeryFragment extends BottomSheetDialogFragment {
    View view;
    int total_with_entry;
    int cash;
    Button bottomContinueBtn,payFromWallet;
    Call<Res> call;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    String time_remaining, match_title, match_subtitle, prize_pool, entry_fee, joined_users, total_users, match_date;
    TextView match_title_TEXTView, date, prize_pool_textview, entry_fee_TEXTVIEW, joined_users_TEXTVIEW, total_users_TEXTVIEW;
    Button detailsButton, joinMatchButton, countinueBTN;
    MaterialTextView d_total_amount,d_winningTEXTView;
    RoundedHorizontalProgressBar horizontalProgressBar;
    SharedPreferences preferences;
    String userName,email,phone_number,selStockList;
    FirebaseAuth m;
    MaterialTextView bonusTV,cashamountTV;
    List<StockModel> selectedStocksList;
    String stock_List,user_email,user_id,
            user_name,match_id,refer_count;
    int wallet_amount;
    ProgressBar progressBarforwallet;
    LinearLayout walletDataLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_balance_summery, container, false);

        m = FirebaseAuth.getInstance();

        if (getArguments() == null) {
            Toast.makeText(getContext(), "Null Arguments", Toast.LENGTH_SHORT).show();
        }
        if (getArguments() != null) {
            stock_List = getArguments().getString("SEL_STOCK_LIST");
            match_title = getArguments().getString("match_title");
            match_subtitle = getArguments().getString("match_subtitle");
            prize_pool = getArguments().getString("prize_pool");
            entry_fee = getArguments().getString("entry_fee");
            joined_users = getArguments().getString("joined_users");
            total_users = getArguments().getString("total_users");
            match_date = getArguments().getString("time_remaining");
            match_id = getArguments().getString("match_id");
            selStockList = getArguments().getString("SEL_STOCK_LIST");
        }

        SharedPreferences preferences = view.getContext().getSharedPreferences("USER", MODE_PRIVATE);
        user_email = preferences.getString("user_email", "");
        user_id = preferences.getString("user_id", "");
        user_name = preferences.getString("user_name", "");
        refer_count = preferences.getString("refer_count","0");
        wallet_amount = preferences.getInt("wallet_amount",0);
        bottomContinueBtn = view.findViewById(R.id.buttonContinue);
        progressBar = view.findViewById(R.id.pb);
        linearLayout = view.findViewById(R.id.balance_data);

        walletDataLayout = view.findViewById(R.id.walletDataLayout);
        progressBarforwallet = view.findViewById(R.id.fetchwalletProgressBar);

        d_winningTEXTView = view.findViewById(R.id.winningTVTEXT);
        d_total_amount = view.findViewById(R.id.d_total_amount);
        bonusTV = view.findViewById(R.id.bonusTV);
        cashamountTV = view.findViewById(R.id.cashAmountTV);

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

        payFromWallet = view.findViewById(R.id.buttonPayFromWallet);

        match_title_TEXTView.setText(match_title);
        prize_pool_textview.setText(prize_pool);
        date.setText(match_date);
        entry_fee_TEXTVIEW.setText(entry_fee);
        joined_users_TEXTVIEW.setText(joined_users);
        total_users_TEXTVIEW.setText(total_users);

        m = FirebaseAuth.getInstance();

        phone_number = m.getCurrentUser().getPhoneNumber();



            try {
                horizontalProgressBar.setProgress(Integer.parseInt(joined_users));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            //d_entry_fee.setText(String.valueOf(entry_fee));

            if (!entry_fee.equals("0")) {
                fetch_user_wallet();
                bottomContinueBtn.setText("Pay " + entry_fee + " Rs ");
            }

            else {
                bottomContinueBtn.setText("Join Match Free");
                d_total_amount.setText("0");
                bonusTV.setText("0");
                cashamountTV.setText("0");
                d_winningTEXTView.setText("0");
                walletDataLayout.setVisibility(View.VISIBLE);
                progressBarforwallet.setVisibility(View.GONE);
            }


            bottomContinueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    if (!entry_fee.equals("0")) {
//                    getPaymentFromWallet();
                        startPayment();

                    }
                    if(wallet_amount>=Integer.parseInt(entry_fee) && !entry_fee.equals("0"))
                    {

                    }
                    if (bottomContinueBtn.getText().toString().equals("Join Match Free")) {
                        int refer_size = Integer.parseInt(refer_count);
                        if (refer_size >= 5) {
                            store_pro_user_match_details();
                        } else {
                            store_free_match_details();
                        }
                    }

                }
            });

            return view;
        }

        private void startPayment() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thecakeswala.in/") // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        String uniqueID = UUID.randomUUID().toString();

        preferences = view.getContext().getSharedPreferences("USER",MODE_PRIVATE);
        userName    = preferences.getString("user_name","");
        email       = preferences.getString("user_email","");

        m = FirebaseAuth.getInstance();

        int orderAmount = Integer.parseInt(d_total_amount.getText().toString());
        call = api.getToken(uniqueID, orderAmount);
        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
           if (response.isSuccessful()) {
                   if (response.body().getMassage() != null) {
                        if (response.body().getMassage().equals("Token generated")) {
                            String token = response.body().getCftoken();
                            HashMap<String, String> params = new HashMap<>();
                            params.put(PARAM_APP_ID, CASH_FREE_APP_ID);
                            params.put(PARAM_ORDER_ID, uniqueID);
                            params.put(PARAM_ORDER_AMOUNT, String.valueOf(orderAmount));
                            params.put(PARAM_ORDER_CURRENCY, CURRENCY);
                            params.put(PARAM_CUSTOMER_NAME, userName);
                            params.put(PARAM_CUSTOMER_EMAIL, email);
                            params.put(PARAM_CUSTOMER_PHONE, m.getCurrentUser().getPhoneNumber());
                            params.put(CFPaymentService.PARAM_NOTIFY_URL, "https://thecakeswala.com/webhook.php");
                            progressBar.setVisibility(View.GONE);
                            CFPaymentService.getCFPaymentServiceInstance().doPayment(getActivity(), params, response.body().getCftoken(), PAYMENT_MODE, "#FFFFFF","#FF000000",true);
                        } else {
                            Log.e("CASGPG", response.body().toString());
                            Toast.makeText(getContext(), response.body().getMassage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    }
                 }else {
                    Toast.makeText(getContext(), response.body().getMassage() + "Fails", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetch_user_wallet(){
        progressBarforwallet.setVisibility(View.VISIBLE);
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

                    progressBarforwallet.setVisibility(View.GONE);
                    walletDataLayout.setVisibility(View.VISIBLE);
                    total_with_entry = Integer.parseInt(entry_fee);
                    cash  = response.body().get(0).getWallet_amount();
                    int bonus = 1;

//                  response.body().get(0).getBonus();
//                  int total = cash + bonus;

                    if(bonus != 0)
                    {
                       bonus  = 1;
                    }

                    total_with_entry = Integer.parseInt(entry_fee) - bonus;
                    d_total_amount.setText(String.valueOf(total_with_entry+bonus));
                    bonusTV.setText(String.valueOf(1));
                    cashamountTV.setText(String.valueOf(total_with_entry));
                    d_winningTEXTView.setText(String.valueOf(response.body().get(0).getWinning_amount()));

                    if(total_with_entry <= wallet_amount){
                        bottomContinueBtn.setVisibility(View.GONE);
                        payFromWallet.setVisibility(View.VISIBLE);
                payFromWallet.setText("Pay "+ entry_fee +" From Wallet.");

                walletDataLayout.setVisibility(View.VISIBLE);
                progressBarforwallet.setVisibility(View.GONE);
            }
                   payFromWallet.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           store_match_from_wallet_money();
//                           Toast.makeText(getContext(), "Pay From Wallet", Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                progressBarforwallet.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void store_free_match_details() {
        String number = m.getCurrentUser().getPhoneNumber();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Gson gson = new Gson();
        // convert your list to json
        String jsonCartList = gson.toJson(selectedStocksList);
        Log.e("JSON_LIST",selStockList);
        Log.d("DATA2",user_name+number+match_title+match_id+selStockList+user_id);

        Call<Res> call = apiInterface.store_free_joined_matches(user_name,number,match_title,match_id,selStockList,user_id);
        Log.e("CALL",call.toString());

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.body().getMassage()), Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("You Joined Match Successfully.")
                            .show();
                             startActivity(new Intent(getContext(),MainActivity.class));
                }else{
                    Log.e("RESPONSE",response.message());
                }

                Log.e("JSON_LIST",response.toString());
                Log.d("DATA2",user_name+number+match_title+match_id+selStockList+user_id);

            }
            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Log.e("CALL",t.getMessage());
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void store_match_from_wallet_money(){
        String number = m.getCurrentUser().getPhoneNumber();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Gson gson = new Gson();
        // convert your list to json
        String jsonCartList = gson.toJson(selectedStocksList);
        Log.e("JSON_LIST",selStockList);
        Log.d("DATA2",user_name+number+match_title+match_id+selStockList+user_id);

        Call<Res> call = apiInterface.store_free_joined_matches(user_name,number,match_title,match_id,selStockList,user_id);
        Log.e("CALL",call.toString());

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {

                Log.e("RESP",response.body().toString());
                if(response.isSuccessful()){
                    update_wallet_money();
                }else{
                    Log.e("RESPONSE",response.message());
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Log.e("CALL",t.getMessage());
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void store_pro_user_match_details() {
        String number = m.getCurrentUser().getPhoneNumber();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Gson gson = new Gson();
        // convert your list to json
        String jsonCartList = gson.toJson(selectedStocksList);
        Log.e("JSON_LIST",selStockList);
        Log.d("DATA2",user_name+number+match_title+match_id+selStockList+user_id);

        Call<Res> call = apiInterface.store_joined_match(user_name,number,match_title,match_id,selStockList,user_id);
        Log.e("CALL",call.toString());

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.body().getMassage()), Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("You Joined Match Successfully.")
                            .show();
                    startActivity(new Intent(getContext(),MainActivity.class));

                }else{
                    Log.e("RESPONSE",response.message());

                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Log.e("CALL",t.getMessage());
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update_wallet_money(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Res> call = apiInterface.update_wallet_money(user_id,Integer.parseInt(entry_fee),1);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.body().getMassage()), Toast.LENGTH_SHORT).show();
                    Log.e("RES",response.body().getMassage());
                    new SweetAlertDialog(getContext())
                            .setTitleText("You Joined Match Successfully.")
                            .show();

                    startActivity(new Intent(getContext(),MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Log.e("RES",t.getMessage().toString());
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}