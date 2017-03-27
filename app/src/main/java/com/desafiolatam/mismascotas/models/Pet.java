package com.desafiolatam.mismascotas.models;

/**
 * Created by fbarrios80 on 26-03-17.
 */

public class Pet {

    private String id;
    private String name;
    private String specie;
    private String gender;
    private String color;
    private String urlPhoto;
    private String remoteUrlPhoto;

    public Pet() {
    }

    public Pet(String id, String name, String specie, String gender, String color, String urlPhoto, String remoteUrlPhoto) {
        this.id = id;
        this.name = name;
        this.specie = specie;
        this.gender = gender;
        this.color = color;
        this.urlPhoto = urlPhoto;
        this.remoteUrlPhoto = remoteUrlPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getRemoteUrlPhoto() {
        return remoteUrlPhoto;
    }

    public void setRemoteUrlPhoto(String remoteUrlPhoto) {
        this.remoteUrlPhoto = remoteUrlPhoto;
    }
}
