package com.apps.myturn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.myturn.Adapters.NewsAdapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.Models.NewsModel;
import com.apps.myturn.Models.SliderItem;
import com.apps.myturn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apps.myturn.Api.BASE_URL;

public class NewsFragment extends Fragment {

    View root;
    RecyclerView recyclerView,recyclerView1;
    List<NewsModel> news_Model_List = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = root.findViewById(R.id.news_recyclerview);
        recyclerView1 = root.findViewById(R.id.categoryRecyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView1.setLayoutManager(linearLayoutManager);
        getNews();
        return root;
    }

    private void getNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<NewsModel>> call = api.getNews();
        
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if(response.isSuccessful()){
                    news_Model_List = response.body();
                }
                if(response.body().size()==0){
                    Toast.makeText(getContext(), "No News Found", Toast.LENGTH_SHORT).show();
                }

                NewsAdapter adapter = new NewsAdapter(news_Model_List);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }
        });

    }
}