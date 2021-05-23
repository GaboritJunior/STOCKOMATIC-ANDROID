package com.example.stockomatic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProduitDetail extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_produit_detail);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation des valeurs via le bundle
        String lIdProd = extras.getString("lId");
        String leLibelle = extras.getString("leLibelle");
        String laDescription = extras.getString("laDescription");
        double lePrix = extras.getDouble("lePrix");
        String lIdCateg = extras.getString("lIdCateg");
        // test dans le logcat pour verifier que les valeurs sont bien récupérer
        Log.i("doInBack","Test lIdProd  detail produit : " + lIdProd);
        Log.i("doInBack","Test leLibelle detail produit  : " + leLibelle);
        Log.i("doInBack","Test lePrix detail produit : " + lePrix);
        Log.i("doInBack","Test laDescription detail produit : " + laDescription);
        Log.i("doInBack","Test lIdCateg detail produit : " + lIdCateg);
        // recuperation des id des textview
        TextView txtIdProd = findViewById(R.id.txtIdProd);
        TextView txtLibelle = findViewById(R.id.txtLibelle);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtPrix = findViewById(R.id.txtPrix);
        TextView txtIdCateg = findViewById(R.id.txtIdCategorie);
        // recuperation des id des boutons
        Button btnSupprProduit = this.findViewById(R.id.btnSupprProduit);
        Button btnModifProduit = this.findViewById(R.id.btnModifProduit);
        Button btnRetourProduit = this.findViewById(R.id.btnRetourProduit);
        // remplacement du texte de base par les valeurs recupérer
        txtIdProd.setText(lIdProd);
        txtLibelle.setText(leLibelle);
        txtDescription.setText(laDescription);
        txtPrix.setText(String.valueOf(lePrix) + " €");
        txtIdCateg.setText(lIdCateg);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnSupprProduit.setOnClickListener(this);
        btnModifProduit.setOnClickListener(this);
        btnRetourProduit.setOnClickListener(this);
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
        // si le bouton cliqué est supprimer
        if (id == R.id.btnSupprProduit){
            // recuperation de la page a afficher
            Intent pageProduitSuppr = new Intent(this, VueProduit.class);
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdProd = extras.getString("lId");
            String leLibelle = extras.getString("leLibelle");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdProd  suppression produit : " + lIdProd);
            Log.i("doInBack","Test leLibelle suppression produit  : " + leLibelle);
            // methode de suppresion de données dans la sgbd
            sgbd.supprimeProduit(lIdProd);
            // fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageProduitSuppr);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Suppresion du produit " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est modifier
        if (id == R.id.btnModifProduit){
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdProd = extras.getString("lId");
            String leLibelle = extras.getString("leLibelle");
            String laDescription = extras.getString("laDescription");
            double lePrix = extras.getDouble("lePrix");
            int lIdCategBrut = extras.getInt("lIdCategBrut");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdProd  modification produit : " + lIdProd);
            Log.i("doInBack","Test leLibelle modification produit  : " + leLibelle);
            Log.i("doInBack","Test lePrix modification produit : " + lePrix);
            Log.i("doInBack","Test laDescription modification produit : " + laDescription);
            Log.i("doInBack","Test lIdCategBrut modification produit : " + lIdCategBrut);
            // recuperation de la page a afficher
            Intent pageProduitModif = new Intent(this, ProduitModif.class);
            // ajout des valeurs dans l'intent
            pageProduitModif.putExtra("lId",lIdProd);
            pageProduitModif.putExtra("leLibelle",leLibelle);
            pageProduitModif.putExtra("laDescription",laDescription);
            pageProduitModif.putExtra("lePrix",lePrix);
            pageProduitModif.putExtra("lIdCateg",lIdCategBrut);
            // affichage de la page
            startActivity(pageProduitModif);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Modification du produit " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est retour
        if (id == R.id.btnRetourProduit){
            // recuperation de la page a afficher
            Intent pageProduitRetour = new Intent(this, VueProduit.class);
            // affichage de la page
            startActivity(pageProduitRetour);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Retour a la liste des produit", Toast.LENGTH_SHORT);
            // affichage du toast
            toast.show();
        }
    }
}