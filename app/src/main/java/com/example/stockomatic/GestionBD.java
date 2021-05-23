package com.example.stockomatic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GestionBD {

    // instanciation des valeurs
    SQLiteDatabase maBase;
    BDHelper bdHelper;

    // constructeur de l'instance de GestionBD
    public GestionBD(Context c) {
        this.bdHelper = new BDHelper(c, "StockOmatic", null, 1);
    }

    // methode pour ouvrir la sgbd
    public void open() {
        maBase = bdHelper.getWritableDatabase();
    }

    // methode pour fermer la sgbd
    public void close() {
        maBase.close();
    }

    // methode pour supprimer les tables de la sgbd
    public void videLesTables() {
        // fonction pour supprimer la tables qteStock
        maBase.delete("qteStock",null,null);
        // fonction pour supprimer la tables produit
        maBase.delete("produit",null,null);
        // fonction pour supprimer la tables inventaire
        maBase.delete("inventaire",null,null);
        // fonction pour supprimer la tables categorie
        maBase.delete("categorie",null,null);
    }

    // methode pour supprimer la table produit
    public void videLaTableProduit() { maBase.delete("produit",null,null); }

    // methode pour supprimer la table categorie
    public void videLaTableCategorie() {
        maBase.delete("categorie",null,null);
    }

    // methode pour supprimer la table inventaire
    public void videLaTableInventaire() {
        maBase.delete("inventaire",null,null);
    }

    // methode pour supprimer la table qteStock
    public void videLaTableQteStock() {
        maBase.delete("qteStock",null,null);
    }

    // methodes pour le CRUD produit
    // methode pour recuperer les libelle de la table produit
    public ArrayList<String> donneLesProduits() {
        // instanciation d'une liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // requette pour recuperer les libelle de la table produit
        String req = "select libelleProduit " +
                "from produit " +
                "order by libelleProduit asc";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        while (c.moveToNext()){
            // ajout du libelle dans la liste
            listeSQL.add(c.getString(0));
        }
        // return de la liste
        return listeSQL;
    }

    // methode pour ajouter une ligne dans la table produit
    public void ajouteProduit(Produits leProduit) {
        // instanciation du ContentValues
        ContentValues val = new ContentValues();
        // ajout des valeurs dans le ContentValues
        val.put("idProduit",leProduit.getId());
        val.put("idCategorie",leProduit.getIdCateg());
        val.put("libelleProduit",leProduit.getLibelleProduit());
        val.put("prix",leProduit.getPrix());
        val.put("description",leProduit.getDescription());
        // fonction pour ajouter une ligne dans la tables produit
        maBase.insert("produit",null,val);
    }

    // methode pour supprimer une ligne dans la table produit
    public void supprimeProduit(String lIdProd) {
        // requette pour supprimmer la ligne de la table produit
        maBase.execSQL("DELETE " +
                "FROM produit " +
                "WHERE idProduit = \"" + lIdProd + "\";");
    }

    // methode pour modifier une ligne dans la table produit
    public void ModifProduit(Produits leProduit, String lIdProduitOriginal) {
        // requette pour modifier la ligne de la table produit
        maBase.execSQL("UPDATE produit " +
                "SET idProduit = \""+leProduit.getId()+"\", " +
                "libelleProduit = \""+leProduit.getLibelleProduit()+"\", " +
                "prix = "+leProduit.getPrix()+", " +
                "description = \""+leProduit.getDescription()+"\", " +
                "idCategorie = \""+leProduit.getIdCateg()+"\" " +
                "WHERE idProduit = \""+lIdProduitOriginal+"\"");
    }

    // methode pour recuperer la reference d'une ligne de la table produit
    public String getIdProdProduit(String leLibelle) {
        // requette pour recuperer la reference
        String req = "SELECT idProduit " +
                "FROM produit " +
                "WHERE libelleProduit = \""+leLibelle+"\";";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("idProduit"));
        // return du resultat
        return res;
    }

    // methode pour recuperer le libelle d'une ligne de la table produit
    public String getLibelleProduit(String idProd) {
        // requette pour recuperer le libelle
        String req = "SELECT libelleProduit " +
                "FROM produit " +
                "WHERE idProduit = \""+idProd+"\";";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("libelleProduit"));
        // return du resultat
        return res;
    }

    // methode pour recuperer la description d'une ligne de la table produit
    public String getDescriptionProduit(String leLibelle) {
        // requette pour recuperer la description
        String req = "SELECT description " +
                "FROM produit " +
                "WHERE libelleProduit = \""+leLibelle+"\";";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("description"));
        // return du resultat
        return res;
    }

    // methode pour recuperer le prix d'une ligne de la table produit
    public double getPrixProduit(String leLibelle) {
        // requette pour recuperer le prix
        String req = "SELECT prix " +
                "FROM produit " +
                "WHERE libelleProduit = \""+leLibelle+"\";";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        double res = c.getDouble(c.getColumnIndex("prix"));
        // return du resultat
        return res;
    }

    // methode pour recuperer l'id de la catégorie d'une ligne de la table produit
    public int getIdCategProduitBrut(String leLibelle) {
        // requette pour recuperer l'id de la catégorie d'une ligne de la table produit
        String req = "SELECT idCategorie " +
                "FROM produit " +
                "WHERE libelleProduit = \""+leLibelle+"\";";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        int res = c.getInt(c.getColumnIndex("idCategorie"));
        // return du resultat
        return res;
    }

    // methode pour recuperer le libelle de la catégorie d'une ligne de la table categorie
    public String getIdCategProduit(String leLibelle) {
        // requette pour recuperer le libelle de la catégorie
        String req = "SELECT categorie.libelleCateg " +
                "AS libelleCategorie " +
                "FROM produit " +
                "INNER JOIN categorie " +
                "ON produit.idCategorie = categorie.idCategorie " +
                "WHERE produit.libelleProduit = \""+leLibelle+"\";";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("libelleCategorie"));
        // return du resultat
        return res;
    }

    // Methodes pour le CRUD Categorie
    // methode pour recuperer les libelle de la table categorie
    public ArrayList<String> donneLesCategorie() {
        // instanciation d'une liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // requette pour recuperer les libelle de la table categorie
        String req = "select libelleCateg " +
                "from categorie " +
                "order by libelleCateg asc";
        // instanciation d'un curseur
        Cursor c = maBase.rawQuery(req,null);
        while (c.moveToNext()){
            // ajout du libelle dans la liste
            listeSQL.add(c.getString(0));
        }
        // return de la liste
        return listeSQL;
    }

    // methode pour ajouter une ligne de la table categorie
    public void ajouteCategorie(Categories laCategorie) {
        // instanciation du ContentValues
        ContentValues val = new ContentValues();
        // ajout des valeurs dans le ContentValues
        val.put("idCategorie",laCategorie.getId());
        val.put("libelleCateg",laCategorie.getLibelle());
        val.put("typeVente",laCategorie.getTypeVente());
        val.put("typeCond",laCategorie.getTypeCond());
        // fonction pour ajouter une ligne dans la tables categorie
        maBase.insert("categorie",null,val);
    }

    // methode pour modifier une ligne de la table categorie
    public void ModifCategorie(Categories laCategorie, String lIdCategOriginal) {
        // requette pour ajouter une ligne de la table categorie
        maBase.execSQL("UPDATE categorie SET idCategorie = \""+laCategorie.getId()+"\", " +
                "libelleCateg = \""+laCategorie.getLibelle()+"\", " +
                "typeVente = \""+laCategorie.getTypeVente()+"\", " +
                "typeCond = \""+laCategorie.getTypeCond()+"\" " +
                "WHERE idCategorie = "+lIdCategOriginal+"");
    }

    // methode pour supprimer une ligne de la table categorie
    public void supprimeCategorie(String lIdCateg) {
        // fonction pour supprimer une ligne de la table categorie
        maBase.execSQL("DELETE " +
                "FROM categorie " +
                "WHERE idCategorie = \"" + lIdCateg + "\";");
    }

    // methode pour recuperer l'id de la categorie d'une ligne de la table categorie
    public String getidCategCategorie(String leLibelle) {
        // requette pour recuperer l'id de la categorie d'une ligne de la table categorie<
        String req = "SELECT idCategorie " +
                "FROM categorie " +
                "WHERE libelleCateg = \""+leLibelle+"\";";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation de l'id de la categorie
        String res = c.getString(c.getColumnIndex("idCategorie"));
        // return du resultat
        return res;
    }

    // methode pour recuperer le type de vente d'une ligne de la table categorie
    public String getTypeVente(String leLibelle) {
        // requette pour recuperer le type de vente d'une ligne de la table categorie
        String req = "SELECT typeVente " +
                "FROM categorie " +
                "WHERE libelleCateg = \""+leLibelle+"\";";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du type de vente de la categorie
        String res = c.getString(c.getColumnIndex("typeVente"));
        // return du resultat
        return res;
    }

    // methode pour recuperer le type de conditionnement d'une ligne de la table categorie
    public String getTypeCond(String leLibelle) {
        // requette pour recuperer le type de conditionnement d'une ligne de la table categorie
        String req = "SELECT typeCond " +
                "FROM categorie " +
                "WHERE libelleCateg = \""+leLibelle+"\";";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du type de conditionnement de la categorie
        String res = c.getString(c.getColumnIndex("typeCond"));
        // return du resultat
        return res;
    }

    // methode pour le crud de inventaire
    // methode pour recuperer les libelle de la table inventaire
    public ArrayList<String> donneLesInventaires() {
        // instanciation de la liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // requette pour recuperer les libelle de la table inventaire
        String req = "select libelleInventaire " +
                "from inventaire " +
                "order by libelleInventaire asc";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        while (c.moveToNext()){
            // ajout des valeurs dans la liste
            listeSQL.add(c.getString(0));
        }
        // return de la liste
        return listeSQL;
    }

    // methode pour ajouter une ligne de la table inventaire
    public void ajouteInventaire(Inventaires lInventaire) {
        // instanciation du ContentValues
        ContentValues val = new ContentValues();
        // ajout des valeurs dans le ContentValues
        val.put("idInventaire",lInventaire.getIdInventaire());
        val.put("libelleInventaire",lInventaire.getLibelleInventaire());
        // fonction pour ajouter une ligne dans la tables inventaire
        maBase.insert("inventaire",null,val);
    }

    // methode pour modifier une ligne de la table inventaire
    public void ModifInventaire(Inventaires lInvent, String lIdInventaireOriginal) {
        // requette pour modifier une ligne de la table inventaire
        maBase.execSQL("UPDATE inventaire SET idInventaire = \""+lInvent.getIdInventaire()+"\", " +
                "libelleInventaire = \""+lInvent.getLibelleInventaire()+"\" " +
                "WHERE idInventaire = \""+lIdInventaireOriginal+"\"");
    }

    // methode pour supprimer une ligne de la table inventaire
    public void supprimeInventaire(String lIdInvent) {
        // requette pour supprimer une ligne de la table inventaire
        maBase.execSQL("DELETE " +
                "FROM inventaire " +
                "WHERE idInventaire = \"" + lIdInvent + "\";");
    }

    // methode pour recuperer l'id d'une ligne de la table inventaire
    public String getIdinventaireInventaire(String leLibelle) {
        // requette pour recuperer le'id d'une ligne de la table inventaire
        String req = "SELECT idInventaire " +
                "FROM inventaire " +
                "WHERE libelleInventaire = \""+leLibelle+"\";";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("idInventaire"));
        // return du resultat
        return res;
    }

    public String getLibelleInventaire(String id){
        // requette pour recuperer le'id d'une ligne de la table inventaire
        String req = "SELECT libelleInventaire " +
                "FROM inventaire " +
                "WHERE idInventaire= \""+id+"\";";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("libelleInventaire"));
        // return du resultat
        return res;
    }

    //methode pour le CRUD de qteStock
    // methode pour recuperer les libelle des produit de la table qteStock
    public ArrayList<String> donneLesQteStock(String leLibelle) {
        // instanciation de la liste
        ArrayList<String> listeSQL = new ArrayList<String>();
        // requette pour recuperer les libelle des produit
        String req = "select produit.libelleProduit " +
                "from produit " +
                "inner join qteStock " +
                "on produit.idProduit = qteStock.idProduit " +
                "inner join inventaire " +
                "on qteStock.idInventaire = inventaire.idInventaire " +
                "where inventaire.libelleInventaire = \""+leLibelle+"\" " +
                "order by produit.libelleProduit asc;";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        while (c.moveToNext()){
            // ajout de la valeur dans la liste
            listeSQL.add(c.getString(0));
        }
        // return de la liste
        return listeSQL;
    }

    // methode pour ajouter une ligne de la table qteStock
    public void ajouteQteStock(ProdInvents leProdInvents) {
        // instanciation du ContentValues
        ContentValues val = new ContentValues();
        // ajout des valeurs dans le ContentValues
        val.put("idProduit",leProdInvents.getIdProduit());
        val.put("idInventaire",leProdInvents.getIdInventaire());
        val.put("limiteStockAlert",leProdInvents.getLimiteStockAlert());
        val.put("qteStock",leProdInvents.getQteStock());
        // fonction pour ajouter la ligne dans la table qteStock
        maBase.insert("qteStock",null,val);
    }

    // methode pour modifier une ligne de la table qteStock
    public void ModifQteStock(ProdInvents leProdInvent, String idProduitOriginal, String idInventaireOriginal) {
        // requette pour modifier une de la table qteStock
        maBase.execSQL("UPDATE qteStock " +
                "SET idProduit = \""+leProdInvent.getIdProduit()+"\", " +
                "idInventaire = \""+leProdInvent.getIdInventaire()+"\", " +
                "limiteStockAlert = "+leProdInvent.getLimiteStockAlert()+", " +
                "qteStock = "+leProdInvent.getQteStock()+" " +
                "WHERE idProduit = \""+idProduitOriginal+"\" " +
                "AND idInventaire = \""+idInventaireOriginal+"\"");
    }

    // methode pour supprimer une ligne de la table qteStock
    public void supprimeQteStock(String idProduit, String leLibelleInventaire) {
        // requette pour supprimer une ligne de la table qteStock
        maBase.execSQL("DELETE " +
                "FROM qteStock " +
                "WHERE idProduit = \"" + idProduit + "\" " +
                "AND idInventaire = \"" + getIdinventaireInventaire(leLibelleInventaire) + "\"");
    }

    // methode pour recuperer l'id du produit d'une ligne de la table qteStock
    public String getIdProdQteStock(String leLibelle, String idInventaire) {
        // requette pour recuperer l'id du produit d'une ligne de la table qteStock
        String req = "SELECT qteStock.idProduit " +
                "AS lIdProd " +
                "FROM qteStock " +
                "INNER JOIN produit " +
                "ON qteStock.idProduit = produit.idProduit " +
                "WHERE qteStock.idInventaire = \""+idInventaire+"\" " +
                "AND produit.libelleProduit = \""+leLibelle+"\" ;";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("lIdProd"));
        // return du resultat
        return res;
    }

    // methode pour recuperer la limite de stock d'alerte d'une ligne de la table qteStock
    public int getLimiteQteStock(String leLibelle, String idInventaire) {
        // requette pour recuperer la limite de stock d'alerte d'une ligne de la table qteStock
        String req = "SELECT qteStock.limiteStockAlert " +
                "AS laLimite " +
                "FROM qteStock " +
                "INNER JOIN produit " +
                "ON qteStock.idProduit = produit.idProduit " +
                "WHERE qteStock.idInventaire = \""+idInventaire+"\" " +
                "AND produit.libelleProduit = \""+leLibelle+"\" ;";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        int res = c.getInt(c.getColumnIndex("laLimite"));
        // return du resultat
        return res;
    }

    // methode pour recuperer la quantité d'une ligne de la table qteStock
    public int getQuantiteQteStock(String leLibelle, String idInventaire) {
        // requette pour recuperer la quantité d'une ligne de la table qteStock
        String req = "SELECT qteStock " +
                "AS laQuantite " +
                "FROM qteStock " +
                "INNER JOIN produit " +
                "ON qteStock.idProduit = produit.idProduit " +
                "WHERE qteStock.idInventaire = \""+idInventaire+"\" " +
                "AND produit.libelleProduit = \""+leLibelle+"\" ;";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        int res = c.getInt(c.getColumnIndex("laQuantite"));
        // return du resultat
        return res;
    }

    // methode pour recuperer l'id de l'inventaire d'une ligne de la table qteStock
    public String getIdInventaireQteStock(String leLibelle, String leLibelleInventaire) {
        // requette pour recuperer l'id de l'inventaire d'une ligne de la table qteStock
        String req = "SELECT qteStock.idInventaire " +
                "AS lIdInvent " +
                "FROM inventaire " +
                "INNER JOIN qteStock " +
                "ON inventaire.idInventaire = qteStock.idInventaire " +
                "INNER JOIN produit " +
                "ON qteStock.idProduit = produit.idProduit " +
                "WHERE produit.libelleProduit = \""+leLibelle+"\" " +
                "AND inventaire.libelleInventaire = \""+leLibelleInventaire+"\"";
        // instanciation du curseur
        Cursor c = maBase.rawQuery(req,null);
        c.moveToNext();
        // recuperation du resultat
        String res = c.getString(c.getColumnIndex("lIdInvent"));
        // return du resultat
        return res;
    }

}