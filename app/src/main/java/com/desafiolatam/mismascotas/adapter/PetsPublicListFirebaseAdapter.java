package com.desafiolatam.mismascotas.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafiolatam.mismascotas.R;
import com.desafiolatam.mismascotas.data.Nodes;
import com.desafiolatam.mismascotas.models.Pet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class PetsPublicListFirebaseAdapter extends FirebaseRecyclerAdapter<Pet, PetsPublicListFirebaseAdapter.PetHolder> {

    public static final String PET_KEY = "pet_key";
    public static final String PET_NAME = "pet_name";
    public static final String PET_URL_PHOTO = "pet_url_photo";

    private PetUpdater update;
    private Context context;

    public PetsPublicListFirebaseAdapter(PetUpdater update, Context context) {
        super(Pet.class, R.layout.pet_public_list, PetHolder.class, new Nodes().allPets());
        this.update = update;
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final PetHolder holder, final Pet pet, final int position) {

        if (pet != null) {

            holder.setName(pet.getName());
            holder.setSpecie(pet.getSpecie());
            holder.setGender(pet.getGender());

            if (pet.getUrlPhoto().trim().length() != 0) {
                holder.setPhoto(context, pet.getUrlPhoto());
            }
        }
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
            Picasso.with(context).load(url).centerCrop().fit().into(petImageView);
        }
    }

}
