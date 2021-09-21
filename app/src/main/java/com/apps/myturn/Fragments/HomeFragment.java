package com.apps.myturn.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.myturn.Adapters.Game_List_Adapter;
import com.apps.myturn.Adapters.SliderAdapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.GoproActivity;
import com.apps.myturn.Models.GameModel;
import com.apps.myturn.Models.SliderItem;
import com.apps.myturn.R;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;
import static com.apps.myturn.Api.IMG1;

public class HomeFragment extends Fragment {
    View view;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    RecyclerView recyclerView;
    GameModel gameModel;
    List<GameModel> game_model_list = new ArrayList<>();
    Game_List_Adapter game_list_adapter;
    SweetAlertDialog  pDialog ;
    ArrayList<SliderItem> sliderItems = new ArrayList<>();
    ArrayList<SliderItem> banner = new ArrayList<>();
    RoundedImageView roundedImageView;
    MaterialButton materialButton;
    private Activity mActivity;
    CardView roundedImageCard;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager2 = view.findViewById(R.id.viewpager);
        recyclerView = view.findViewById(R.id.game_list_recyclerview);
        roundedImageView = view.findViewById(R.id.promotional_banner_2);
        materialButton = view.findViewById(R.id.goProScreen);
        roundedImageCard = view.findViewById(R.id.roundedImageCard);

        viewPager2.setVisibility(View.GONE);
        roundedImageCard.setVisibility(View.GONE);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadSlider();
        loadGameItems();
        loadBanner();

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), GoproActivity.class));
            }
        });
        return view;
    }

    private void loadBanner() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<ArrayList<SliderItem>> call = api.banner();

        call.enqueue(new Callback<ArrayList<SliderItem>>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(Call<ArrayList<SliderItem>> call, Response<ArrayList<SliderItem>> response) {
                if (getActivity() == null) {
                    return;
                }
                if(response.isSuccessful()) {
                    banner = response.body();

                    try {
                        Glide.with(getContext()).load(IMG1 + response.body().get(0).getImageUrl()).into(roundedImageView);

                        if (response.body().get(0).getPromotionalUrl() != null) {
                            roundedImageCard.setVisibility(View.VISIBLE);

                            roundedImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri uri = Uri.parse(response.body().get(0).getPromotionalUrl());
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            });
                        }
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SliderItem>> call, Throwable t) {

            }
        });
    }

    private void loadGameItems() {

        pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<GameModel>> call = api.gamesCategories();

        call.enqueue(new Callback<List<GameModel>>() {
            @Override
            public void onResponse(Call<List<GameModel>> call, Response<List<GameModel>> response) {

                if(response.isSuccessful()) {
                    game_model_list = response.body();
                    pDialog.dismiss();
                    game_list_adapter = new Game_List_Adapter(game_model_list);
                    game_list_adapter.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();
                    recyclerView.setAdapter(game_list_adapter);
                }
            }

            @Override
            public void onFailure(Call<List<GameModel>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadSlider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<ArrayList<SliderItem>> call = api.sliders_list();

        call.enqueue(new Callback<ArrayList<SliderItem>>() {
            @Override
            public void onResponse(Call<ArrayList<SliderItem>> call, Response<ArrayList<SliderItem>> response) {
              if(response.isSuccessful()) {

                  viewPager2.setVisibility(View.VISIBLE);
                  ArrayList<SliderItem> sliderItems = response.body();
                  Log.e("TAG",response.message().toString());
                  viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
                  viewPager2.setClipToPadding(false);
                  viewPager2.setClipChildren(false);
                  viewPager2.setOffscreenPageLimit(3);
                  viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                  CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                  compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                  compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                      @Override
                      public void transformPage(@NonNull View page, float position) {
                          float r = 1 - Math.abs(position);
                          page.setScaleY(0.85f + r * 0.15f);
                      }
                  });

                  viewPager2.setPageTransformer(compositePageTransformer);

                  viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                      @Override
                      public void onPageSelected(int position) {
                          super.onPageSelected(position);
                          sliderHandler.removeCallbacks(sliderRunnable);
                          sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
                      }
                  });
              }
            }

            @Override
            public void onFailure(Call<ArrayList<SliderItem>> call, Throwable t) {

            }
        });


    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+2);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 1000);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}