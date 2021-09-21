package com.apps.myturn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.myturn.Adapters.LeaderboardAdapter;
import com.apps.myturn.Adapters.MyTeamAdapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.Models.MyTeam;
import com.apps.myturn.Models.Result;
import com.apps.myturn.R;

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

public class LeaderboardFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    List<Result> leader_board_List = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerView = v.findViewById(R.id.leaderBoard_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        load_leaderboard();
        Bundle b = getArguments();

        if(b != null){
            b.getString("match_id");
            b.getString("user_id");
//            Toast.makeText(getContext(), String.valueOf(b.getString("match_id")+"\n"+b.getString("user_id")), Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(getContext(), "Arguments Are Null", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void load_leaderboard() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<Result>> getResult = apiInterface.get_result(MATCH_ID);

        getResult.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                if (response.isSuccessful()) {
//                  rank.setText("Rank: " + response.body().get(0).getRank());
                    leader_board_List = response.body();

                    Log.e("TeamList", response.body().toString());
                }
                Log.e("TAG0", response.toString());
                LeaderboardAdapter adapter = new LeaderboardAdapter(leader_board_List);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.e("TAG1", t.getMessage());
            }
        });

    }
}