package com.apps.myturn.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apps.myturn.Adapters.CompleteMatchesAdapter;
import com.apps.myturn.Adapters.Joined_Matches_Adapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.Models.JoinedMatchModel;
import com.apps.myturn.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.apps.myturn.Api.BASE_URL;

public class CompletedFragment extends Fragment {
    View v;
    MaterialButton button;
    List<JoinedMatchModel> match_Model_List  = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayout quoteCard;
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_completed, container, false);
       button = v.findViewById(R.id.makemyteam1);
       recyclerView = v.findViewById(R.id.upcoming_matches_recyclerView);
       quoteCard = v.findViewById(R.id.quoteCard);

       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
       linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
       recyclerView.setLayoutManager(linearLayoutManager);

       load_upcoming_joined();

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getParentFragmentManager().beginTransaction().replace(R.id.frame_container,
                       new HomeFragment())
                       .commit();
           }
       });
        return v;
    }

    private void load_upcoming_joined() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        SharedPreferences preferences = v.getContext().getSharedPreferences("USER",MODE_PRIVATE);

        String user_id = preferences.getString("user_id","");

        Call<List<JoinedMatchModel>> getList = apiInterface.get_completed_matches(user_id);

        getList.enqueue(new Callback<List<JoinedMatchModel>>() {
            @Override
            public void onResponse(Call<List<JoinedMatchModel>> call, Response<List<JoinedMatchModel>> response) {
                if(response.isSuccessful()){
                    match_Model_List = response.body();
                    CompleteMatchesAdapter adapter = new CompleteMatchesAdapter(match_Model_List);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<JoinedMatchModel>> call, Throwable t) {
//              Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                quoteCard.setVisibility(View.VISIBLE);
            }
        });


    }
}