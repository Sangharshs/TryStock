package com.apps.myturn.Fragments;

import android.app.Activity;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.myturn.Adapters.MyTeamAdapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.Models.MyTeam;
import com.apps.myturn.Models.Res;
import com.apps.myturn.Models.Result;
import com.apps.myturn.R;
import com.apps.myturn.ResultActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;
import static com.apps.myturn.ResultActivity.MATCH_ID;
import static com.apps.myturn.ResultActivity.USER_ID;

public class TeamFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    String match_id,user_id;
    List<MyTeam> team_list = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


               root = inflater.inflate(R.layout.fragment_team, container, false);
        recyclerView = root.findViewById(R.id.myTeamRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        load_team();
        Bundle b = getArguments();
        if(b != null){
            b.getString("match_id");
            b.getString("user_id");
                }else{
                }
//        Toast.makeText(getContext(), String.valueOf(MATCH_ID+"\n"+USER_ID), Toast.LENGTH_SHORT).show();

        return root;
    }

    private void load_team() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Call<List<MyTeam>> getResult = apiInterface.getTeamList(MATCH_ID,USER_ID);

        getResult.enqueue(new Callback<List<MyTeam>>() {
            @Override
            public void onResponse(Call<List<MyTeam>> call, Response<List<MyTeam>> response) {
                team_list = response.body();
                if(response.isSuccessful()) {
//                  rank.setText("Rank: " + response.body().get(0).getRank());
                    team_list = response.body();

                    Log.e("TeamList",response.body().toString());
                }
                Log.e("TAG0",response.toString());
                MyTeamAdapter adapter = new MyTeamAdapter(team_list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MyTeam>> call, Throwable t) {
                Log.e("TAG1",t.getMessage());
            }
        });


    }

}