package com.softrasol.ahmed.digitaltaskeradmin.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softrasol.ahmed.digitaltaskeradmin.Adapters.ComplaintsAdapter;
import com.softrasol.ahmed.digitaltaskeradmin.ComplaintsActivity;
import com.softrasol.ahmed.digitaltaskeradmin.Helper.DatabaseHelper;
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintsFragment extends Fragment {


    public ComplaintsFragment() {
        // Required empty public constructor
    }

    private View mView;
    private List<NotificationsModel> list = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_manage_workers, container, false);
        mRecyclerView = mView.findViewById(R.id.recyclerview_complaints);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getComplaintsFormFirestore();


        return mView;
    }

    private void getComplaintsFormFirestore() {

        DatabaseHelper.mDatabase.collection("complaints")

                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (!task.getResult().isEmpty()){
                        for (QueryDocumentSnapshot snapshot : task.getResult()){

                            NotificationsModel model = snapshot.toObject(NotificationsModel.class);
                            list.add(model);

                        }

                        ComplaintsAdapter adapter = new ComplaintsAdapter(list, getActivity());
                        mRecyclerView.setAdapter(adapter);

                    }
                }
            }
        });

    }

}
