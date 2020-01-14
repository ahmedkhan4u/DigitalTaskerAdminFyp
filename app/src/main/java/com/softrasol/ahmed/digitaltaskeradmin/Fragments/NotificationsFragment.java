package com.softrasol.ahmed.digitaltaskeradmin.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softrasol.ahmed.digitaltaskeradmin.NotificationsActivity;
import com.softrasol.ahmed.digitaltaskeradmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_notifications, container, false);

        mView.findViewById(R.id.UserProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NotificationsActivity.class));
            }
        });

        return mView;
    }

}
