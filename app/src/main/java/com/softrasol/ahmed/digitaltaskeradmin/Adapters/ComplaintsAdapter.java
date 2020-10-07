package com.softrasol.ahmed.digitaltaskeradmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softrasol.ahmed.digitaltaskeradmin.ComplaintsActivity;
import com.softrasol.ahmed.digitaltaskeradmin.Helper.GetTimeAgo;
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.Model.UserDataModel;
import com.softrasol.ahmed.digitaltaskeradmin.R;
import com.softrasol.ahmed.digitaltaskeradmin.ViewUserProficeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ViewHolder> {

    private List<NotificationsModel> list;
    private Context context;

    public static List<NotificationsModel> complaintList = new ArrayList<>();

    public ComplaintsAdapter(List<NotificationsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.complaints_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final NotificationsModel model = list.get(position);

        getRevieverImageAndNameFromDatabase(model, holder);
        getSenderDataFromFirestore(model, holder);

        long time_stamp = Long.parseLong(model.getTime_stamp());
        holder.mTxtDateTime.setText(GetTimeAgo.getTimeAgo(time_stamp, context));
        holder.mTxtComplaintTitle.setText(model.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintList.add(list.get(position));
                context.startActivity(new Intent(context, ComplaintsActivity.class));
            }
        });

    }

    private void getSenderDataFromFirestore(NotificationsModel model, final ViewHolder holder) {

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("users");

        Query query = collectionReference.whereEqualTo("uid", model.getSender_uid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(context, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot snapshot : task.getResult()){

                        UserDataModel userDataModel = snapshot.toObject(UserDataModel.class);

                        holder.mTxtSenderName.setText(userDataModel.getName());
                        holder.mTxtSenderAddress.setText(userDataModel.getAddress());

                    }

                }else {
                    Log.d("dxdiag", task.getException().getMessage());
                }
            }
        });

    }

    private void getRevieverImageAndNameFromDatabase(final NotificationsModel model, final ViewHolder holder) {

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("users");

        Query query = collectionReference.whereEqualTo("uid", model.getReciever_uid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(context, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot snapshot : task.getResult()){

                        UserDataModel userDataModel = snapshot.toObject(UserDataModel.class);

                        holder.mTxtName.setText(userDataModel.getName());
                        Picasso.get().load(userDataModel.getProfile_img()).placeholder(R.drawable.profile_image)
                                .resize(80, 80).into(holder.mImgProfile);
                    }

                }else {
                    Log.d("dxdiag", task.getException().getMessage());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mImgProfile;
        private TextView mTxtName, mTxtDateTime, mTxtComplaintTitle, mTxtSenderName,
        mTxtSenderAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProfile = itemView.findViewById(R.id.complait_personImage);
            mTxtName = itemView.findViewById(R.id.complaint_personName);
            mTxtDateTime = itemView.findViewById(R.id.complaint_date);
            mTxtComplaintTitle = itemView.findViewById(R.id.complaint_title);
            mTxtSenderName =  itemView.findViewById(R.id.complaint_sender_name);
            mTxtSenderAddress = itemView.findViewById(R.id.complaint_address);

        }
    }
}
