package com.softrasol.ahmed.digitaltaskeradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.softrasol.ahmed.digitaltaskeradmin.Adapters.ComplaintsAdapter;
import com.softrasol.ahmed.digitaltaskeradmin.Helper.DatabaseHelper;
import com.softrasol.ahmed.digitaltaskeradmin.Helper.GetTimeAgo;
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.Model.UserDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComplaintsActivity extends AppCompatActivity {

    private CircleImageView mRevieverProfile;
    private ImageView mImgSenderImage;
    private TextView mTxtRecieverName, mTxtRecieverAddress, mTxtTime, mTxtTitle, mTxtDescription;
    private TextView mTxtSenderName, mTxtSenderAddress;
    private NotificationsModel list = ComplaintsAdapter.complaintList.get(0);

    private String senderEmail, recieverEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        toolbarInflation();
        widgetsInflation();

        mTxtTitle.setText(list.getTitle());
        mTxtDescription.setText(list.getBody());
        long time = Long.parseLong(list.getTime_stamp());
        mTxtTime.setText(GetTimeAgo.getTimeAgo(time, getApplicationContext()));

        getSenderDetailsDetails(list.getSender_uid());
        getRecieverDetailsDetails(list.getReciever_uid());


    }

    private void getSenderDetailsDetails(String sender_uid) {

        DatabaseHelper.mDatabase.collection("users").document(sender_uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    UserDataModel model = task.getResult().toObject(UserDataModel.class);
                    mTxtSenderName.setText(model.getName());
                    Picasso.get().load(model.getProfile_img()).placeholder(R.drawable.profile_image)
                            .into(mImgSenderImage);
                    mTxtSenderAddress.setText(model.getAddress());
                    senderEmail = model.getEmail();

                }
            }
        });

    }

    private void getRecieverDetailsDetails(String reviever_uid) {

        DatabaseHelper.mDatabase.collection("users").document(reviever_uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    UserDataModel model = task.getResult().toObject(UserDataModel.class);
                    mTxtRecieverName.setText(model.getName());
                    Picasso.get().load(model.getProfile_img()).placeholder(R.drawable.profile_image)
                            .into(mImgSenderImage);
                    mTxtRecieverAddress.setText(model.getAddress());
                    recieverEmail = model.getEmail();

                }
            }
        });

    }

    private void widgetsInflation() {

        mRevieverProfile = findViewById(R.id.complait_personImage);
        mImgSenderImage = findViewById(R.id.complaint_senderImage);
        mTxtRecieverName = findViewById(R.id.complaint_personName);
        mTxtTime = findViewById(R.id.complaint_date);
        mTxtTitle = findViewById(R.id.complaint_title);
        mTxtDescription = findViewById(R.id.complaint_description);
        mTxtSenderName = findViewById(R.id.complaint_senderName);
        mTxtSenderAddress = findViewById(R.id.complaint_senderAddress);
        mTxtRecieverAddress = findViewById(R.id.complaint_reciever_address);


    }

    public void ContactVendorClick(View view) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",senderEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void ContactPosterClick(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",recieverEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void toolbarInflation() {

        Toolbar toolbar;
        TextView textView;
        ImageButton mBtnBack;
        toolbar = findViewById(R.id.toolbar_complaints);
        textView = toolbar.findViewById(R.id.toolbarText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView.setText("Complaint");
        mBtnBack = toolbar.findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
