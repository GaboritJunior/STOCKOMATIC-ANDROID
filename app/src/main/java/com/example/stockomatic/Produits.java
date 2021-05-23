package com.example.stockomatic;

import java.util.ArrayList;

public class Produits {

    // instanciation des valeurs
    private String id, libelleProduit, description, idcateg;
    private Categories idcategorie;
    private double prix;
    private static ArrayList<Produits> lesProduits = new ArrayList<Produits>();

    // contructeur de l'instance de produit
    public Produits(String id, String libelleProduit, double prix, String description, String idcateg){
        this.id = id;
        this.libelleProduit = libelleProduit;
        this.prix = prix;
        this.description = description;
        this.idcateg = idcateg;
    }

    // contructeur de l'instance de produit
    public Produits(String id, String libelleProduit, double prix, String description, Categories idcategorie){
        this.id = id;
        this.libelleProduit = libelleProduit;
        this.prix = prix;
        this.description = description;
        this.idcategorie = idcategorie;
    }

    // getteur de id
    public String getId(){
        return id;
    }

    // getteur de libelle
    public String getLibelleProduit(){
        return libelleProduit;
    }

    // getteur de prix
    public double getPrix(){
        return prix;
    }

    // getteur de description
    public String getDescription(){
        return description;
    }

    // getteur de id categ
    public String getIdCateg(){
        return idcateg;
    }

    // getteur de id categorie
    public Categories getIdCategorie(){
        return idcategorie;
    }

    // getteur de la liste d'inventaire
    public static  ArrayList<Produits> getLesProduits() {
        return lesProduits;
    }

    // setteur de id
    public void setId (String lId){
        this.id = lId;
    }

    // setteur de libelle
    public void setLibelleProduit(String leLibelle){
        this.libelleProduit = leLibelle;
    }

    // setteur de prix
    public void setPrix(double lePrix){
        this.prix = lePrix;
    }

    // setteur de description
    public void setDescription(String laDescription) {
        this.description = laDescription;
    }

    // setteur de id categ
    public void setIdcateg(String lIdCateg) {
        this.idcateg = lIdCateg;
        this.idcategorie = null;
    }

    // setteur de id categorie
    public void setIdcategorie(Categories lIdCategorie) {
        this.idcategorie = lIdCategorie;
        this.idcateg = null;
    }

    // ajout d'une instance de produit dans la liste
    public void ajoutProduit(){
        lesProduits.add(this);
    }

    // suppresion d'une instance de produit dans la liste
    public void supprProduit(){
        lesProduits.remove(this);
    }

    // affichage des données de la liste
    public static String afficheProduit(){
        String res = "Debut de la liste : \n\n";
        for (Produits leProduit : lesProduits){
            res = res + "Réference Produit : " + leProduit.getId() + " \n" +
                    "Libelle : " + leProduit.getLibelleProduit() + " \n" +
                    "Prix : " + leProduit.getPrix() + " \n" +
                    "Description : " + leProduit.getDescription() + " \n";
            if (leProduit.getIdCateg() == null){
                res = res + "Categorie : " + leProduit.getIdCategorie().getLibelle() + " \n";
            }
            else{
                res = res + "Categorie : " + leProduit.getIdCateg() + " \n";
            }
            res = res + "\n";
        }
        res = res + "Fin de la liste";
        return res;
    }

    // affichage de l'instance de produit
    public String toString(){
        String res = "Réference Produit : " + getId() + " \n" +
                "Libelle : " + getLibelleProduit() + " \n" +
                "Prix : " + getPrix() + " \n" +
                "Description : " + getDescription() + " \n";
        if (getIdCateg() == null){
            res = res + "Categorie : " + getIdCategorie().getLibelle() + " \n";
        }
        else{
            res = res + "Categorie : " + getIdCateg() + " \n";
        }
        res = res + "\n";
        return res;
    }
}
