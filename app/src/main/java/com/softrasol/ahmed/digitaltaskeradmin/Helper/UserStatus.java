package com.softrasol.ahmed.digitaltaskeradmin.Helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class UserStatus {

    public static void saveUserStatus(String  status){

        Map map = new HashMap<>();
        map.put("status", status);
        DatabaseHelper.mDatabase.collection("users").document(DatabaseHelper.Uid)
                .update(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    //Helper.logMessage("Status Saved");
                }else {
                    //Helper.logMessage(task.getException().getMessage());
                }
            }
        });

    }
}
