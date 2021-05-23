package com.example.stockomatic;

import java.util.ArrayList;

public class Categories {
    // instanciation des valeurs
    private String id, libelle, typeVente, typeCond;
    private static ArrayList<Categories> lesCategories = new ArrayList<Categories>();

    // constructeur de l'instance de categorie
    public Categories(String id, String libelle, String typeVente, String typeCond){
        this.id = id;
        this.libelle = libelle;
        this.typeVente = typeVente;
        this.typeCond = typeCond;
    }

    // getteur de id
    public String getId(){
        return  id;
    }

    // getteur de libelle
    public String getLibelle(){
        return libelle;
    }

    // getteur de type de vente
    public String getTypeVente(){
        return typeVente;
    }

    // getteur de type de conditionnement
    public String getTypeCond(){
        return typeCond;
    }

    // getteur de la liste de catégorie
    public static ArrayList<Categories> getLesCategories() {
        return lesCategories;
    }

    // setteur de id
    public void setId(String lId){
        this.id = lId;
    }

    // setteur de libelle
    public void setLibelle(String leLibelle){
        this.libelle = leLibelle;
    }

    // setteur de typeVente
    public void setTypeVente(String leTypeVente){
        this.typeVente = leTypeVente;
    }

    // setteur de typeCond
    public void setTypeCond(String leTypeCond){
        this.typeCond = leTypeCond;
    }

    // ajout d'une instance de categorie dans la liste
    public void ajoutCategorie(){
        lesCategories.add(this);
    }

    // suppresion d'une instance de categorie dans la liste
    public void supprCategorie(){
        lesCategories.remove(this);
    }

    // affichage des données de la liste
    public static String afficheCategorie(){
        String res = "Debut de la liste : \n\n";
        for (Categories lacateg : lesCategories){
            res = res + "Réference catégorie : " + lacateg.getId() + " \n" +
                    "Libelle : " + lacateg.getLibelle() + " \n" +
                    "Type de vente : " + lacateg.getTypeVente() + " \n" +
                    "Type de conditionnement  : " + lacateg.getTypeCond() + " \n" +
                    "\n";
        }
        res = res + "Fin de la liste";
        return res;
    }

    // affichage de l'instance de categorie
    @Override
    public String toString() {
        return  "Réference catégorie : " + getId() + " \n" +
                "Libelle : " + getLibelle() + " \n" +
                "Type de vente : " + getTypeVente() + " \n" +
                "Type de conditionnement  : " + getTypeCond() + " \n" +
                "\n";
    }

}
