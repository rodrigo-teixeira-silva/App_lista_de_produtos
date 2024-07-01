package com.example.controledeprodutos.helper;

import com.google.firebase.auth.FirebaseAuth;

public class FireBaseHelper {

    private static FirebaseAuth auth;

    public static FirebaseAuth getAuth(){
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
