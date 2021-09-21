package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyWalletActivity extends AppCompatActivity {

    ImageButton imageButton;
    Button add_cash,verify_acc_btn;
    SharedPreferences preferences;
    int wallet_amount,winning_amount,bonus;
    TextView wallet_amountTV,bonus_amountTV,winning_amountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        imageButton = findViewById(R.id.goBackButton);
        add_cash    = findViewById(R.id.add_amount);
        verify_acc_btn = findViewById(R.id.verifyAccountBtn);
        wallet_amountTV = findViewById(R.id.WALLET_AMOUNT);
        winning_amountTV= findViewById(R.id.WINNING_AMOUNT);
        bonus_amountTV  = findViewById(R.id.BONUS_AMOUNT);

        preferences = getSharedPreferences("USER",MODE_PRIVATE);

        wallet_amount = preferences.getInt("wallet_amount",0);
        bonus    = preferences.getInt("bonus",0);
        winning_amount = preferences.getInt("winning_amount",0);

        winning_amountTV.setText(String.valueOf("₹ "+ winning_amount));
        bonus_amountTV.setText(String.valueOf("₹ "+ bonus));
        wallet_amountTV.setText(String.valueOf("₹ "+  wallet_amount));

        verify_acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWalletActivity.this,VerifyBankDetailsActivity.class));
            }
        });

        add_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWalletActivity.this,AddCashActivity.class));
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}