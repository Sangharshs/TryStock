package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.myturn.Adapters.ViewpagerAdapter;
import com.apps.myturn.Adapters.ViewpagerverificationAdapter;
import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.VerificationModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class VerifyBankDetailsActivity extends AppCompatActivity {

    ImageButton imageButton;
    //    ViewPager viewPager2;
//    ViewpagerAdapter viewPagerAdapter;
//    TabLayout tabLayout;
    public static String user_id, pan_number, pan_name, bank_holder_name, bank_account_number, ifsc_code;
    TextInputEditText panNAME, panNUMBER, ACCOUNT_H_NAME, ACCOUNT_NUMBER, ifsc_CODE;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_bank_details);
        imageButton = findViewById(R.id.goBackButton);

        panNAME = findViewById(R.id.pan_Name);
        panNUMBER = findViewById(R.id.pan_Number);
        ACCOUNT_NUMBER = findViewById(R.id.bank_account_number);
        ACCOUNT_H_NAME = findViewById(R.id.bank_account_holder_name);
        ifsc_CODE = findViewById(R.id.account_ifsc);
        submitBtn  = findViewById(R.id.submit);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pan_name = panNAME.getText().toString();
        pan_number =panNUMBER.getText().toString();
        bank_holder_name = ACCOUNT_H_NAME.getText().toString();
        bank_account_number = ACCOUNT_NUMBER.getText().toString();
        ifsc_code = ifsc_CODE.getText().toString();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                validate(){
//
//                }

//                if(pan_name.equals("")){
//                    panNAME.setError("Required");
//                }else if(pan_number.equals("")){
//                    panNUMBER.setError("Required");
//                }else if(bank_account_number.equals("")){
//                    ACCOUNT_NUMBER.setError("Required");
//                }else if(bank_holder_name.equals("")){
//                    ACCOUNT_H_NAME.setError("Required");
//                }else if(ifsc_code.equals("")){
//                    ifsc_CODE.setError("Required");
//                }

//
//                if(!panNUMBER.getText().equals("") && !panNAME.getText().equals("")
//                   && !ACCOUNT_NUMBER.getText().equals("") && !ACCOUNT_H_NAME.getText().equals("")
//                && !ifsc_CODE.getText().equals("")) {
//                    submit_verification_data();
//                }else{
//                    Toast.makeText(VerifyBankDetailsActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
//                }
            }
        });

//        viewPager2 = findViewById(R.id.viewpager22);
//        tabLayout  = findViewById(R.id.tabs);
//
//        ViewpagerverificationAdapter adapter = new ViewpagerverificationAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//
//        //Adding adapter to pager
//        viewPager2.setAdapter(adapter);
//
//        // It is used to join TabLayout with ViewPager.
//        tabLayout.setupWithViewPager(viewPager2);

        SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
        user_id = preferences.getString("user_id", "");



        load_verification_data();

    }

    private boolean validate(TextInputEditText[] fields){
        for(int i = 0; i < fields.length; i++){
            TextInputEditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }

    private void submit_verification_data() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Log.e("DATA",user_id + pan_name + pan_number+bank_holder_name+bank_account_number+ifsc_code);
        Call<Res> call = api.update_verification(user_id,pan_name,pan_number,bank_holder_name,bank_account_number,ifsc_code);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if(response.isSuccessful()){
                    Toast.makeText(VerifyBankDetailsActivity.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();response.body().getMassage();
//                    finish();
                }else{
                    Toast.makeText(VerifyBankDetailsActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
               Log.e("TAG",t.getMessage());
            }
        });

    }

    private void load_verification_data() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<VerificationModel>> call = api.get_verification_details(user_id);

        call.enqueue(new Callback<List<VerificationModel>>() {
            @Override
            public void onResponse(Call<List<VerificationModel>> call, Response<List<VerificationModel>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().equals("")) {
                        panNAME.setText(response.body().get(0).getPan_name());
                        panNUMBER.setText(response.body().get(0).getPan_number());
                        ACCOUNT_H_NAME.setText(response.body().get(0).getAccount_holder_name());
                        ACCOUNT_NUMBER.setText(response.body().get(0).getAccount_number());
                        ifsc_CODE.setText(response.body().get(0).getIfsc_code());

                        pan_name = response.body().get(0).getPan_name();
                        pan_number = response.body().get(0).getPan_number();
                        bank_account_number = response.body().get(0).getAccount_number();
                        bank_holder_name = response.body().get(0).getAccount_holder_name();
                        ifsc_code = response.body().get(0).getIfsc_code();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VerificationModel>> call, Throwable t) {

            }
        });


    }

//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        viewPager2.setCurrentItem(1);
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }
}