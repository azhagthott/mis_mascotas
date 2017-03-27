package com.desafiolatam.mismascotas.views.login;

import android.support.annotation.NonNull;

import com.desafiolatam.mismascotas.data.Nodes;
import com.desafiolatam.mismascotas.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by fbarrios80 on 26-03-17.
 */

public class LoginValidation {

    private LoginCallback callback;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    public LoginValidation(LoginCallback callback) {
        this.callback = callback;
    }

    public void signIn() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser newUser = firebaseAuth.getCurrentUser();

                if (newUser != null) {

                    User user = new User(newUser.getDisplayName(), newUser.getUid(), newUser.getEmail());
                    DatabaseReference reference = new Nodes().userByUid(newUser.getUid());
                    reference.setValue(user);
                    callback.signedIn();
                } else {
                    callback.error();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }
}
