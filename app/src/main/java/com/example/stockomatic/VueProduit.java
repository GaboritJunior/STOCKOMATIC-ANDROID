package com.example.stockomatic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class VueProduit extends AppCompatActivity implements AdapterView.OnItemClickListener{
    // instanciation de la liste de produit vie le json
    ArrayList<String> lesProduitsViaJson = new ArrayList<String>();
    // instanciation de la liste de produit
    ArrayList<Produits> lesProduits = new ArrayList<Produits>();
    // instanciation de la liste de produit via le SQL
    ArrayList<String> lesProduitsViaSQL = new ArrayList<String>();
    // instanciation de la liste de categorie
    ArrayList<Categories> lesCategories = new ArrayList<Categories>();
    // instanciation de la liste de categorie via le json
    ArrayList<String> lesCategoriesViaJson = new ArrayList<String>();
    // instanciation de la liste d'inventaire vie le json
    ArrayList<String> lesInventairesViaJson = new ArrayList<String>();
    // instanciation de la liste d'inventaire
    ArrayList<Inventaires> lesInventaires = new ArrayList<Inventaires>();
    // instanciation du JSONObjet
    JSONObject jObj = null;
    // creation de la variable sgbd
    GestionBD sgbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        // affichage de l'activité
        setContentView(R.layout.activity_produit);
        // recuperation de l'id de la listview
        ListView lvProduit = findViewById(R.id.lvProduit);
        // lecture du fichier json
        String ficProduits = lectureFichierLocal();
        // test dans le logcat pour verifier la lecture du fichier local
        Log.i("doInBack", "le fichier contient : " + ficProduits);
        JSONObject jsonProduits = parseProduits(ficProduits);
        recProduits(jsonProduits);
        // debut de la recupération des categories afin de ne pas avoir l'erreur au premier lancement
        // qui faissait planter l'app car l'app n'as pas encore recup les categorie au premier lancement
        // si on a pas visiter la page des categories
        // lecture du fichier json
        String ficCategories = lectureFichierLocalCateg();
        // test dans le logcat pour verifier la lecture du fichier local
        Log.i("doInBack", "le fichier contient : " + ficCategories);
        JSONObject jsonCategories = parseCategories(ficCategories);
        recCategories(jsonCategories);
        // debut de la recupération des inventaires afin de ne pas avoir l'erreur au premier lancement
        // qui faissait planter l'app car l'app n'as pas encore recup les inventaires au premier lancement
        // si on a pas visiter la page des inventaires
        // lecture du fichier json
        String ficInventaires = lectureFichierLocalInvent();
        // test dans le logcat pour verifier la lecture du fichier local
        Log.i("doInBack", "le fichier contient : " + ficInventaires);
        JSONObject jsonInventaires = parseInventaires(ficInventaires);
        recInventaires(jsonInventaires);
        // ajout des libelle dans la liste
        lesProduitsViaSQL = sgbd.donneLesProduits();
        // instanciation de l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesProduitsViaSQL);
        // ajout de l'adapter dans la listView
        lvProduit.setAdapter(adapter);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur la liste
        lvProduit.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Recup du texte via IHM
        String leProduitClique = parent.getAdapter().getItem(position).toString();
        // recuperation des données du produit cliqué
        String leLibelle = lesProduitsViaSQL.get(position);
        // recuperation des données via le SQL
        String lIdProd = sgbd.getIdProdProduit(leLibelle);
        String laDescription = sgbd.getDescriptionProduit(leLibelle);
        double lePrix = sgbd.getPrixProduit(leLibelle);
        String lIdCateg = sgbd.getIdCategProduit(leLibelle);
        int lIdCategBrut = sgbd.getIdCategProduitBrut(leLibelle);
        // test dans le logcat pour verifier la recuperation des données
        Log.i("doInBack","Test leLibelle vue produit : " + leLibelle);
        Log.i("doInBack","Test lIdProd vue produit : " + lIdProd);
        Log.i("doInBack","Test laDescription vue produit : " + laDescription);
        Log.i("doInBack","Test lePrix vue produit : " + lePrix);
        Log.i("doInBack","Test lIdCateg vue produit : " + lIdCateg);
        Log.i("doInBack","Test lIdCategBrut vue produit : " + lIdCategBrut);
        // recuperation de la page a afficher
        Intent ProduitDetail = new Intent(this, ProduitDetail.class);
        // ajout des valeurs dans l'intent
        ProduitDetail.putExtra("lId",lIdProd);
        ProduitDetail.putExtra("leLibelle",leLibelle);
        ProduitDetail.putExtra("laDescription",laDescription);
        ProduitDetail.putExtra("lePrix",lePrix);
        ProduitDetail.putExtra("lIdCateg",lIdCateg);
        ProduitDetail.putExtra("lIdCategBrut",lIdCategBrut);
        // fermeture de la sgbd
        sgbd.close();
        // affichage de la page
        startActivity(ProduitDetail);
        // fermeture de la page en cours
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Affichage du menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // recuperation de l'id du bouton cliqué sur le menu
        int id = item.getItemId();
        // si le bouton cliqué est produit
        if (id == R.id.action_produit){
            // recuperation de la page a afficher
            Intent pageProduit = new Intent(this, VueProduit.class);
            // affichage de la page
            startActivity(pageProduit);
            // fermeture de la page en cours
            this.finish();
        }
        // si le bouton cliqué est categorie
        if (id == R.id.action_categorie){
            // recuperation de la page a afficher
            Intent pageCategorie = new Intent(this, VueCategorie.class);
            // affichage de la page
            startActivity(pageCategorie);
            // fermeture de la page en cours
            this.finish();
        }
        // si le bouton cliqué est inventaire
        if (id == R.id.action_inventaire){
            // recuperation de la page a afficher
            Intent pageInventaire = new Intent(this, VueInventaire.class);
            // affichage de la page
            startActivity(pageInventaire);
            // fermeture de la page en cours
            this.finish();
        }
        // si le bouton cliqué est ajouter un produit
        if (id == R.id.action_add_produit){
            // recuperation de la page a afficher
            Intent pageAddProduit = new Intent(this, ProduitCreate.class);
            // affichage de la page
            startActivity(pageAddProduit);
            // fermeture de la page en cours
            this.finish();
        }
        // si le bouton cliqué est ajouter une categorie
        if (id == R.id.action_add_categorie){
            // recuperation de la page a afficher
            Intent pageAddCategorie = new Intent(this, CategorieCreate.class);
            // affichage de la page
            startActivity(pageAddCategorie);
            // fermeture de la page en cours
            this.finish();
        }
        // si le bouton cliqué est ajouter un inventaire
        if (id == R.id.action_add_inventaire){
            // recuperation de la page a afficher
            Intent pageAddInventaire = new Intent(this, InventaireCreate.class);
            // affichage de la page
            startActivity(pageAddInventaire);
            // fermeture de la page en cours
            this.finish();
        }
        // si le bouton cliqué est ajouter un produit dans un inventaire
        if (id == R.id.action_add_produit_inventaire) {
            // recuperation de la page a afficher
            Intent pageAddProdInventaire = new Intent(this, ProdInventCreate.class);
            // affichage de la page
            startActivity(pageAddProdInventaire);
            // fermeture de la page en cours
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private JSONObject parseProduits(String ficProduits) {
        try {
            jObj = new JSONObject(ficProduits);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private String lectureFichierLocal() {
        InputStream is = getResources().openRawResource(R.raw.produits);
        StringBuilder builder = new StringBuilder();
        String ligne = null;
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((ligne = bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    private void recProduits(JSONObject jsonProduits) {
        JSONArray lesProds = null;
        //sgbd.videLaTableProduit();
        try {
            lesProds = jsonProduits.getJSONArray("produits");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lesProds.length(); i++) {
            JSONObject pJson = null;
            String leLibelle, laDescription, lIdProduit, lIdCateg;
            int laLimite;
            double lePrix;
            Produits leProduit;

            try {
                pJson = lesProds.getJSONObject(i);
                // recuperation des données dans le json
                lIdProduit = pJson.getString("idproduit");
                leLibelle = pJson.getString("libelle");
                lePrix = pJson.getDouble("prix");
                laDescription = pJson.getString("description");
                lIdCateg = pJson.getString("idcateg");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lIdProduit vue produit : " + lIdProduit);
                Log.i("doInBack","Test leLibelle vue produit : " + leLibelle);
                Log.i("doInBack","Test lePrix vue produit : " + lePrix);
                Log.i("doInBack","Test laDescription vue produit : " + laDescription);
                Log.i("doInBack","Test lIdCateg vue produit : " + lIdCateg);
                // instanciation du produit
                leProduit = new Produits(lIdProduit,leLibelle,lePrix,laDescription,lIdCateg);
                // ajoutr du libelle dans la liste
                lesProduitsViaJson.add(leLibelle);
                // ajout du produit dans la liste
                lesProduits.add(leProduit);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouteProduit(leProduit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject parseCategories(String ficCategories) {
        try {
            jObj = new JSONObject(ficCategories);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private String lectureFichierLocalCateg() {
        InputStream is = getResources().openRawResource(R.raw.categories);
        StringBuilder builder = new StringBuilder();
        String ligne = null;
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((ligne = bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    private void recCategories(JSONObject jsonCategories) {
        JSONArray lesCategs = null;
        //sgbd.videLaTableCategorie();
        try {
            lesCategs = jsonCategories.getJSONArray("categories");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lesCategs.length(); i++) {
            JSONObject pJson = null;
            String lId, leLibelle, typeVente, typeCond;
            Categories laCategorie;

            try {
                pJson = lesCategs.getJSONObject(i);
                lId = pJson.getString("id");
                leLibelle = pJson.getString("libelle");
                typeVente = pJson.getString("typeVente");
                typeCond = pJson.getString("typeCond");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lId recCategories : " + lId);
                Log.i("doInBack","Test leLibelle recCategories : " + leLibelle);
                Log.i("doInBack","Test typeVente recCategories : " + typeVente);
                Log.i("doInBack","Test typeCond recCategories : " + typeCond);
                laCategorie = new Categories(lId,leLibelle,typeVente,typeCond);
                lesCategoriesViaJson.add(leLibelle);
                lesCategories.add(laCategorie);
                sgbd.ajouteCategorie(laCategorie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject parseInventaires(String ficInventaires) {
        try {
            jObj = new JSONObject(ficInventaires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private String lectureFichierLocalInvent() {
        InputStream is = getResources().openRawResource(R.raw.inventaires);
        StringBuilder builder = new StringBuilder();
        String ligne = null;
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((ligne = bfr.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(ligne);
        }
        return builder.toString();
    }

    private void recInventaires(JSONObject jsonInventaires) {
        JSONArray lesInvents = null;
        //sgbd.videLaTableInventaire();
        try {
            lesInvents = jsonInventaires.getJSONArray("inventaires");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lesInvents.length(); i++) {
            JSONObject pJson = null;
            String lIdInventaire, leLibelle ;
            Inventaires lInventaire;

            try {
                pJson = lesInvents.getJSONObject(i);
                // recuperation des données dans le json
                lIdInventaire = pJson.getString("id");
                leLibelle = pJson.getString("libelle");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lIdInventaire recInventaires : " + lIdInventaire);
                Log.i("doInBack","Test leLibelle recInventaires : " + leLibelle);
                // instanciation de l'inventaire
                lInventaire = new Inventaires(lIdInventaire,leLibelle);
                // ajout du libelle dans la liste
                lesInventairesViaJson.add(leLibelle);
                // ajout du produit dans la liste
                lesInventaires.add(lInventaire);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouteInventaire(lInventaire);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}