package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class GoproActivity extends AppCompatActivity {
    MaterialTextView editText;
    ImageView imageView;
    ImageButton goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gopro);

        goback = findViewById(R.id.goBackButton);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView = findViewById(R.id.copyImgbtn);

        editText = findViewById(R.id.refferel_code_edit_text);

        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        String refer_code = preferences.getString("refer_code","");
        editText.setText(refer_code);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("refer_code", refer_code);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(GoproActivity.this, "Refer Code Copied.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}