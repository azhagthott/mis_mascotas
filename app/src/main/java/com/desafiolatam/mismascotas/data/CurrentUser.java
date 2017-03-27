package com.desafiolatam.mismascotas.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by fbarrios80 on 26-03-17.
 */

public class CurrentUser {

    public FirebaseUser get() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String email() {
        return get().getEmail();
    }

    public String name() {
        return get().getDisplayName();
    }

    public String uid() {
        return get().getUid();
    }

    public String sanitizedEmail(String email) {
        return email.replace("@", "AT").replace(".", "DOT");
    }
}
