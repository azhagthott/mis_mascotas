package com.desafiolatam.mismascotas.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.desafiolatam.mismascotas.R;
import com.desafiolatam.mismascotas.views.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private static final int RC_SIGN_IN = 1199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(
                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                        ))
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {

                new LoginValidation(LoginActivity.this).signIn();

                return;
            }
        }
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (resultCode == ErrorCodes.NO_NETWORK) {
            return;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void signedIn() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void error() {
        Toast.makeText(this, "sesion cerrada", Toast.LENGTH_SHORT).show();
    }
}
