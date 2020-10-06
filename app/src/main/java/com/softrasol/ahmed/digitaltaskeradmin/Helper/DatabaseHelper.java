package com.softrasol.ahmed.digitaltaskeradmin.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


public class DatabaseHelper {

    public static FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    public static FirebaseStorage mStorage = FirebaseStorage.getInstance();
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static String Uid = FirebaseAuth.getInstance().getUid();
}
