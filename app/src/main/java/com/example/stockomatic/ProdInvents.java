package com.example.stockomatic;

import java.util.ArrayList;

public class ProdInvents {

    // instanciation des valeurs
    private String idProduit, idInventaire;
    private Produits idProd;
    private Inventaires idInvent;
    private int limiteStockAlert, qteStock;
    private static ArrayList<ProdInvents> lesProdInvents = new ArrayList<ProdInvents>();

    // contructeur de l'instance de prodInvent
    public ProdInvents(String lIdProduit, String lIdInvent, int laLimite, int qteStock) {
        this.idProduit = lIdProduit;
        this.idInventaire = lIdInvent;
        this.limiteStockAlert = laLimite;
        this.qteStock = qteStock;
    }

    // contructeur de l'instance de prodInvent
    public ProdInvents(Produits idProduit, Inventaires idInvent, int laLimite, int qteStock) {
        this.idProd = idProduit;
        this.idInvent = idInvent;
        this.limiteStockAlert = laLimite;
        this.qteStock = qteStock;
    }

    // contructeur de l'instance de prodInvent
    public ProdInvents(Produits idProd, String idInventaire, int laLimite, int qteStock) {
        this.idProd = idProd;
        this.idInventaire = idInventaire;
        this.limiteStockAlert = laLimite;
        this.qteStock = qteStock;
    }

    // contructeur de l'instance de prodInvent
    public ProdInvents(String idProduit, Inventaires idInvent, int laLimite, int qteStock) {
        this.idProduit = idProduit;
        this.idInvent = idInvent;
        this.limiteStockAlert = laLimite;
        this.qteStock = qteStock;
    }

    // getteur de idProduit
    public String getIdProduit() {
        return idProduit;
    }

    // getteur de idInventaire
    public String getIdInventaire() {
        return idInventaire;
    }

    // getteur de limite
    public int getLimiteStockAlert() {
        return limiteStockAlert;
    }

    // getteur de qteStock
    public int getQteStock() {
        return qteStock;
    }

    // getteur de idProd
    public Produits getIdProd() {
        return idProd;
    }

    // getteur de idInvent
    public Inventaires getIdInvent() {
        return idInvent;
    }

    // getteur de lesProdInvents
    public static ArrayList<ProdInvents> getLesProdInvents() {
        return lesProdInvents;
    }

    // setteur de ipProduit
    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
        this.idProd = null;
    }

    // setteur de idInventaire
    public void setIdInventaire(String idInventaire) {
        this.idInventaire = idInventaire;
        this.idInvent = null;
    }

    // setteur de limite
    public void setLimiteStockAlert(int limiteStockAlert) {
        this.limiteStockAlert = limiteStockAlert;
    }

    // setteur de qteStock
    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    // setteur de ipProd
    public void setIdProd(Produits idProd) {
        this.idProd = idProd;
        this.idProduit = null;
    }

    // setteur de idInvent
    public void setIdInvent(Inventaires idInvent) {
        this.idInvent = idInvent;
        this.idInventaire = null;
    }

    // ajout d'une instance de ProdInvents dans la liste
    public void ajoutProdInvent(){
        lesProdInvents.add(this);
    }

    // suppresion d'une instance de ProdInvents dans la liste
    public void supprProdInvent(){
        lesProdInvents.remove(this);
    }

    // affichage des données de la liste
    public static String afficheProdInvent(){
        String res = "Debut de la liste : \n\n";
        for (ProdInvents leProdInvent : lesProdInvents){
            if (leProdInvent.getIdProduit() == null){
                res = res + "Référence Produit : " + leProdInvent.getIdProd().getId() + " \n";
            }
            else{
                res = res + "Référence Produit : " + leProdInvent.getIdProduit() + " \n";
            }
            if (leProdInvent.getIdInventaire() == null){
                res = res + "Référence Inventaire : " + leProdInvent.getIdInvent().getIdInventaire() + " \n";
            }
            else{
                res = res + "Référence Inventaire : " + leProdInvent.getIdInventaire() + " \n";
            }
            res = res + "Limite Stock d'Alerte : " + leProdInvent.getLimiteStockAlert() + " \n" +
                    "Quantité en stock : " + leProdInvent.getQteStock() + " \n" +
                    "\n";
        }
        res = res + "Fin de la liste";
        return res;
    }

    public String toString (){
        String res = "";
        if (getIdProduit() == null){
            res = res + "Référence Produit : " + getIdProd().getId() + " \n";
        }
        else{
            res = res + "Référence Produit : " + getIdProduit() + " \n";
        }
        if (getIdInventaire() == null){
            res = res + "Référence Inventaire : " + getIdInvent().getIdInventaire() + " \n";
        }
        else{
            res = res + "Référence Inventaire : " + getIdInventaire() + " \n";
        }
        res = res + "Limite Stock d'Alerte : " + getLimiteStockAlert() + " \n" +
                "Quantité en stock : " + getQteStock() + " \n" +
                "\n";
        return res;
    }

}
