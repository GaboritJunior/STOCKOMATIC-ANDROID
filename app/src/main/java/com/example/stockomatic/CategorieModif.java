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

public class CategorieModif extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_categorie_modif);
        // instanciation du bundle
        Bundle extras = getIntent().getExtras();
        // recuperation des valeurs via le bundle
        String lIdCateg = extras.getString("lId");
        String leLibelle = extras.getString("leLibelle");
        String typeVente = extras.getString("typeVente");
        String typeCond = extras.getString("typeCond");
        // test dans le logcat pour verifier que les valeurs sont bien récupérer
        Log.i("doInBack","Test lIdCateg modification categorie : " + lIdCateg);
        Log.i("doInBack","Test leLibelle modification categorie : " + leLibelle);
        Log.i("doInBack","Test typeVente modification categorie : " + typeVente);
        Log.i("doInBack","Test typeCond modification categorie : " + typeCond);
        // recuperation des id des boutons
        Button btnModifCateg = this.findViewById(R.id.btnModifCategorie);
        Button btnCancelCateg = this.findViewById(R.id.btnCancelCategorie);
        ImageButton btnScanIdCateg = this.findViewById(R.id.btnScanIdCategModifCateg);
        // recuperation des id des EditText
        EditText edIdCateg = (EditText)findViewById(R.id.txtModifIdCateg);
        EditText edLibelle = (EditText)findViewById(R.id.txtModifLibelleCateg);
        EditText edTypeVente = (EditText)findViewById(R.id.txtModifTypeVenteCateg);
        EditText edTypeCond = (EditText)findViewById(R.id.txtModifTypeCondCateg);
        // ajout des valeurs dans les EditText
        edIdCateg.setText(lIdCateg);
        edLibelle.setText(leLibelle);
        edTypeVente.setText(typeVente);
        edTypeCond.setText(typeCond);
        // mise en place de setOnClickListener pour pouvoir faire une action en cliquant sur les boutons
        btnModifCateg.setOnClickListener(this);
        btnCancelCateg.setOnClickListener(this);
        btnScanIdCateg.setOnClickListener(this);
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
        // si le bouton cliqué est modifier
        if (id == R.id.btnModifCategorie){
            // recuperation de la page a afficher
            Intent pageCategorieModif = new Intent(this, VueCategorie.class);
            // recuperation des id des editText
            EditText edIdCateg = (EditText)findViewById(R.id.txtModifIdCateg);
            EditText edLibelle = (EditText)findViewById(R.id.txtModifLibelleCateg);
            EditText edTypeVente = (EditText)findViewById(R.id.txtModifTypeVenteCateg);
            EditText edTypeCond = (EditText)findViewById(R.id.txtModifTypeCondCateg);
            // instanciation du bundle
            Bundle extras = getIntent().getExtras();
            // recuperation des valeurs via le bundle
            String lIdCategOriginal = extras.getString("lId");
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdCategOriginal modification categorie : " + lIdCategOriginal);
            // recuperation des valeurs
            String lIdCateg = edIdCateg.getText().toString();
            String leLibelle = edLibelle.getText().toString();
            String typeVente = edTypeVente.getText().toString();
            String typeCond = edTypeCond.getText().toString();
            // test dans le logcat pour verifier que les valeurs sont bien récupérer
            Log.i("doInBack","Test lIdCateg modification categorie : " + lIdCateg);
            Log.i("doInBack","Test leLibelle modification categorie : " + leLibelle);
            Log.i("doInBack","Test typeVente modification categorie : " + typeVente);
            Log.i("doInBack","Test typeCond modification categorie : " + typeCond);
            // creation d'une instance de categorie
            Categories laCategorie = new Categories(lIdCateg,leLibelle,typeVente,typeCond);
            // methode de modification de données dans la sgbd
            sgbd.ModifCategorie(laCategorie,lIdCategOriginal);
            // fermeture de la sgbd
            sgbd.close();
            // affichage de la page
            startActivity(pageCategorieModif);
            // fermeture de la page en cours
            this.finish();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Modification de la categorie " + leLibelle, Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué esr annuler
        if (id == R.id.btnCancelCategorie){
            // recuperation de la page a afficher
            Intent pageCategorieCancel = new Intent(this, VueCategorie.class);
            // affichage de la page
            startActivity(pageCategorieCancel);
            // fermeture de la page en cours
            this.finish();
            // fermeture de la sgbd
            sgbd.close();
            // instanciation du toast
            Toast toast = Toast.makeText(getApplicationContext(),"Abandon de la modification de la categorie", Toast.LENGTH_SHORT);
            // affichage d'un toast
            toast.show();
        }
        // si le bouton cliqué est scan
        if (id == R.id.btnScanIdCategModifCateg) {
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
                EditText txtIdCatef = (EditText) findViewById(R.id.txtModifIdCateg);
                txtIdCatef.setText(recup);
            }
        }
    }
}