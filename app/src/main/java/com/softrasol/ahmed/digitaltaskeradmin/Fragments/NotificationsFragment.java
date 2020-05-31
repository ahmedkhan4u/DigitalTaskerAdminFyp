package com.softrasol.ahmed.digitaltaskeradmin.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.R;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }

    private View mView;
    private RecyclerView mRecyclerViewNotifications;
    private List<NotificationsModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationRecyclerViewImplementation();


        return mView;
    }

    private void notificationRecyclerViewImplementation() {

        list = new ArrayList<>();

        mRecyclerViewNotifications = mView.findViewById(R.id.recycler_view_notifications);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setReverseLayout(true);
        mRecyclerViewNotifications.setLayoutManager(linearLayoutManager);

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
                        mRecyclerViewNotifications.setAdapter(adapter);

                    }
                });

    }

}
