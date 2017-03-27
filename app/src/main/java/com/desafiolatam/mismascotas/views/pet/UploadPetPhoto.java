package com.desafiolatam.mismascotas.views.pet;

import android.net.Uri;
import android.util.Log;

import com.desafiolatam.mismascotas.data.CurrentUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.desafiolatam.mismascotas.views.BaseActivity.BASE_URL_FIREBASE;

/**
 * Created by fbarrios80 on 27-03-17.
 */

public class UploadPetPhoto {

    public static final String TAG = "TAG:::: ";

    public void toFirebaseStorage(String path, String petName) {

        final CurrentUser currentUser = new CurrentUser();

        String fileName = petName;
        String extension = ".jpeg";
        String photoName = fileName + extension;
        String folder = currentUser.sanitizedEmail(currentUser.email()) + "/";

        String refUrl = BASE_URL_FIREBASE + photoName;

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");

                String url = fullUrl[0];

                Log.d(TAG, "onSuccess: " + url);

            }
        });
    }
}
