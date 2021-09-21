package com.apps.myturn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.myturn.R;
import com.apps.myturn.VerifyBankDetailsActivity;
import com.google.android.material.textfield.TextInputEditText;

import static com.apps.myturn.VerifyBankDetailsActivity.ifsc_code;

public class BankdetailsFragment extends Fragment {
    VerifyBankDetailsActivity activity;
    View view;
    TextInputEditText bank_holder_name,bank_account_number,ifsc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bankdetails, container, false);

        bank_account_number = view.findViewById(R.id.bank_account_number);
        bank_holder_name = view.findViewById(R.id.bank_account_holder_name);
        ifsc = view.findViewById(R.id.account_ifsc);

        ifsc.setText(activity.ifsc_code);
        bank_holder_name.setText(activity.bank_holder_name);
        bank_account_number.setText(activity.bank_account_number);

        return view;
    }
}