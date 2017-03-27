package com.desafiolatam.mismascotas.views.pet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.desafiolatam.mismascotas.R;
import com.desafiolatam.mismascotas.data.CurrentUser;
import com.desafiolatam.mismascotas.data.Nodes;
import com.desafiolatam.mismascotas.models.Pet;
import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.desafiolatam.mismascotas.views.BaseActivity.RESIZE_PHOTO_PIXELS_PERCENTAGE;

public class PetProfileActivity extends AppCompatActivity implements PhotoCallback {

    private String path;
    private MagicalCamera magicalCamera;
    private PermissionGranted permissionGranted = new PermissionGranted(PetProfileActivity.this);
    private FloatingActionButton fab;

    private ImageView petPhoto;
    private Button saveButton;
    private EditText nameEditText, specieEditText, colorEditText, genderEditText;

    private DatabaseReference mDatabase;

    private String petName;
    private String key;
    private String petUrlLocalPhoto;
    private String publicUrlPhoto;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_pet_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        findViews();

        if (getIntent().getStringExtra("pet_name") != null && getIntent().getStringExtra("pet_key") != null) {

            petName = getIntent().getStringExtra("pet_name");
            key = getIntent().getStringExtra("pet_key");
            petUrlLocalPhoto = getIntent().getStringExtra("pet_url_photo");


            toolbar.setTitle(petName);
            toolbar_layout.setTitle(petName);
        }

        if (getIntent().getStringExtra("pet_url_photo").trim().length() != 0) {
            Picasso.with(this).load(petUrlLocalPhoto).fit().centerCrop().into(petPhoto);
        }


        readDatafromFirebase();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                magicalCamera.takePhoto();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChange();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findViews() {
        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELS_PERCENTAGE, permissionGranted);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        petPhoto = (ImageView) findViewById(R.id.petPhoto);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        specieEditText = (EditText) findViewById(R.id.specieEditText);
        colorEditText = (EditText) findViewById(R.id.colorEditText);
        genderEditText = (EditText) findViewById(R.id.genderEditText);

        saveButton = (Button) findViewById(R.id.saveButton);

    }

    private void checkPermissions() {
        permissionGranted = new PermissionGranted(this);
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            permissionGranted.checkAllMagicalCameraPermission();
        } else {
            permissionGranted.checkCameraPermission();
            permissionGranted.checkReadExternalPermission();
            permissionGranted.checkWriteExternalPermission();
            permissionGranted.checkLocationPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode, resultCode, data);
        petPhoto.setImageBitmap(magicalCamera.getPhoto());

        path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "Mascota", "Mis Mascotas App", MagicalCamera.JPEG, true);

        if (path != null) {
            path = "file://" + path;
            showPhoto(path);
            new UploadPetPhoto().toFirebaseStorage(path, petName);

        } else {
            //Picasso.with(MyPetsActivity.this).load(R.drawable.dogs_1).into(petCircularImageView);
        }
    }

    @Override
    public void takePhoto() {
        magicalCamera.takePhoto();
    }

    @Override
    public void showPhoto(String path) {
        Log.d("showPhoto: ", path);
    }

    private void saveChange() {

        Pet pet = new Pet();

        pet.setName(nameEditText.getText().toString());
        pet.setGender(genderEditText.getText().toString());
        pet.setSpecie(specieEditText.getText().toString());
        pet.setColor(colorEditText.getText().toString());
        pet.setId(key);
        pet.setUrlPhoto(path);
        pet.setRemoteUrlPhoto(publicUrlPhoto);

        new Nodes().petByUid(new CurrentUser().uid()).child(key).setValue(pet);
        new Nodes().allPets().child(key).setValue(pet);

        startActivity(new Intent(PetProfileActivity.this, MyPetsActivity.class));
        finish();

    }

    private void readDatafromFirebase() {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("pets").child(new CurrentUser().uid()).child(key).child("name").getValue().toString();
                String color = dataSnapshot.child("pets").child(new CurrentUser().uid()).child(key).child("color").getValue().toString();
                String gender = dataSnapshot.child("pets").child(new CurrentUser().uid()).child(key).child("gender").getValue().toString();
                String specie = dataSnapshot.child("pets").child(new CurrentUser().uid()).child(key).child("specie").getValue().toString();

                nameEditText.setText(name);
                specieEditText.setText(specie);
                colorEditText.setText(color);
                genderEditText.setText(gender);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

    }
}
