package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class LoginActivity extends AppCompatActivity {
    MaterialButton nextbtn;
    TextInputEditText textInputEditText;
    String number;
    LinearLayout animateLayout;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nextbtn = findViewById(R.id.nextBTN_material);
        textInputEditText = findViewById(R.id.mobile_number);
        animateLayout = findViewById(R.id.animate);


        if (number != null)
            number = textInputEditText.getText().toString();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        try {
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    number = textInputEditText.getText().toString();
                    if(number.length() != 10){

                        Toast.makeText(LoginActivity.this, "Enter 10 Digits Number", Toast.LENGTH_SHORT).show();
                    }

                    if (!number.equals("") && number.length() == 10) {
                        Intent intent = new Intent(LoginActivity.this, VeriyfiotpActivity.class);
                        intent.putExtra("number", "+91"+number);
                        startActivity(intent);

                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please Valid Enter Mobile Number.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void check_user_exists_or_not() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<Res>> check_user = api.check_user_exist("+91" + number);

        check_user.enqueue(new Callback<List<Res>>() {
            @Override
            public void onResponse(Call<List<Res>> call, Response<List<Res>> response) {
                Log.d("MS1", String.valueOf(response.isSuccessful()));
                if (response.isSuccessful()) {

                    if(response.body().get(0).getMassage().equals("Success.")){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("number", "+91"+number);
                        startActivity(intent);
                        pDialog.dismissWithAnimation();
                        finish();
                    }
                    else if(response.body().get(0).getMassage().equals("FAIL.")){
                        Intent intent = new Intent(LoginActivity.this, VeriyfiotpActivity.class);
                        intent.putExtra("number", "+91"+number);
                        startActivity(intent);
                        pDialog.dismissWithAnimation();
                        finish();

                    }



                }
                Log.d("MS1", response.body().toString());

            }


        @Override
        public void onFailure (Call < List < Res >> call, Throwable t){
//              pDialog.dismissWithAnimation();
            Log.e("MS4", t.getMessage());
            Log.e("MS5", call.toString());
        }
    });
}

    @Override
    protected void onStart() {
        super.onStart();
        TransitionManager.beginDelayedTransition(animateLayout);
//      animateLayout.setVisibility(View.GONE);
    }



}
