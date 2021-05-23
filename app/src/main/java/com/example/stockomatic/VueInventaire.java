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

public class VueInventaire extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // instanciation de la liste d'inventaire vie le json
    ArrayList<String> lesInventairesViaJson = new ArrayList<String>();
    // instanciation de la liste d'inventaire
    ArrayList<Inventaires> lesInventaires = new ArrayList<Inventaires>();
    // instanciation de la liste d'inventaire via le SQL
    ArrayList<String> lesInventairesViaSQL = new ArrayList<String>();
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
        setContentView(R.layout.activity_inventaire);
        // recuperation de l'id de la listview
        ListView lvInventaire = findViewById(R.id.lvInventaire);
        // lecture du fichier json
        String ficInventaires = lectureFichierLocal();
        // test dans le logcat pour verifier la lecture du fichier local
        Log.i("doInBack", "le fichier contient : " + ficInventaires);
        JSONObject jsonInventaires = parseInventaires(ficInventaires);
        recInventaires(jsonInventaires);
        // ajout des libelle dans la liste
        lesInventairesViaSQL = sgbd.donneLesInventaires();
        // instanciation de l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesInventairesViaSQL);
        // ajout de l'adapter dans la listView
        lvInventaire.setAdapter(adapter);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur la liste
        lvInventaire.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Recup du texte via IHM
        String lInventaireClique = parent.getAdapter().getItem(position).toString();
        // recuperation des données de la catégorie cliqué
        String leLibelle = lesInventairesViaSQL.get(position);
        // recuperation des données via le SQL
        String lIdInventaire = sgbd.getIdinventaireInventaire(leLibelle);
        // test dans le logcat pour verifier la recuperation des données
        Log.i("doInBack","Test leLibelle vue inventaire : " + leLibelle);
        Log.i("doInBack","Test lIdInventaire vue inventaire : " + lIdInventaire);
        //recuperation de la page a afficher
        Intent InventaireDetail = new Intent(this, InventaireDetail.class);
        // ajout des valeurs dans l'intent
        InventaireDetail.putExtra("lIdInventaire",lIdInventaire);
        InventaireDetail.putExtra("leLibelleInventaire",leLibelle);
        // fermeture de la sgbd
        sgbd.close();
        // affichage de la page
        startActivity(InventaireDetail);
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

    private JSONObject parseInventaires(String ficInventaires) {
        try {
            jObj = new JSONObject(ficInventaires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private String lectureFichierLocal() {
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