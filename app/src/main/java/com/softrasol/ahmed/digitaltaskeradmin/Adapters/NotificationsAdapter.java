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
import com.softrasol.ahmed.digitaltaskeradmin.Helper.GetTimeAgo;
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.Model.UserDataModel;
import com.softrasol.ahmed.digitaltaskeradmin.ViewUserProficeActivity;
import com.softrasol.ahmed.digitaltaskeradmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<NotificationsModel> list;
    private Context context;

    public NotificationsAdapter(List<NotificationsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.notification_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotificationsModel model = list.get(position);

        getSenderImageAndNameFromDatabase(model, holder);

        holder.mTxtDateTime.setText(model.getTime_stamp());

        try{
            long time = Long.parseLong(model.getTime_stamp());
            holder.mTxtDateTime.setText(GetTimeAgo.getTimeAgo(time, context));
        }catch (Exception ex){}

        holder.mTxtBody.setText(model.getBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNotificationViewActivity(model.getType(), model.getSender_uid());
            }
        });

    }

    private void gotoNotificationViewActivity(String type, String uid) {

        if (type.equalsIgnoreCase("new user")){

            Intent intent = new Intent(context, ViewUserProficeActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            return;
        }

    }

    private void getSenderImageAndNameFromDatabase(final NotificationsModel model, final ViewHolder holder) {

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("users");

        Query query = collectionReference.whereEqualTo("uid", model.getSender_uid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(context, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot snapshot : task.getResult()){

                        UserDataModel userDataModel = snapshot.toObject(UserDataModel.class);

                        if (userDataModel.getIs_verified()
                                .equalsIgnoreCase("false")){
                            String boldText = userDataModel.getName();
                            String normalText = model.getTitle();
                            SpannableString str = new SpannableString(boldText + " " + normalText);
                            str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            holder.mTxtTitle.setText(str);

                        }else {
                            String boldText = userDataModel.getName();
                            String normalText = "";
                            SpannableString str = new SpannableString(boldText + " " + normalText);
                            holder.mTxtBody.setText(userDataModel.getAddress());
                            str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            holder.mTxtTitle.setText(str);
                        }


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
        private TextView mTxtTitle, mTxtBody, mTxtDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProfile = itemView.findViewById(R.id.img_notification_profile);
            mTxtTitle = itemView.findViewById(R.id.txt_notification_title);
            mTxtBody = itemView.findViewById(R.id.txt_notification_body);
            mTxtDateTime = itemView.findViewById(R.id.txt_notification_time);

        }
    }
}
