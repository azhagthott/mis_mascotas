package com.desafiolatam.mismascotas.views.pet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.desafiolatam.mismascotas.R;
import com.desafiolatam.mismascotas.adapter.MyPetsListFirebaseAdapter;
import com.desafiolatam.mismascotas.adapter.PetUpdater;
import com.desafiolatam.mismascotas.data.CurrentUser;
import com.desafiolatam.mismascotas.presenters.PetManager;

public class MyPetsActivity extends AppCompatActivity implements PetUpdater {

    private RecyclerView mainRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyPetsListFirebaseAdapter adapter;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_pets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new MyPetsListFirebaseAdapter(new CurrentUser().uid(), MyPetsActivity.this, MyPetsActivity.this);

        mainRecyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPetDialog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findViews() {
        mainRecyclerView = (RecyclerView) findViewById(R.id.myPetsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void addPetDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View inflate = layoutInflater.inflate(R.layout.dialog_add_new_pet, null);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(inflate);

        dialog.setPositiveButton("guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText petNameEditText = (EditText) inflate.findViewById(R.id.petNameEditText);
                EditText petSpecieEditText = (EditText) inflate.findViewById(R.id.petSpecieEditText);
                EditText petGenderEditText = (EditText) inflate.findViewById(R.id.petGenderEditText);
                EditText petColorEditText = (EditText) inflate.findViewById(R.id.petColorEditText);

                String name = petNameEditText.getText().toString();
                String specie = petSpecieEditText.getText().toString();
                String gender = petGenderEditText.getText().toString();
                String color = petColorEditText.getText().toString();

                new PetManager(MyPetsActivity.this).create(name, specie, gender, color);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void update() {

    }

    @Override
    public void delete() {

        Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show();

    }


}
