package com.apps.myturn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.myturn.Fragments.HomeFragment;
import com.apps.myturn.Fragments.MatchesFragment;
import com.apps.myturn.Fragments.MoreFragment;
import com.apps.myturn.Fragments.NewsFragment;
import com.apps.myturn.Models.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    String num;
    String refer_code = "0";
    List<UserModel> refer_count_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new HomeFragment())
                .commit();
        fragment = null;

        refer_count_list();

        store_user_offline();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.menu_home:    fragment = new HomeFragment();
                        break;

                    case R.id.menu_matches: fragment = new MatchesFragment();
                        break;

                    case R.id.menu_news:    fragment = new NewsFragment();
                        break;

                    case R.id.menu_more:    fragment = new MoreFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment)
                        .commit();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("You want to exit ?")
                .setContentText("Are you sure for exit from this app.")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }



    public void store_user_offline(){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

            num = mAuth.getCurrentUser().getPhoneNumber();


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
                    editor.putString("refer_count",String.valueOf(refer_count_list.size()));
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    public void refer_count_list(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<UserModel>> call =  apiInterface.refer_list(refer_code);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    refer_count_list = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }
}