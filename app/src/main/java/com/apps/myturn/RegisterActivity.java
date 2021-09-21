package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class RegisterActivity extends AppCompatActivity {

    MaterialButton submitBtn;
    String number;
    TextInputEditText full_name_edit_text, email_edit_text, refer_code_edit_text;
    String name, email, refercode;
    FirebaseAuth auth;
    LinearLayout slideDown;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submitBtn = findViewById(R.id.submitBTN_material);
        full_name_edit_text = findViewById(R.id.full_name_edit_text);
        email_edit_text = findViewById(R.id.email_edit_text);
        refer_code_edit_text = findViewById(R.id.refferel_code_edit_text);
        slideDown = findViewById(R.id.slideDownAnimation);
        auth = FirebaseAuth.getInstance();

        number = auth.getCurrentUser().getPhoneNumber();;

//      check_user_exists_or_not();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                store_user();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        TransitionManager.beginDelayedTransition(slideDown);
        check_user_exists_or_not();
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

        Call<List<Res>> check_user = api.check_user_exist(number);

        check_user.enqueue(new Callback<List<Res>>() {
            @Override
            public void onResponse(Call<List<Res>> call, Response<List<Res>> response) {
                Log.d("MS1", String.valueOf(response.isSuccessful()));
                if (response.isSuccessful()) {

                    if(response.body().get(0).getMassage().equals("Success.")){
//                      Toast.makeText(RegisterActivity.this,response.body().get(0).getMassage() , Toast.LENGTH_SHORT).show();
                        pDialog.dismissWithAnimation();
                    }
                    if(response.body().get(0).getMassage().equals("FAIL.")){
//                        Toast.makeText(RegisterActivity.this,response.body().get(0).getMassage() , Toast.LENGTH_SHORT).show();
                       pDialog.dismissWithAnimation();
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

    private void store_user() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        email = email_edit_text.getText().toString();
        name = full_name_edit_text.getText().toString();
        refercode = refer_code_edit_text.getText().toString();

//      Toast.makeText(this,number+email+refercode+name , Toast.LENGTH_SHORT).show();


        if (!email.equals("") && !name.equals("") && !number.equals("")) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);

            Call<UserModel> storeUser = api.register_user(name, email, number, refercode);

            storeUser.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        pDialog.dismissWithAnimation();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra("number", "+91"+number);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        pDialog.dismissWithAnimation();
                        Toast.makeText(RegisterActivity.this, "Something went wrong.." + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(RegisterActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if(!email_edit_text.getText().equals("")){
                   email_edit_text.setError("Required");
        }
        else
        {
            full_name_edit_text.setError("Required");
        }
    }
}