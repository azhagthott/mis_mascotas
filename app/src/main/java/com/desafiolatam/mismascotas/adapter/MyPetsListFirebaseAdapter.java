package com.desafiolatam.mismascotas.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafiolatam.mismascotas.R;
import com.desafiolatam.mismascotas.data.Nodes;
import com.desafiolatam.mismascotas.models.Pet;
import com.desafiolatam.mismascotas.views.pet.PetProfileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class MyPetsListFirebaseAdapter extends FirebaseRecyclerAdapter<Pet, MyPetsListFirebaseAdapter.PetHolder> {

    public static final String PET_KEY = "pet_key";
    public static final String PET_NAME = "pet_name";
    public static final String PET_URL_PHOTO = "pet_url_photo";

    private PetUpdater update;
    private Context context;
    private String uid;

    public MyPetsListFirebaseAdapter(String uid, PetUpdater update, Context context) {
        super(Pet.class, R.layout.pet_public_list, PetHolder.class, new Nodes().petByUid(uid));
        this.update = update;
        this.context = context;
        this.uid = uid;
    }

    @Override
    protected void populateViewHolder(final PetHolder holder, final Pet pet, final int position) {

        holder.setName(pet.getName());
        holder.setSpecie(pet.getSpecie());
        holder.setGender(pet.getGender());

        if (pet.getUrlPhoto().trim().length() != 0) {
            holder.setPhoto(context, pet.getUrlPhoto());
        }

        holder.petSelectorCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PetProfileActivity.class);
                intent.putExtra(PET_KEY, pet.getId());
                intent.putExtra(PET_NAME, pet.getName());
                intent.putExtra(PET_URL_PHOTO, pet.getUrlPhoto());
                v.getContext().startActivity(intent);
            }
        });

        holder.petSelectorCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("¿desea aliminar la mascota?")
                        .setMessage("Si elimina la mascota de la lista, tambien morirá en la vida real")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String key = pet.getId();

                                new Nodes().petByUid(uid).child(key).removeValue();
                                new Nodes().allPets().child(key).removeValue();

                                notifyDataSetChanged();

                                update.delete();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

                return true;
            }
        });

    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        update.update();
    }

    public static class PetHolder extends RecyclerView.ViewHolder {

        private TextView petNameTextView;
        private TextView specieTextView;
        private TextView genderTextView;
        private CardView petSelectorCardView;
        private ImageView petImageView;

        public PetHolder(View view) {
            super(view);
            petNameTextView = (TextView) view.findViewById(R.id.petNameTextView);
            specieTextView = (TextView) view.findViewById(R.id.specieTextView);
            genderTextView = (TextView) view.findViewById(R.id.genderTextView);
            petImageView = (ImageView) view.findViewById(R.id.petImageView);
            petSelectorCardView = (CardView) view.findViewById(R.id.petSelectorCardView);
        }

        public void setName(String name) {
            petNameTextView.setText(name);
        }

        public void setSpecie(String specie) {
            specieTextView.setText(specie);
        }

        public void setGender(String gender) {
            genderTextView.setText(gender);
        }

        public void setPhoto(Context context, String url) {
            if (url != null) {
                Picasso.with(context).load(url).centerCrop().fit().into(petImageView);
            }
        }
    }

}
