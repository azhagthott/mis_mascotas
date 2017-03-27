package com.desafiolatam.mismascotas.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by fbarrios80 on 26-03-17.
 */

public class Nodes {

    private DatabaseReference root() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference users() {
        return root().child("users");
    }

    public DatabaseReference userByUid(String uid) {
        return users().child(uid);
    }

    public DatabaseReference pets() {
        return root().child("pets");
    }

    public DatabaseReference allPets() {
        return root().child("allPets");
    }

    public DatabaseReference petByUid(String userUid) {
        return pets().child(userUid);
    }

}
