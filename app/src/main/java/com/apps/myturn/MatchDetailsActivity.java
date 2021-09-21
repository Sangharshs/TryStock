package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myturn.Adapters.RankListAdapter;
import com.apps.myturn.Models.MatchDetailsModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class MatchDetailsActivity extends AppCompatActivity {


    RankListAdapter adapter;
    List<MatchDetailsModel> matchDetailsModelList;
    RecyclerView recyclerView;
    SweetAlertDialog pDialog;
    TextView dateTextView,prizePoolTextview;
    ImageButton iButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        recyclerView = findViewById(R.id.List);
        iButton = findViewById(R.id.goBackButton);
        dateTextView = findViewById(R.id.mDate);
        prizePoolTextview = findViewById(R.id.ppAmount);

        String id = getIntent().getStringExtra("id");
        String prize_pool = getIntent().getStringExtra("prize_pool");
        String date       = getIntent().getStringExtra("date");

        dateTextView.setText(date);
        prizePoolTextview.setText(prize_pool);

        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    
        ApiInterface api = retrofit.create(ApiInterface.class);
//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        Call<List<MatchDetailsModel>> call = api.match_details_list(id);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<MatchDetailsModel>>() {
            @Override
            public void onResponse(Call<List<MatchDetailsModel>> call, Response<List<MatchDetailsModel>> response) {
              if(response.isSuccessful()){
                  matchDetailsModelList = new ArrayList<>();

                  matchDetailsModelList = response.body();
                  adapter = new RankListAdapter(matchDetailsModelList);
                  recyclerView.setAdapter(adapter);
                  pDialog.dismissWithAnimation();
              }
            }

            @Override
            public void onFailure(Call<List<MatchDetailsModel>> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(MatchDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}