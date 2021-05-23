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

public class ProdInventModif extends AppCompatActivity implements View.OnClickListener{

    // creation de la variable sgbd
    GestionBD sgbd;
    // instanciation de la liste de produit via le SQL
    ArrayList<String> lesProduitViaSQL = new ArrayList<String>();
    // instanciation de la liste d'inventaires via le SQL
    ArrayList<String> lesInventaireViaSQL = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        // affichage de l'activité
        setContentView(R.layout.activity_prod_invent_modif);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation des valeurs via le bundle
        String idProduit = extras.getString("idProduit");
        String idInventaire = extras.getString("idInventaire");
        int laLimite = extras.getInt("laLimite");
        int qteStock = extras.getInt("qteStock");
        // test dans le logcat pour verifier que les valeurs sont bien récupérer
        Log.i("doInBack","Test idProduit modification prodinvent : " + idProduit);
        Log.i("doInBack","Test idInventaire modification prodinvent : " + idInventaire);
        Log.i("doInBack","Test laLimite modification prodinvent : " + laLimite);
        Log.i("doInBack","Test qteStock modification prodinvent : " + qteStock);
        // recuperation des id des boutons
        Button btnModifProdInvent = this.findViewById(R.id.btnModifProdInvent);
        Button btnCancelProdInvent = this.findViewById(R.id.btnCancelProdInvent);
        // recuperation des id des EditText
        EditText edLimite = (EditText)findViewById(R.id.txtModifLimiteStockAlert);
        EditText edQuantite = (EditText)findViewById(R.id.txtModifQteStock);
        // recuperation des id des spinner
        Spinner spinLibelleProd = this.findViewById(R.id.spinLibelleProduitModifProdInvent);
        Spinner spinLibelleInvent = this.findViewById(R.id.spinLibelleInventaireModifProdInvent);
        // ajout des libelle dans la liste
        lesProduitViaSQL = sgbd.donneLesProduits();
        lesInventaireViaSQL = sgbd.donneLesInventaires();
        // adpater du spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,lesProduitViaSQL);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,lesInventaireViaSQL);
        spinLibelleProd.setAdapter(adapter);
        spinLibelleInvent.setAdapter(adapter2);
        // recup du libelle
        String leLibelleProdSelect = sgbd.getLibelleProduit(idProduit);
        String leLibelleInventSelect = sgbd.getLibelleInventaire(idInventaire);
        // recup de la position
        int positionProd = lesProduitViaSQL.indexOf(leLibelleProdSelect);
        int positionInvent = lesInventaireViaSQL.indexOf(leLibelleInventSelect);
        // selection du spinner
        spinLibelleProd.setSelection(positionProd);
        spinLibelleInvent.setSelection(positionInvent);
        // ajout des valeurs dans les EditText
        edLimite.setText(String.valueOf(laLimite));
        edQuantite.setText(String.valueOf(qteStock));
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnModifProdInvent.setOnClickListener(this);
        btnCancelProdInvent.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        // recuperation de l'id du bouton cliqué sur l'activité
        int id = v.getId();
        // si le bouton cliqué est modifier
        if (id == R.id.btnModifProdInvent){
            // recuperation de la page a afficher
            Intent pageProduitInventModif = new Intent(this, VueInventaire.class);
            // recuperation des id des EditText
            EditText edIdProd = (EditText)findViewById(R.id.txtModifIdProd);
            EditText edIdInvent = (EditText)findViewById(R.id.txtModifIdIvent);
            EditText edLimite = (EditText)findViewById(R.id.txtModifLimiteStockAlert);
            EditText edQuantite = (EditText)findViewById(R.id.txtModifQteStock);
            // recuperation de l'id du spinner
            Spinner spinLibelleProd = (Spinner)findViewById(R.id.spinLibelleProduitModifProdInvent);
            Spinner spinLibelleInvent = (Spinner)findViewById(R.id.spinLibelleInventaireModifProdInvent);
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String idProduitOriginal = extras.getString("idProduit");
            String idInventaireOriginal = extras.getString("idInventaire");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test idProduitOriginal modification prodinvent : " + idProduitOriginal);
            Log.i("doInBack","Test idInventaireOriginal modification prodinvent : " + idInventaireOriginal);
            // recuperation des valeurs
            int laLimite = (int) Integer.parseInt(edLimite.getText().toString());
            int laQuantite = (int) Integer.parseInt(edQuantite.getText().toString());
            int leLibelleProdSelect = spinLibelleProd.getSelectedItemPosition();
            int leLibelleInventSelect = spinLibelleInvent.getSelectedItemPosition();
            // recuperation du libelle
            String leLibelleProd = lesProduitViaSQL.get(leLibelleProdSelect);
            String leLibelleInvent = lesInventaireViaSQL.get(leLibelleInventSelect);
            // recup de l'id de la categorie
            String lIdProdListe = sgbd.getIdProdProduit(leLibelleProd);
            String lIdInventListe = sgbd.getIdinventaireInventaire(leLibelleInvent);
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdProduit modification prodinvent : " + lIdProdListe);
            Log.i("doInBack","Test lIdInvent modification prodinvent : " + lIdInventListe);
            Log.i("doInBack","Test laLimite modification prodinvent : " + laLimite);
            Log.i("doInBack","Test laQuantite modification prodinvent : " + laQuantite);
            // instanciation du prodinvent
            ProdInvents leProdInvent = new ProdInvents(lIdProdListe,lIdInventListe,laLimite,laQuantite);
            // methode pour modifier les données dans la sgbd
            sgbd.ModifQteStock(leProdInvent,idProduitOriginal,idInventaireOriginal);
            // affichage de la page
            startActivity(pageProduitInventModif);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Modification du produit "+ leLibelleProd +" dans l'inventaire "+ leLibelleInvent, Toast.LENGTH_SHORT);
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
            // fermuture de la page en cours
            this.finish();
            // fermeture de la sgbd
            sgbd.close();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Abandon de la modification du produit dans l'inventaire", Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
    }
}