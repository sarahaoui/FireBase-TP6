package com.example.pojectfirebase;

public class Produit {
    public String nom;
    public String id;
    public String description;
    public String image;

    public Produit(String id,String nom, String description, String image){
        this.nom=nom;
        this.description=description;
        this.image=image;
        this.id=id;

    }

    public Produit(){
    }

    public Produit(String id,String nom,String description){
        this.nom=nom;
        this.description=description;
        this.id=id;


    }

    public  String getId(){
        return id;
    }
    public  String getImage(){
        return image;
    }
    public  void setImage(String image){
        this.image=image;
    }

    public  String getDescription(){
        return description;
    }
    public  String getNom(){
        return nom;
    }
    public  void setId(String id){
        this.id=id;
    }
    public  void setNom(String nom){
        this.nom=nom;
    }
    public  void setDescription(String description){
        this.description=description;
    }}