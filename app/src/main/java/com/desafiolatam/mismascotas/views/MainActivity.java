package com.desafiolatam.mismascotas.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.desafiolatam.mismascotas.R;
import com.desafiolatam.mismascotas.adapter.PetUpdater;
import com.desafiolatam.mismascotas.adapter.PetsPublicListFirebaseAdapter;
import com.desafiolatam.mismascotas.views.login.LoginActivity;
import com.desafiolatam.mismascotas.views.pet.MyPetsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements PetUpdater {

    private RecyclerView mainRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PetsPublicListFirebaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new PetsPublicListFirebaseAdapter(MainActivity.this, MainActivity.this);

        mainRecyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerView.setAdapter(adapter);

    }

    private void findViews() {
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.action_my_ptes:
                startActivity(new Intent(MainActivity.this, MyPetsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
