package com.softrasol.ahmed.digitaltaskeradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.softrasol.ahmed.digitaltaskeradmin.Model.NotificationsModel;
import com.softrasol.ahmed.digitaltaskeradmin.Model.UserDataModel;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewUserProficeActivity extends FragmentActivity implements OnMapReadyCallback {

    private ImageView mImgProfile, mImgCnicFront, mImgCnicBack;
    private TextView mTxtName, mTxtCategory, mTxtStatus, mTxtEmail, mTxtAddress, mTxtDescription,
    mTxtPhone;
    private String mUid;

    private GoogleMap mMap;
    private Button mBtnVerifyUser, mBtnUnVerifyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        mUid = getIntent().getStringExtra("uid");

        getSupportMapFragment();
        toolbarInflation();
        widgetsInflation();

        getUserDetailsFromFirebaseDatabase();

        verifyUserClick();


    }

    private void verifyUserClick() {

        mBtnVerifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance()
                        .collection("users");

                DocumentReference documentReference = collectionReference.document(mUid);

                Map map = new HashMap();
                map.put("is_verified", "true");

                documentReference.update(map).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ViewUserProficeActivity.this,
                                    "User Verified", Toast.LENGTH_LONG).show();
                            sendUserVerifiedNotification();
                        }
                    }
                });

            }
        });

    }

    private void sendUserVerifiedNotification() {
        CollectionReference notificationReference = FirebaseFirestore.getInstance()
                .collection("notifications");

        String uid = notificationReference.document().getId();
        DocumentReference documentReference = notificationReference.document(uid);

        Date date = new Date();
        String mDate = date.toLocaleString();

        NotificationsModel model = new NotificationsModel("Verified by admin"
                ,"Your profile is verified by admin keep using our services",mDate, "admin"
                ,mUid,"false","new user",uid);


        documentReference.set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ViewUserProficeActivity.this,
                            "User approval notification send", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(ViewUserProficeActivity.this,
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserDetailsFromFirebaseDatabase() {

        CollectionReference collectionReference = FirebaseFirestore.getInstance()
                .collection("users");

        DocumentReference documentReference = collectionReference.document(mUid);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    UserDataModel userDataModel = task.getResult().toObject(UserDataModel.class);
                    setDataToLayouts(userDataModel);

                }else {
                    Toast.makeText(getApplicationContext(),
                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setDataToLayouts(UserDataModel model) {

        Picasso.get().load(model.getProfile_img()).placeholder(R.drawable.profile_image)
                .resize(400, 400).into(mImgProfile);

        Picasso.get().load(model.getCnic_front_img()).placeholder(R.drawable.profile_image)
                .resize(100, 100).into(mImgCnicFront);

        Picasso.get().load(model.getCnic_back_img()).placeholder(R.drawable.profile_image)
                .resize(100, 100).into(mImgCnicBack);

        mImgCnicFront.setPadding(0,0,0,0);
        mImgCnicBack.setPadding(0,0,0,0);
        mImgCnicBack.setScaleType(ImageView.ScaleType.FIT_XY);
        mImgCnicFront.setScaleType(ImageView.ScaleType.FIT_XY);

        mTxtName.setText(model.getName());
        mTxtCategory.setText(model.getCategory());
        mTxtAddress.setText(model.getAddress());
        mTxtDescription.setText(model.getDescription());
        mTxtEmail.setText(model.getEmail());
        mTxtPhone.setText(model.getPhone());

        if (model.getIs_verified().equalsIgnoreCase("false")){
            mTxtStatus.setText("Un Verified");
            mTxtStatus.setTextColor(Color.parseColor("#FF1E14"));
        }else {
            mTxtStatus.setText("Verified");
            mTxtStatus.setTextColor(Color.parseColor("#4CAF50"));
            mBtnVerifyUser.setVisibility(View.GONE);
            mBtnUnVerifyUser.setVisibility(View.VISIBLE);
        }

        // Set Location On Google Map
        LatLng latLng = new LatLng(Double.parseDouble(model.getLat()),
                Double.parseDouble(model.getLng()));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).snippet(model.getAddress());
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                latLng, 15);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);

    }

    private void getSupportMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_notification);
        mapFragment.getMapAsync(this);
    }

    private void widgetsInflation() {

        mImgProfile = findViewById(R.id.img_notif_verfication_profile);
        mImgCnicFront = findViewById(R.id.img_notify_verif_cnic_front);
        mImgCnicBack = findViewById(R.id.img_notify_verif_cnic_back);

        mTxtName = findViewById(R.id.txt_notify_verif_name);
        mTxtEmail = findViewById(R.id.txt_notify_verif_email);
        mTxtDescription = findViewById(R.id.txt_notify_verif_description);
        mTxtStatus = findViewById(R.id.txt_notify_verif_status);
        mTxtCategory = findViewById(R.id.txt_notify_verif_category);
        mTxtAddress = findViewById(R.id.txt_notify_verif_address);
        mTxtPhone = findViewById(R.id.txt_notify_verif_phone);

        mBtnVerifyUser = findViewById(R.id.btn_notif_verif_verify_profile);
        mBtnUnVerifyUser = findViewById(R.id.btn_notif_verif_un_verify_user);

    }

    private void toolbarInflation() {

        Toolbar toolbar;
        TextView textView;
        ImageButton mBtnBack;
        toolbar = findViewById(R.id.toolbar_notifications);
        textView = toolbar.findViewById(R.id.toolbarText);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView.setText("Notifications");
        mBtnBack = toolbar.findViewById(R.id.btnBack);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

    }
}
