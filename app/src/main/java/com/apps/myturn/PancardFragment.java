package com.apps.myturn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import static com.apps.myturn.VerifyBankDetailsActivity.pan_name;
import static com.apps.myturn.VerifyBankDetailsActivity.pan_number;

public class PancardFragment extends Fragment {
    VerifyBankDetailsActivity activity;
    View view;
    TextInputEditText panname,pannumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pancard, container, false);

        panname = view.findViewById(R.id.pan_Name);
        pannumber = view.findViewById(R.id.pan_Number);

        pannumber.setText(activity.pan_number);
        panname.setText(activity.pan_name);

        return view;
    }
}