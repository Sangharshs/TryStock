package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myturn.Adapters.ResultviewpagerAdapter;
import com.apps.myturn.Adapters.ViewpagerAdapter;
import com.apps.myturn.Fragments.LeaderboardFragment;
import com.apps.myturn.Fragments.TeamFragment;
import com.apps.myturn.Models.JoinedMatchModel;
import com.apps.myturn.Models.Result;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ResultActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    public static String USER_ID = NULL;
    public static String MATCH_ID = NULL;
    ViewPager viewPager2;
    TabLayout tabLayout;
    ImageButton goback;
    public String pPool,mName,dAte,rAnk,match_id,user_id;
    TextView prizepool,matchname,date,rank,pointsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        goback = findViewById(R.id.goBackButton);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPager2 = findViewById(R.id.viewpager22);
        tabLayout  = findViewById(R.id.tabs);
        prizepool = findViewById(R.id.prizepoool);
        matchname = findViewById(R.id.matchname);
        date      = findViewById(R.id.date);
        rank      = findViewById(R.id.rank);

        pPool = getIntent().getStringExtra("prize_pool");
        dAte  = getIntent().getStringExtra("date");
        rAnk  = getIntent().getStringExtra("rank");
        mName = getIntent().getStringExtra("match_name");
        user_id = getIntent().getStringExtra("user_id");
        match_id = getIntent().getStringExtra("match_id");

        USER_ID = getIntent().getStringExtra("user_id");
        MATCH_ID = getIntent().getStringExtra("match_id");
        Log.e("UID",user_id);
        Log.e("MID",match_id);

        Bundle bundle = new Bundle();
        bundle.putString("match_id",match_id);
        bundle.putString("user_id",user_id);

        LeaderboardFragment fragobj1 = new LeaderboardFragment();
        fragobj1.setArguments(bundle);

        TeamFragment fragment = new TeamFragment();
        fragment.setArguments(bundle);

        prizepool.setText(pPool);
        date.setText("Date: "+dAte);
        matchname.setText(mName);
        ResultviewpagerAdapter adapter = new ResultviewpagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager2.setAdapter(adapter);
        //It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager2);
        load_rank();
//      fragobj.sendMyData(match_id,user_id);
    }

    private void load_rank() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<Result>> getResult = apiInterface.get_result(match_id);
//      Toast.makeText(this, user_id+" "+match_id, Toast.LENGTH_SHORT).show();
        getResult.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
               if(response.isSuccessful()) {
                   try {
                       rank.setText("Rank: " + response.body().get(0).getRank());
                       response.body().get(0).getStock_data();
                   }catch (IndexOutOfBoundsException e){
                       e.printStackTrace();
                   }
                   }
               Log.e("TAG0",response.toString());
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                  Log.e("TAG1",t.getMessage());
            }
        });

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager2.setCurrentItem(1);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


}