package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.myturn.Adapters.GameMatchesAdapter;
import com.apps.myturn.Models.MatchModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class GameMatchesActivity extends AppCompatActivity {
    GameMatchesAdapter adapter;
    RecyclerView recyclerView;
    List<MatchModel> matchModelList;
    String cid;
    SweetAlertDialog pDialog ;
    String stock_Limit;
    ImageButton goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matches);

        goback = findViewById(R.id.goBackButton);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        stock_Limit = getIntent().getStringExtra("stock_limit");

        recyclerView = findViewById(R.id.matchesRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        matchModelList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cid = getIntent().getStringExtra("cid");

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<MatchModel>> call = api.match_model_list(cid);
        Log.e("RESP1",api.toString());
        Log.e("RESP2",call.toString());
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<MatchModel>>() {
            @Override
            public void onResponse(Call<List<MatchModel>> call, Response<List<MatchModel>> response) {
                Log.e("RESP",response.message().toString());
                if (response.isSuccessful()) {
                    matchModelList = response.body();
                    adapter = new GameMatchesAdapter(matchModelList,Integer.parseInt(stock_Limit),cid);
                    recyclerView.setAdapter(adapter);
                    pDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<MatchModel>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(GameMatchesActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}