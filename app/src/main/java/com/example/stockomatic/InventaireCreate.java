package com.example.stockomatic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InventaireCreate extends AppCompatActivity implements View.OnClickListener{

    // creation de la variable sgbd
    GestionBD sgbd;
    // creation du code cam_request
    private static final int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        // affichage de l'activité
        setContentView(R.layout.activity_inventaire_create);
        // recuperation des id des boutons
        Button btnAddInventaire = this.findViewById(R.id.btnAddInventaire);
        Button btnCancelInventaire = this.findViewById(R.id.btnCancelInventaire);
        ImageButton btnScanIdInventaire = this.findViewById(R.id.btnScanIdInventaireAddInvent);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnAddInventaire.setOnClickListener(this);
        btnCancelInventaire.setOnClickListener(this);
        btnScanIdInventaire.setOnClickListener(this);
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
        if (id == R.id.btnAddInventaire){
            // recuperation de la page a afficher
            Intent pageInventaireAdd = new Intent(this, VueInventaire.class);
            // recuperation des id des editText
            EditText edIdInvent = (EditText)findViewById(R.id.txtAddIdIvent);
            EditText edLibelleInvent = (EditText)findViewById(R.id.txtAddLibelleInventaire);
            // recuperation des valeurs
            String lIdInvent = edIdInvent.getText().toString();
            String leLibelle = edLibelleInvent.getText().toString();
            // instanciation de l'inventaire
            Inventaires lInvent = new Inventaires(lIdInvent,leLibelle);
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdInvent  ajout inventaire : " + lIdInvent);
            Log.i("doInBack","Test leLibelle ajout inventaire  : " + leLibelle);
            // methode d'ajout de données dans la sgbd
            sgbd.ajouteInventaire(lInvent);
            // fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageInventaireAdd);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Ajout de l'inventaire " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est annuler
        if (id == R.id.btnCancelInventaire){
            // recuperation de la page a afficher
            Intent pageInventaireCancel = new Intent(this, VueInventaire.class);
            // affichage de la page
            startActivity(pageInventaireCancel);
            // fermeture de la sgbd
            sgbd.close();
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Abandon de l'ajout de l'inventaire", Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est scan
        if (id == R.id.btnScanIdInventaireAddInvent) {
            int permissioncheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
            //verif de l'autorisation
            if (permissioncheck == PackageManager.PERMISSION_GRANTED){
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                //Parametrage de l'intent de gestion des codes
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt(String.valueOf(R.string.prompt));
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},CAM_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Echec récuperation", Toast.LENGTH_LONG).show();
            }
            else{
                String recup = result.getContents();
                if (recup.startsWith("http")){
                    Uri uri = Uri.parse(recup);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
                EditText txtIdCatef = (EditText) findViewById(R.id.txtAddIdIvent);
                txtIdCatef.setText(recup);
            }
        }
    }
}