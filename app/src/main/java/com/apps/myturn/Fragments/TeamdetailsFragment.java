package com.apps.myturn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.apps.myturn.Adapters.RankListAdapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.MatchDetailsActivity;
import com.apps.myturn.Models.MatchDetailsModel;
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

public class TeamdetailsFragment extends Fragment {
    RecyclerView recyclerView;
    List<MatchDetailsModel> matchDetailsModelList;
    RankListAdapter adapter;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_teamdetails, container, false);
        recyclerView = v.findViewById(R.id.List);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        load_match_details();
        // Inflate the layout for this fragment
        return v;
    }

    private void load_match_details() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);


        Call<List<MatchDetailsModel>> call = api.match_details_list(String.valueOf(MATCH_ID));
        call.enqueue(new Callback<List<MatchDetailsModel>>() {
            @Override
            public void onResponse(Call<List<MatchDetailsModel>> call, Response<List<MatchDetailsModel>> response) {
                if(response.isSuccessful()){
                    matchDetailsModelList = new ArrayList<>();

                    matchDetailsModelList = response.body();
                    adapter = new RankListAdapter(matchDetailsModelList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<MatchDetailsModel>> call, Throwable t) {

//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}