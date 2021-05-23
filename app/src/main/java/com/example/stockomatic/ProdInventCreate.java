package com.example.stockomatic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProdInventCreate extends AppCompatActivity implements View.OnClickListener {

    // creation de la variable sgbd
    GestionBD sgbd;
    // instanciation de la liste de produit via le SQL
    ArrayList<String> lesProduitsViaSQL = new ArrayList<String>();
    // instanciation de la liste de produit via le SQL
    ArrayList<String> lesInventairesViaSQL = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        // affichage de l'activité
        setContentView(R.layout.activity_prod_invent_create);
        // recuperation des id des boutons
        Button btnAddProdInvent = this.findViewById(R.id.btnAddProdInvent);
        Button btnCancelProdInvent = this.findViewById(R.id.btnCancelProdInvent);
        // recuperation des id des spinner
        Spinner spinLibelleProduit = this.findViewById(R.id.spinLibelleProduitAddProdInvent);
        Spinner spinLibelleInventaire = this.findViewById(R.id.spinLibelleInventaireAddProdInvent);
        // ajout des libelle dans la liste
        lesProduitsViaSQL = sgbd.donneLesProduits();
        lesInventairesViaSQL = sgbd.donneLesInventaires();
        // adapter du spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,lesProduitsViaSQL);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,lesInventairesViaSQL);
        spinLibelleProduit.setAdapter(adapter);
        spinLibelleInventaire.setAdapter(adapter2);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnAddProdInvent.setOnClickListener(this);
        btnCancelProdInvent.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // affichage du menu
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

    @Override
    public void onClick(View v) {
        // recuperation de l'id du bouton cliqué sur l'activité
        int id = v.getId();
        // si le bouton cliqué est ajouter
        if (id == R.id.btnAddProdInvent){
            // recuperation de la page a afficher
            Intent pageProduitInventAdd = new Intent(this, VueInventaire.class);
            // recuperation des id des EditText
            EditText edIdProd = (EditText)findViewById(R.id.txtAddIdProd);
            EditText edIdIvent = (EditText)findViewById(R.id.txtAddIdIvent);
            EditText edLimite = (EditText)findViewById(R.id.txtAddLimiteStockAlert);
            EditText edQteStock = (EditText)findViewById(R.id.txtAddQteStock);
            // recuperation des id du spinner
            Spinner spinLibelleProd = (Spinner)findViewById(R.id.spinLibelleProduitAddProdInvent);
            Spinner spinLibelleInvent = (Spinner)findViewById(R.id.spinLibelleInventaireAddProdInvent);
            // recuperation des valeurs
            int lIdProdList = spinLibelleProd.getSelectedItemPosition();
            int lIdInventList = spinLibelleInvent.getSelectedItemPosition();
            int laLimite = (int) Integer.parseInt(edLimite.getText().toString());
            int qteStock = (int) Integer.parseInt(edQteStock.getText().toString());
            // recuperation du libelle de la categorie
            String leLibelleProd = lesProduitsViaSQL.get(lIdProdList);
            String leLibelleInvent = lesInventairesViaSQL.get(lIdInventList);
            // recup de l'id de la categorie
            String lIdProd = sgbd.getIdProdProduit(leLibelleProd);
            String lIdInvent = sgbd.getIdinventaireInventaire(leLibelleInvent);
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test leLibelleProd ajout prodinvent : " + lIdProd);
            Log.i("doInBack","Test lIdInvent ajout prodinvent : " + lIdInvent);
            Log.i("doInBack","Test laLimite ajout prodinvent : " + laLimite);
            Log.i("doInBack","Test qteStock ajout prodinvent : " + qteStock);
            // instanciation du prodinvent
            ProdInvents leProdInvent = new ProdInvents(lIdProd,lIdInvent,laLimite,qteStock);
            // methode d'ajout de données dans la sgbd
            sgbd.ajouteQteStock(leProdInvent);
            // affichage de la page
            startActivity(pageProduitInventAdd);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Ajout du produit "+ leLibelleProd +" dans l'inventaire "+ leLibelleInvent , Toast.LENGTH_SHORT);
            // fermeture de la sgbd
            sgbd.close();
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est annuler
        if (id == R.id.btnCancelProdInvent){
            // recuperation de la page a afficher
            Intent pageProduitInventCancel = new Intent(this, VueInventaire.class);
            // affichage de la page
            startActivity(pageProduitInventCancel);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Abandon de l'ajout du produit dans l'inventaire", Toast.LENGTH_SHORT);
            // affichage du toast
            toast.show();
        }
    }
}