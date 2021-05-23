package com.example.stockomatic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProdInventDetail extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_prod_invent_detail);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation des valeurs via le bundle
        String idProduit = extras.getString("idProduit");
        String leLibelle = extras.getString("leLibelle");
        int laLimite = extras.getInt("laLimite");
        int qteStock = extras.getInt("qteStock");
        // test dans le logcat pour verifier que les valeurs sont bien récupérer
        Log.i("doInBack","Test idProduit detail prodinvent : " + idProduit);
        Log.i("doInBack","Test leLibelle detail prodinvent : " + leLibelle);
        Log.i("doInBack","Test laLimite detail prodinvent :" + laLimite);
        Log.i("doInBack","Test qteStock detail prodinvent :" + qteStock);
        // recuperation des id des textview
        TextView txtIdProd = findViewById(R.id.txtIdProdInvent);
        TextView txtLibelle = findViewById(R.id.txtLibelleProdInvent);
        TextView txtLimite = findViewById(R.id.txtLimiteProdInvent);
        TextView txtQteStock = findViewById(R.id.txtQteProdInvent);
        TextView txtEtatStock = findViewById(R.id.txtEtatStock);
        // recuperation des id des boutons
        Button btnSupprProdInvent = this.findViewById(R.id.btnSupprProdInvent);
        Button btnModifProdInvent = this.findViewById(R.id.btnModifProdInvent);
        Button btnRetourProdInvent = this.findViewById(R.id.btnRetourProdInvent);
        // remplacement du texte de base par les valeurs recupérer
        txtIdProd.setText(idProduit);
        txtLibelle.setText(leLibelle);
        txtLimite.setText(String.valueOf(laLimite));
        txtQteStock.setText(String.valueOf(qteStock));
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnSupprProdInvent.setOnClickListener(this);
        btnModifProdInvent.setOnClickListener(this);
        btnRetourProdInvent.setOnClickListener(this);
        // si la quantité est au dessus de la limite
        if (qteStock > laLimite){
            // affichage du texte
            txtEtatStock.setText("Stock d'alerte non atteint");
            txtEtatStock.setTextColor(Color.parseColor("#27AE60"));
        }
        // si la quantité est égale à la limite
        else if (qteStock == laLimite) {
            // affichage du texte
            txtEtatStock.setText("Stock d'alerte atteint, pensez à réapprovisionner le stock");
            txtEtatStock.setTextColor(Color.parseColor("#F39C12"));
        }
        // si la quantité est en dessous de la limite
        else if (qteStock < laLimite && qteStock > 0){
            // affichage du texte
            txtEtatStock.setText("Stock d'alerte dépassé, réapprovisionner le stock au plus vite");
            txtEtatStock.setTextColor(Color.parseColor("#C0392B"));
        }
        // si la quantité est egale à 0
        else if (qteStock == 0){
            // affichage du texte
            txtEtatStock.setText("Stock vide, réapprovisionner le stock au plus vite");
            txtEtatStock.setTextColor(Color.parseColor("#C0392B"));
        }

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
        if (id == R.id.btnSupprProdInvent){
            // recuperation de la page a afficher
            Intent pageInventaireSuppr = new Intent(this, VueInventaire.class);
            // recuperation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String idProduit = extras.getString("idProduit");
            String leLibelleInventaire = extras.getString("leLibelleInventaire");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test idProduit suppresion prodinvent : " + idProduit);
            Log.i("doInBack","Test leLibelleInventaire suppresion prodinvent : " + leLibelleInventaire);
            // methoode de suppression de données dans la sgbd
            sgbd.supprimeQteStock(idProduit,leLibelleInventaire);
            // affichage de la page
            startActivity(pageInventaireSuppr);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Suppresion du produit "+ sgbd.getLibelleProduit(idProduit) +" de l'inventaire", Toast.LENGTH_SHORT);
            // fermeture de la sgbd
            sgbd.close();
            // affichage du toast
            toast.show();
        }
        // si le bouton cliqué est modifier
        if (id == R.id.btnModifProdInvent){
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
            // recuperation de la page a afficher
            Intent pageInventaireModif = new Intent(this, ProdInventModif.class);
            // ajout des valeurs dans l'intent
            pageInventaireModif.putExtra("idProduit",idProduit);
            pageInventaireModif.putExtra("laLimite",laLimite);
            pageInventaireModif.putExtra("qteStock",qteStock);
            pageInventaireModif.putExtra("idInventaire",idInventaire);
            // affichage de la page
            startActivity(pageInventaireModif);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Modification du produit "+ sgbd.getLibelleProduit(idProduit) +" de l'inventaire", Toast.LENGTH_SHORT);
            // fermeture de la sgbd
            sgbd.close();
            // affichage du toast
            toast.show();
        }
        // si le bouton cliqué est retour
        if (id == R.id.btnRetourProdInvent){
            // affichage de la page a afficher
            Intent pageInventaireRetour = new Intent(this, VueInventaire.class);
            // affichage de la page
            startActivity(pageInventaireRetour);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Retour a la liste des produit de l'inventaire", Toast.LENGTH_SHORT);
            // fermeture de la sgbd
            sgbd.close();
            // affichage du toast
            toast.show();
        }
    }
}