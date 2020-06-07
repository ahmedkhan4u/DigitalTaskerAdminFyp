package com.softrasol.ahmed.digitaltaskeradmin.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softrasol.ahmed.digitaltaskeradmin.Adapters.NotificationsAdapter;
import com.softrasol.ahmed.digitaltaskeradmin.ManageUsersActivity;
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.R;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private View mView;
    private RecyclerView mRecyclerView;
    private List<NotificationsModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        mView = inflater.inflate(R.layout.fragment_home, container, false);

        userRecyclerViewImplementation();

        return mView;
    }

    private void userRecyclerViewImplementation() {

        list = new ArrayList<>();

        mRecyclerView = mView.findViewById(R.id.recycler_view_home);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications").whereEqualTo("reciever_uid", "admin")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (!list.isEmpty()){
                            list.clear();
                        }

                        if (e != null){
                            Log.d("dxdiag", e.getMessage());
                            return;
                        }

                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){

                            NotificationsModel model = snapshot.toObject(NotificationsModel.class);
                            list.add(model);
                        }

                        NotificationsAdapter adapter = new NotificationsAdapter(list, getContext());
                        mRecyclerView.setAdapter(adapter);

                    }
                });

    }


}
