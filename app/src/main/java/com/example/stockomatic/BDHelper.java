package com.example.stockomatic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {

    public BDHelper (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // requete de creation de la table categorie
        String req =
                "CREATE TABLE categorie (idCategorie text not null, " +
                        "libelleCateg text not null, " +
                        "typeVente text not null, " +
                        "typeCond text not null," +
                        "PRIMARY KEY (idCategorie));";
        // execution de la requete
        db.execSQL(req);
        // requete de creation de la table inventaire
        String req2 =
                "CREATE TABLE inventaire (idInventaire text not null, " +
                        "libelleInventaire text not null, " +
                        "PRIMARY KEY (idInventaire));";
        // execution de la requete
        db.execSQL(req2);
        // requete de creation de la table produit
        String req3 =
                "CREATE TABLE produit (idProduit text not null, " +
                        "libelleProduit text not null, " +
                        "prix float not null, " +
                        "description text," +
                        "idCategorie text not null, " +
                        "PRIMARY KEY (idProduit), " +
                        "FOREIGN KEY (idCategorie) REFERENCES categorie(idCategorie));";
        // execution de la requete
        db.execSQL(req3);
        // requete de creation de la table qteStock
        String req4 =
                "CREATE TABLE qteStock (idProduit text not null, " +
                        "idInventaire text not null, " +
                        "limiteStockAlert integer not null, " +
                        "qteStock integer not null, " +
                        "PRIMARY KEY (idProduit, idInventaire), " +
                        "FOREIGN KEY (idProduit) REFERENCES produit(idProduit), " +
                        "FOREIGN KEY (idInventaire) REFERENCES inventaire(idInventaire));";
        // execution de la requete
        db.execSQL(req4);
        // requette du trigger avant la suppresion d'une ligne de categorie
        String req5 =
                "CREATE TRIGGER tr_delete_categorie " +
                        "BEFORE DELETE " +
                        "ON categorie " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "DELETE " +
                        "FROM produit " +
                        "WHERE idCategorie = old.idCategorie; " +
                        "END;";
        // execution de la requette
        db.execSQL(req5);
        // requette du trigger avant la suppresion d'une ligne de inventaire
        String req6 =
                "CREATE TRIGGER tr_delete_inventaire " +
                        "BEFORE DELETE " +
                        "ON inventaire " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "DELETE " +
                        "FROM qteStock " +
                        "WHERE idInventaire = old.idInventaire; " +
                        "END;";
        // execution de la requette
        db.execSQL(req6);
        // requette du trigger avant la suppresion d'une ligne de produit
        String req7 =
                "CREATE TRIGGER tr_delete_produit " +
                        "BEFORE DELETE " +
                        "ON produit " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "DELETE " +
                        "FROM qteStock " +
                        "WHERE idProduit = old.idProduit; " +
                        "END;";
        // execution de la requette
        db.execSQL(req7);
        // requette du trigger apres la modification d'une ligne de categorie
        String req8 =
                "CREATE TRIGGER tr_update_categorie " +
                        "AFTER UPDATE " +
                        "ON categorie " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "UPDATE produit " +
                        "SET idCategorie = new.idCategorie " +
                        "WHERE idCategorie = old.idCategorie; " +
                        "END;";
        // execution de la requette
        db.execSQL(req8);
        // requette du trigger apres la modification d'une ligne de inventaire
        String req9 =
                "CREATE TRIGGER tr_update_inventaire " +
                        "AFTER UPDATE " +
                        "ON inventaire " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "UPDATE qteStock " +
                        "SET idInventaire = new.idInventaire " +
                        "WHERE idInventaire = old.idInventaire; " +
                        "END;";
        // execution de la requette
        db.execSQL(req9);
        // requette du trigger apres la modification d'une ligne de produit
        String req10 =
                "CREATE TRIGGER tr_update_produit " +
                        "AFTER UPDATE " +
                        "ON produit " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "UPDATE qteStock " +
                        "SET idProduit = new.idProduit " +
                        "WHERE idProduit = old.idProduit; " +
                        "END;";
        // execution de la requette
        db.execSQL(req10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
