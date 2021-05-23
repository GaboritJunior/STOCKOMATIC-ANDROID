package com.example.stockomatic;

import java.util.ArrayList;

public class Inventaires {
    // instanciation des valeurs
    private String id, libelleInventaire;
    private static ArrayList<Inventaires> lesInventaires = new ArrayList<Inventaires>();

    // contructeur de l'instance d'inventaire
    public Inventaires(String id, String libelleInventaire){
        this.id = id;
        this.libelleInventaire = libelleInventaire;
    }

    // getteur de id
    public String getIdInventaire(){
        return id;
    }

    // getteur de libelle
    public String getLibelleInventaire(){
        return libelleInventaire;
    }

    // getteur de la liste d'inventaire
    public static ArrayList<Inventaires> getLesInventaires() {
        return lesInventaires;
    }

    // setteur de id
    public void setId(String id) {
        this.id = id;
    }

    // setteur de libelle
    public void setLibelleInventaire(String libelleInventaire) {
        this.libelleInventaire = libelleInventaire;
    }

    // ajout d'une instance d'inventaire dans la liste
    public void ajoutInventaire(){
        lesInventaires.add(this);
    }

    // suppresion d'une instance d'inventaire dans la liste
    public void supprInventaire(){
        lesInventaires.remove(this);
    }

    // affichage des données de la liste
    public static String afficheInventaire(){
        String res = "Debut de la liste : \n\n";
        for (Inventaires lInventaire : lesInventaires){
            res = res + "Réference inventaire : " + lInventaire.getIdInventaire() + " \n" +
                    "Libelle : " + lInventaire.getLibelleInventaire() + " \n" +
                    "\n";
        }
        res = res + "Fin de la liste";
        return res;
    }

    // affichage de l'instance de Inventaire
    @Override
    public String toString() {
        return  "Réference inventaire : " + getIdInventaire() + " \n" +
                "Libelle : " + getLibelleInventaire() + " \n" +
                "\n";
    }
}
