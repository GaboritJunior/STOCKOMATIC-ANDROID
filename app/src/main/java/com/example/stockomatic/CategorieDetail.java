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

public class CategorieDetail extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_categorie_detail);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation des valeurs via le bundle
        String lIdCateg = extras.getString("lId");
        String leLibelle = extras.getString("leLibelle");
        String typeVente = extras.getString("typeVente");
        String typeCond = extras.getString("typeCond");
        // test dans le logcat pour verifier que les valeurs sont bien récupérer
        Log.i("doInBack","Test lIdCateg detail categorie : " + lIdCateg);
        Log.i("doInBack","Test leLibelle detail categorie : " + leLibelle);
        Log.i("doInBack","Test typeVente detail categorie : " + typeVente);
        Log.i("doInBack","Test typeCond detail categorie : " + typeCond);
        // recuperation des id des textview
        TextView txtIdCateg = findViewById(R.id.txtIdCateg);
        TextView txtLibelleCateg = findViewById(R.id.txtLibelleCateg);
        TextView txtTypeVente = findViewById(R.id.txtTypeVente);
        TextView txtTypeConditionnement = findViewById(R.id.txtTypeConditionnement);
        // recuperation des id des boutons
        Button btnSupprCategorie = this.findViewById(R.id.btnSupprCategorie);
        Button btnModifCategorie = this.findViewById(R.id.btnModifCategorie);
        Button btnRetourCategorie = this.findViewById(R.id.btnRetourCategorie);
        // remplacement du texte de base par les valeurs recupérer
        txtIdCateg.setText(lIdCateg);
        txtLibelleCateg.setText(leLibelle);
        txtTypeVente.setText(typeVente);
        txtTypeConditionnement.setText(typeCond);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnSupprCategorie.setOnClickListener(this);
        btnModifCategorie.setOnClickListener(this);
        btnRetourCategorie.setOnClickListener(this);
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
        if (id == R.id.btnSupprCategorie){
            // recuperation de la page a afficher
            Intent pageCategorieSuppr = new Intent(this, VueCategorie.class);
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdCateg = extras.getString("lId");
            String leLibelle = extras.getString("leLibelle");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdCateg  suppresion categorie : " + lIdCateg);
            Log.i("doInBack","Test leLibelle  suppresion categorie : " + leLibelle);
            // methode de suppresion d'une ligne de la table catégorie
            sgbd.supprimeCategorie(lIdCateg);
            // fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageCategorieSuppr);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Suppresion de la categorie " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est modifier
        if (id == R.id.btnModifCategorie){
            // recuperation de la page a afficher
            Intent pageCategorieModif = new Intent(this, CategorieModif.class);
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdCateg = extras.getString("lId");
            String leLibelle = extras.getString("leLibelle");
            String typeVente = extras.getString("typeVente");
            String typeCond = extras.getString("typeCond");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdCateg  modification categorie : " + lIdCateg);
            Log.i("doInBack","Test leLibelle  modification categorie : " + leLibelle);
            Log.i("doInBack","Test typeVente  modification categorie : " + typeVente);
            Log.i("doInBack","Test typeCond  modification categorie : " + typeCond);
            // ajout des valeurs dans l'intent
            pageCategorieModif.putExtra("lId",lIdCateg);
            pageCategorieModif.putExtra("leLibelle",leLibelle);
            pageCategorieModif.putExtra("typeVente",typeVente);
            pageCategorieModif.putExtra("typeCond",typeCond);
            // affichage de la page
            startActivity(pageCategorieModif);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Modification de la categorie " + leLibelle, Toast.LENGTH_SHORT);

            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est retour
        if (id == R.id.btnRetourCategorie){
            // recuperation de la page a afficher
            Intent pageCategorieRetour = new Intent(this, VueCategorie.class);
            // affichage de la page
            startActivity(pageCategorieRetour);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Retour a la liste des categorie", Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
    }
}