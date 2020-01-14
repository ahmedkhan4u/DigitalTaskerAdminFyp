package com.softrasol.ahmed.digitaltaskeradmin.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softrasol.ahmed.digitaltaskeradmin.ActiveTransactionsActivity;
import com.softrasol.ahmed.digitaltaskeradmin.CustomerSupportActivity;
import com.softrasol.ahmed.digitaltaskeradmin.FeedbackActivity;
import com.softrasol.ahmed.digitaltaskeradmin.HistoryTransactionsActivity;
import com.softrasol.ahmed.digitaltaskeradmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_menu, container, false);

        mView.findViewById(R.id.active_transactions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActiveTransactionsActivity.class));
            }
        });

        mView.findViewById(R.id.transactions_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HistoryTransactionsActivity.class));
            }
        });

        mView.findViewById(R.id.customer_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CustomerSupportActivity.class));
            }
        });

        mView.findViewById(R.id.feed_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FeedbackActivity.class));
            }
        });


        return mView;
    }

}
