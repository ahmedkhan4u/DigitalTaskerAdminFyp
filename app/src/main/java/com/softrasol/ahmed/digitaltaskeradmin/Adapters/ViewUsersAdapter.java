package com.softrasol.ahmed.digitaltaskeradmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.softrasol.ahmed.digitaltaskeradmin.Model.UserDataModel;
import com.softrasol.ahmed.digitaltaskeradmin.R;
import com.softrasol.ahmed.digitaltaskeradmin.ViewUserProficeActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewUsersAdapter extends RecyclerView.Adapter<ViewUsersAdapter.ViewHolder> {

    private Context context;
    private List<UserDataModel> list;

    public ViewUsersAdapter(Context context, List<UserDataModel> list) {

        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_users_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final UserDataModel model = list.get(position);

        Picasso.get().load(model.getProfile_img()).resize(80,80)
                .placeholder(R.drawable.profile_image).into(holder.mImgProfile);
        holder.mTxtName.setText(model.getName());
        holder.mTxtPrice.setText("Per Day Price : "+model.getPrice()+" Rs");

//        if (model.getStatus().equals("online")){
//            holder.mImgStatus.setVisibility(View.VISIBLE);
//        }else {
//            holder.mImgStatus.setVisibility(View.GONE);
//        }


        LatLng latLng1 = new LatLng(Double.parseDouble(model.getLat()),
                Double.parseDouble(model.getLng()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewUserProficeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("uid", model.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mImgProfile;
        private TextView mTxtName, mTxtPrice, mTxtDistance;
        private ImageView mImgStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProfile = itemView.findViewById(R.id.img_view_user_profile);
            mTxtName = itemView.findViewById(R.id.txt_view_user_name);
            mTxtPrice = itemView.findViewById(R.id.txt_view_users_price);
            mTxtDistance = itemView.findViewById(R.id.txt_view_users_distance);
            mImgStatus =  itemView.findViewById(R.id.img_online_status);
        }
    }

}
