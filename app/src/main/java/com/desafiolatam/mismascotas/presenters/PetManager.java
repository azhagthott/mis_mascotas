package com.desafiolatam.mismascotas.presenters;

import com.desafiolatam.mismascotas.adapter.PetUpdater;
import com.desafiolatam.mismascotas.data.CurrentUser;
import com.desafiolatam.mismascotas.data.Nodes;
import com.desafiolatam.mismascotas.models.Pet;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by fbarrios80 on 27-03-17.
 */

public class PetManager {

    private PetUpdater callback;
    String uid = new CurrentUser().uid();

    public PetManager(PetUpdater callback) {
        this.callback = callback;
    }

    public void create(String name, String specie, String gender, String color) {

        DatabaseReference refPetByUid = new Nodes().petByUid(uid);
        DatabaseReference refAllPet = new Nodes().allPets();

        String key = refPetByUid.push().getKey();
        String url = "";

        Pet pet = new Pet(key, name, specie, gender, color, url, url);

        refPetByUid.child(key).setValue(pet);
        refAllPet.child(key).setValue(pet);

        callback.update();

    }

}
