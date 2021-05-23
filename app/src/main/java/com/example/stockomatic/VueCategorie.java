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

public class VueCategorie extends AppCompatActivity implements AdapterView.OnItemClickListener{
    // instanciation de la liste de categorie via le json
    ArrayList<String> lesCategoriesViaJson = new ArrayList<String>();
    // instanciation de la liste de categorie
    ArrayList<Categories> lesCategories = new ArrayList<Categories>();
    // instanciation de la liste de categorie via le SQL
    ArrayList<String> lesCategoriesViaSQL = new ArrayList<String>();
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
        setContentView(R.layout.activity_categorie);
        // recuperation de l'id de la listview
        ListView lvCategorie = findViewById(R.id.lvCategorie);
        // lecture du fichier json
        String ficCategories = lectureFichierLocal();
        // test dans le logcat pour verifier la lecture du fichier local
        Log.i("doInBack", "le fichier contient : " + ficCategories);
        JSONObject jsonCategories = parseCategories(ficCategories);
        recCategories(jsonCategories);
        // ajout des libelle dans la liste
        lesCategoriesViaSQL = sgbd.donneLesCategorie();
        // instanciation de l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesCategoriesViaSQL);
        // ajout de l'adapter dans la listView
        lvCategorie.setAdapter(adapter);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur la liste
        lvCategorie.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Recup du texte via IHM
        String laCategClique = parent.getAdapter().getItem(position).toString();
        // recuperation des données de la catégorie cliqué
        String leLibelle = lesCategoriesViaSQL.get(position);
        // recuperation des données via le SQL
        String lIdCateg = sgbd.getidCategCategorie(leLibelle);
        String typeVente = sgbd.getTypeVente(leLibelle);
        String typeCond = sgbd.getTypeCond(leLibelle);
        // test dans le logcat pour verifier la recuperation des données
        Log.i("doInBack","Test leLibelle vue categorie : "+leLibelle);
        Log.i("doInBack","Test lIdCateg vue categorie : "+lIdCateg);
        Log.i("doInBack","Test typeVente vue categorie : "+typeVente);
        Log.i("doInBack","Test typeCond  vue categorie : "+typeCond);
        // recuperation de la page a afficher
        Intent CategorieDetail = new Intent(this, CategorieDetail.class);
        // ajout des valeurs dans l'intent
        CategorieDetail.putExtra("lId",lIdCateg);
        CategorieDetail.putExtra("leLibelle",leLibelle);
        CategorieDetail.putExtra("typeVente",typeVente);
        CategorieDetail.putExtra("typeCond",typeCond);
        // fermeture de la sgbd
        sgbd.close();
        // affichage de la page
        startActivity(CategorieDetail);
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

    private JSONObject parseCategories(String ficCategories) {
        try {
            jObj = new JSONObject(ficCategories);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    private String lectureFichierLocal() {
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
                // recuperation des données dans le json
                lId = pJson.getString("id");
                leLibelle = pJson.getString("libelle");
                typeVente = pJson.getString("typeVente");
                typeCond = pJson.getString("typeCond");
                // test dans le logcat pour verifier la recuperation des données
                Log.i("doInBack","Test lId recCategories : " + lId);
                Log.i("doInBack","Test leLibelle recCategories : " + leLibelle);
                Log.i("doInBack","Test typeVente recCategories : " + typeVente);
                Log.i("doInBack","Test typeCond recCategories : " + typeCond);
                // instanciation de la categorie
                laCategorie = new Categories(lId,leLibelle,typeVente,typeCond);
                // ajout du libelle dans la liste
                lesCategoriesViaJson.add(leLibelle);
                // ajout de la categorie dans la liste
                lesCategories.add(laCategorie);
                // methode pour ajouter une ligne dans la sgbd
                sgbd.ajouteCategorie(laCategorie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
