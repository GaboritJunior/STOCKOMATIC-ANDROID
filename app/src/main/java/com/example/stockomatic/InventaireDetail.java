package com.example.stockomatic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InventaireDetail extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_inventaire_detail);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation des valeurs via le bundle
        String lIdInventaire = extras.getString("lIdInventaire");
        String leLibelle = extras.getString("leLibelleInventaire");
        // test dans le logcat pour verifier que les valeurs sont bien recuperer
        Log.i("doInBack","Test lIdInventaire detail inventaire " + lIdInventaire);
        Log.i("doInBack","Test leLibelle detail inventaire " + leLibelle);
        // recuperation des id des textview
        TextView txtIdProd = findViewById(R.id.txtIdInventaire);
        TextView txtLibelle = findViewById(R.id.txtLibelleInventaire);
        // recuperation des id des boutons
        Button btnSupprInventaire = this.findViewById(R.id.btnSupprInventaire);
        Button btnModifInventaire = this.findViewById(R.id.btnModifInventaire);
        Button btnRetourInventaire = this.findViewById(R.id.btnRetourInventaire);
        Button btnVueProduitInventaire = this.findViewById(R.id.btnVueProduitInventaire);
        // remplacement du texte de base par les veleurs repuperer
        txtIdProd.setText(lIdInventaire);
        txtLibelle.setText(leLibelle);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnSupprInventaire.setOnClickListener(this);
        btnModifInventaire.setOnClickListener(this);
        btnRetourInventaire.setOnClickListener(this);
        btnVueProduitInventaire.setOnClickListener(this);
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
        // recuperation des l'id du bouton cliqué sur l'activité
        int id = v.getId();
        // si le bouton cliqué est supprimer
        if (id == R.id.btnSupprInventaire){
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdInventaire = extras.getString("lIdInventaire");
            String leLibelle = extras.getString("leLibelleInventaire");
            // test dans le logcat pour verifier que les valeurs sont bien recuperer
            Log.i("doInBack","Test lIdInventaire suppresion inventaire " + lIdInventaire);
            Log.i("doInBack","Test libelle2 suppresion inventaire " + leLibelle);
            // recuperation de la page a afficher
            Intent pageInventaireSuppr = new Intent(this, VueInventaire.class);
            // methode de suppresion de données dans la sgbd
            sgbd.supprimeInventaire(lIdInventaire);
            // fermuture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageInventaireSuppr);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Suppresion de l'inventaire " + leLibelle , Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est modifier
        if (id == R.id.btnModifInventaire){
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdInventaire = extras.getString("lIdInventaire");
            String leLibelle = extras.getString("leLibelleInventaire");
            // test dans le logcat pour verifier que les valeurs sont bien recuperer
            Log.i("doInBack","Test lIdInventaire modification inventaire " + lIdInventaire);
            Log.i("doInBack","Test libelle2 modification inventaire " + leLibelle);
            // recuperation de la page a afficher
            Intent pageInventaireModif = new Intent(this, InventaireModif.class);
            // ajout des valeurs dans l'intent
            pageInventaireModif.putExtra("lIdInventaire",lIdInventaire);
            pageInventaireModif.putExtra("leLibelleInventaire",leLibelle);
            // affichage de la page
            startActivity(pageInventaireModif);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Modification de l'inventaire " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est retour
        if (id == R.id.btnRetourInventaire){
            // recuperation de la page a afficher
            Intent pageInventaireRetour = new Intent(this, VueInventaire.class);
            // affichage de la page
            startActivity(pageInventaireRetour);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Retour a la liste des inventaires", Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est voir les produit
        if (id == R.id.btnVueProduitInventaire) {
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdInventaire = extras.getString("lIdInventaire");
            String leLibelle = extras.getString("leLibelleInventaire");
            // test dans le logcat pour verifier que les valeurs sont bien recuperer
            Log.i("doInBack","Test lIdInventaire vue prodinvent " + lIdInventaire);
            Log.i("doInBack","Test leLibelle vue prodinvent " + leLibelle);
            // recuperation de la page a afficher
            Intent pageProduitInventaire = new Intent(this, VueProduitInvent.class);
            // ajout du libelle dans l'intent
            pageProduitInventaire.putExtra("leLibelle",leLibelle);
            pageProduitInventaire.putExtra("leLibelleInventaire",lIdInventaire);
            // affichage de la page
            startActivity(pageProduitInventaire);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(), "Vue des produits dans l'inventaire "+ leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
    }
}