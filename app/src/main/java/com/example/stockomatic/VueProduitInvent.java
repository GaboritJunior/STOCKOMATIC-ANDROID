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

public class VueProduitInvent extends AppCompatActivity implements AdapterView.OnItemClickListener{
    // instanciation de la liste de produit dans l'inventaire via le json
    ArrayList<String> lesProduitsInventairesViaJson = new ArrayList<String>();
    // instanciation de la liste de produit dans l'inventaire
    ArrayList<ProdInvents> lesProduitsInventaires = new ArrayList<ProdInvents>();
    // instanciation de la liste de produit dans l'inventaire via le SQL
    ArrayList<String> lesProduitsInventairesViaSQL = new ArrayList<String>();
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
        setContentView(R.layout.activity_prod_invent);
        // recuperation de l'id de la listview
        ListView lvProduitsInventaire = findViewById(R.id.lvProduitInventaire);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation du libelle
        String leLibelle = extras.getString("leLibelle");
        // test dans le logcat
        Log.i("doInBack", "Test leLibelle vue prodInvent " + leLibelle);
        // lecture du fichier json
        String ficProduitsInventaires = lectureFichierLocal();
        // test dans le logcat pour verifier la lecture du fichier local
        Log.i("doInBack", "le fichier contient : " + ficProduitsInventaires);
        JSONObject jsonProduitsInventaires = parseProduitsInventaires(ficProduitsInventaires);
        // recuperation de la liste dans le json avec comme parametre le libelle pour trouver le bon inventaire
        recProduitsInventaires(jsonProduitsInventaires);
        // ajout des libelle dans la liste
        lesProduitsInventairesViaSQL = sgbd.donneLesQteStock(leLibelle);
        // instanciation de l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesProduitsInventairesViaSQL);
        // ajout de l'adapter dans la listView
        lvProduitsInventaire.setAdapter(adapter);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur la liste
        lvProduitsInventaire.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Recup du texte via IHM
        String leProdInventaireClique = parent.getAdapter().getItem(position).toString();
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation du libelle
        String leLibelleInventaire = extras.getString("leLibelle");
        // recuperation des données de le produit dans l'inventaire cliqué
        String leLibelle = lesProduitsInventairesViaSQL.get(position);
        String IdInventaire = sgbd.getIdInventaireQteStock(leLibelle,leLibelleInventaire);
        // recuperation des données via le SQL
        String idProduit = sgbd.getIdProdQteStock(leLibelle, IdInventaire);
        int laLimite = sgbd.getLimiteQteStock(leLibelle, IdInventaire);
        int qteStock = sgbd.getQuantiteQteStock(leLibelle, IdInventaire);
        // test dans le logcat pour verifier la recuperation des données
        Log.i("doInBack","Test leLibelleInventaire vue prodinvent : " + leLibelleInventaire);
        Log.i("doInBack","Test leLibelle vue prodinvent : " + leLibelle);
        Log.i("doInBack","Test IdInventaire vue prodinvent : " + IdInventaire);
        Log.i("doInBack","Test idProduit vue prodinvent : " + idProduit);
        Log.i("doInBack","Test laLimite vue prodinvent : " + laLimite);
        Log.i("doInBack","Test qteStock vue prodinvent : " + qteStock);
        // recuperation de la page a afficher
        Intent ProduitInventaireDetail = new Intent(this, ProdInventDetail.class);
        // ajout des valeurs dans l'intent
        ProduitInventaireDetail.putExtra("idProduit",idProduit);
        ProduitInventaireDetail.putExtra("idInventaire",IdInventaire);
        ProduitInventaireDetail.putExtra("leLibelle",leLibelle);
        ProduitInventaireDetail.putExtra("laLimite",laLimite);
        ProduitInventaireDetail.putExtra("qteStock",qteStock);
        ProduitInventaireDetail.putExtra("leLibelleInventaire",leLibelleInventaire);
        // fermeture de la sgbd
        sgbd.close();
        // affichage de la page
        startActivity(ProduitInventaireDetail);
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

    private void recProduitsInventaires(JSONObject jsonProduitsInventaires) {
        JSONArray lesProdInvents = null;
        //sgbd.videLaTableQteStock();
        try {
            lesProdInvents = jsonProduitsInventaires.getJSONArray("prodInvent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lesProdInvents.length(); i++) {
            JSONObject pJson = null;
            int laLimite,qteStock;
            String leLibelle,lIdProd,lIdInventaire;
            ProdInvents leProdInvents;

            try {
                pJson = lesProdInvents.getJSONObject(i);
                // recuperation des données dans le json
                lIdProd = pJson.getString("idproduit");
                lIdInventaire = pJson.getString("idinventaire");
                leLibelle = pJson.getString("libelle");
                laLimite = pJson.getInt("limiteStockAlert");
                qteStock = pJson.getInt("qteStock");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lIdProd recProduitsInventaires : " + lIdProd);
                Log.i("doInBack","Test lIdInventaire recProduitsInventaires : " + lIdInventaire);
                Log.i("doInBack","Test leLibelle recProduitsInventaires : " + leLibelle);
                Log.i("doInBack","Test laLimite recProduitsInventaires : " + laLimite);
                Log.i("doInBack","Test qteStock recProduitsInventaires : " + qteStock);
                // instanciation du ProdInvents
                leProdInvents = new ProdInvents(lIdProd,lIdInventaire,laLimite,qteStock);
                // ajout du libelle dans la liste
                lesProduitsInventairesViaJson.add(leLibelle);
                // ajout du ProdInvents dans la liste
                lesProduitsInventaires.add(leProdInvents);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouteQteStock(leProdInvents);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private JSONObject parseProduitsInventaires(String ficProduitsInventaires) {
        try {
            jObj = new JSONObject(ficProduitsInventaires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private String lectureFichierLocal() {
        InputStream is = getResources().openRawResource(R.raw.produitinventaire);
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
}