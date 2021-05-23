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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ProduitCreate extends AppCompatActivity implements View.OnClickListener {

    // creation de la variable sgbd
    GestionBD sgbd;
    // instanciation de la liste de categorie via le SQL
    ArrayList<String> lesCategorieViaSQL = new ArrayList<String>();
    // creation du code cam_request
    private static final int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // instanciation de la sgbd
        sgbd = new GestionBD(this);
        // ouverture de la sgbd
        sgbd.open();
        //affichage de l'activité
        setContentView(R.layout.activity_produit_create);
        // recuperation de l'id des bouton
        Button btnAddProd = this.findViewById(R.id.btnAddProduit);
        Button btnCancelProd = this.findViewById(R.id.btnCancelProduit);
        ImageButton btnScanIdProd = this.findViewById(R.id.btnScanIdProdAddProduit);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnAddProd.setOnClickListener(this);
        btnCancelProd.setOnClickListener(this);
        btnScanIdProd.setOnClickListener(this);
        // recuperation des id des spinner
        Spinner spinLibelleCateg = this.findViewById(R.id.spinLibelleCategAddProduit);
        // ajout des libelle dans la liste
        lesCategorieViaSQL = sgbd.donneLesCategorie();
        // adapter du spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,lesCategorieViaSQL);
        spinLibelleCateg.setAdapter(adapter);
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
        // si le bouton cliqué est ajouter
        if (id == R.id.btnAddProduit){
            // recuperation de la page a afficher
            Intent pageProduitAdd = new Intent(this, VueProduit.class);
            // recuperation des id des editText
            EditText edIdProd = (EditText)findViewById(R.id.txtAddIdProd);
            EditText edLibelleProd = (EditText)findViewById(R.id.txtAddLibelleProd);
            EditText edPrixProd = (EditText)findViewById(R.id.txtAddPrixProd);
            EditText edDescriptionProd = (EditText)findViewById(R.id.txtAddDescriptionProd);
            // recuperation des id du spinner
            Spinner spinLibelleCateg = (Spinner)findViewById(R.id.spinLibelleCategAddProduit);
            // recuperation des valeurs
            String lIdProduit = edIdProd.getText().toString();
            String leLibelle = edLibelleProd.getText().toString();
            double lePrix = (double) Double.parseDouble(edPrixProd.getText().toString());
            String laDescription = edDescriptionProd.getText().toString();
            int lIdCategList = spinLibelleCateg.getSelectedItemPosition();
            // recuperation du libelle de la categorie
            String leLibelleCateg = lesCategorieViaSQL.get(lIdCategList);
            // recup de l'id de la categorie
            String lIdCateg = sgbd.getidCategCategorie(leLibelleCateg);
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdProduit  ajout produit : " + lIdProduit);
            Log.i("doInBack","Test leLibelle ajout produit  : " + leLibelle);
            Log.i("doInBack","Test lePrix ajout produit : " + lePrix);
            Log.i("doInBack","Test laDescription ajout produit : " + laDescription);
            Log.i("doInBack","Test lIdCateg ajout produit : " + lIdCateg);
            Log.i("doInBack","Test leLibelleCateg ajout produit : " + leLibelleCateg);
            // instanciation du produit
            Produits leProduit = new Produits(lIdProduit,leLibelle,lePrix,laDescription,lIdCateg);
            // methode pour ajouter des données dans la sgbd
            sgbd.ajouteProduit(leProduit);
            // fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageProduitAdd);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Ajout du produit " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est annuler
        if (id == R.id.btnCancelProduit){
            // recuperation de la page a afficher
            Intent pageProduitCancel = new Intent(this, VueProduit.class);
            // affichage de la page
            startActivity(pageProduitCancel);
            // fermeture de la sgbd
            sgbd.close();
            // fetmeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Abandon de l'ajout du produit", Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est scan
        if (id == R.id.btnScanIdProdAddProduit){
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
                EditText txtIdCatef = (EditText) findViewById(R.id.txtAddIdProd);
                txtIdCatef.setText(recup);
            }
        }
    }
}