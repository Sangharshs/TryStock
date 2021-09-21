package com.apps.myturn.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myturn.Adapters.MenuItemsAdapter;
import com.apps.myturn.ApiInterface;
import com.apps.myturn.GoproActivity;
import com.apps.myturn.LoginActivity;
import com.apps.myturn.Models.MenuModel;
import com.apps.myturn.Models.UserModel;
import com.apps.myturn.R;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.apps.myturn.Api.BASE_URL;

public class MoreFragment extends Fragment {
    View view;
    ImageButton imageButton;
    FirebaseAuth mAuth;
    TextView username_textView, user_mo_textView,bigLater,refer_count_tv;
    String number,user_name,refer_code,email;
    Button button;
    MenuItemsAdapter adapter;
    RecyclerView recyclerView;
    TextView textViewTotalBalance;
    int winning_amount,bonus,wallet_amount,total_balance;
    String refer_count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);

        user_mo_textView = view.findViewById(R.id.user_mo_number);
        username_textView = view.findViewById(R.id.user_name);
        bigLater = view.findViewById(R.id.bigText);
        refer_count_tv = view.findViewById(R.id.refer_count_tv);

        bigLater = view.findViewById(R.id.bigText);
        imageButton = view.findViewById(R.id.log_Out_btn_ImageView);
        button = view.findViewById(R.id.groProMenuFra);
        recyclerView = view.findViewById(R.id.menuItems);
        textViewTotalBalance = view.findViewById(R.id.totalBalance);

        mAuth = FirebaseAuth.getInstance();
      if(number!=null) {
          number = mAuth.getCurrentUser().getPhoneNumber();
      }
        SharedPreferences preferences = view.getContext().getSharedPreferences("USER",MODE_PRIVATE);
        email =           preferences.getString("email","");
        refer_code =      preferences.getString("refer_code","");
        user_name =       preferences.getString("user_name","");
        wallet_amount =   preferences.getInt("wallet_amount",0);
        bonus    =        preferences.getInt("bonus",0);
        winning_amount =  preferences.getInt("winning_amount",0);
        refer_count = preferences.getString("refer_count","0");



        user_mo_textView.setText(number);
        username_textView.setText(user_name);

        total_balance = wallet_amount+winning_amount+bonus;

        textViewTotalBalance.setText(String.valueOf("₹ "+ total_balance));

        bigLater.setText(String.valueOf(user_name.charAt(0)));

        refer_count_tv.setText(refer_count);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null) {
                    mAuth.signOut();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GoproActivity.class));
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<MenuModel> menuModels = new ArrayList<>();

        menuModels.add(new MenuModel(R.drawable.wallet_,"My Wallet",""));
        menuModels.add(new MenuModel(R.drawable.faqs,"FAQ's","https://www.linkedin.com/feed/"));
        menuModels.add(new MenuModel(R.drawable.ic_baseline_info_24,"How To Play","https://sites.google.com/view/howtoplay/home"));
        menuModels.add(new MenuModel(R.drawable.ic_baseline_privacy_tip_24,"Privacy Policy","https://sites.google.com/view/myturnprivacy/home"));
        menuModels.add(new MenuModel(R.drawable.ic_baseline_news_24,"Terms & Conditions","https://sites.google.com/view/terms/home"));
        menuModels.add(new MenuModel(R.drawable.contact_us,"Contact Us","https://sites.google.com/view/contact/home"));

        adapter = new MenuItemsAdapter(menuModels);
        recyclerView.setAdapter(adapter);

        return view;
    }
}