package com.example.stockomatic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    // instanciation de 2 categories
    Categories c1 = new Categories("CA465","Jouet", "a l'unité", "endroit sec");
    Categories c2 = new Categories("CA795","Électronique", "a l'appareil", "lieux abrité");
    // instanciation de 2 produits
    Produits p1 = new Produits("PH245", "ordi", 100.00, "ordinateur", c1);
    Produits p2 = new Produits("ph246", "telephone", 550, "telephone portable", "Electronique");
    // instanciation de 2 inventaires
    Inventaires i1 = new Inventaires("IV565","Intermarché");
    Inventaires i2 = new Inventaires("OC874","E-leclerc");
    // instanciation de 5 ProdInvents
    ProdInvents pi1 = new ProdInvents("PH495","IV945",5,48);
    ProdInvents pi2 = new ProdInvents(p2,i1,55,99);
    ProdInvents pi3 = new ProdInvents(p1,i1,45,89);
    ProdInvents pi4 = new ProdInvents("PF487",i1,45,89);
    ProdInvents pi5 = new ProdInvents(p1,"OD842",45,89);

    @Test
    public void testCategorie(){
        // categorie
        // test getId
        assertEquals("Doit etre edgale à 'CA465'", "CA465", c1.getId());
        // test getLibelle
        assertEquals("Doit etre egale à 'Jouet'", "Jouet", c1.getLibelle());
        // test getTypeVente
        assertEquals("Doit etre egale à 'a l'unité'", "a l'unité", c1.getTypeVente());
        // test getTypeCond
        assertEquals("Doit etre egale à 'endroit sec'", "endroit sec", c1.getTypeCond());
        // categorie via produit
        // test getId via categorie
        assertEquals("Doit etre egale à 'CA465'", "CA465", p1.getIdCategorie().getId());
        // test getTypeVente via categorie
        assertEquals("Doit etre egale à 'a l'unité'", "a l'unité", p1.getIdCategorie().getTypeVente());
        // test getLibelle via categorie
        assertEquals("Doit etre egale à 'Jouet'", "Jouet", p1.getIdCategorie().getLibelle());
        // test getTypeCond via categorie
        assertEquals("Doit etre egale à 'endroit sec'", "endroit sec", p1.getIdCategorie().getTypeCond());
        // test getIdCateg via categorie
        assertEquals("Doit etre egale a null",null,p1.getIdCateg());
        // categorie via ProdInvent
        // test getId via ProdInvent
        assertEquals("Doit etre egale à 'CA465'", "CA465", pi3.getIdProd().getIdCategorie().getId());
        // test getTypeVente via ProdInvent
        assertEquals("Doit etre egale à 'a l'unité'", "a l'unité", pi3.getIdProd().getIdCategorie().getTypeVente());
        // test getLibelle via ProdInvent
        assertEquals("Doit etre egale à 'Jouet'", "Jouet", pi3.getIdProd().getIdCategorie().getLibelle());
        // test getTypeCond via ProdInvent
        assertEquals("Doit etre egale à 'endroit sec'", "endroit sec", pi3.getIdProd().getIdCategorie().getTypeCond());
        // categorie via toString
        // test toString
        assertEquals("Erreur de traitement" , "Réference catégorie : CA465 \n" +
                        "Libelle : Jouet \n" +
                        "Type de vente : a l'unité \n" +
                        "Type de conditionnement  : endroit sec \n" +
                        "\n"
                , c1.toString());
        // categorie via liste
        // ajout de c1 dans la liste
        c1.ajoutCategorie();
        // ajout de c2 dans la liste
        c2.ajoutCategorie();
        // test afficheCategorie
        assertEquals("Erreur de traitement", "Debut de la liste : \n\n" +
                        "Réference catégorie : CA465 \n" +
                        "Libelle : Jouet \n" +
                        "Type de vente : a l'unité \n" +
                        "Type de conditionnement  : endroit sec \n\n" +
                        "Réference catégorie : CA795 \n" +
                        "Libelle : Électronique \n" +
                        "Type de vente : a l'appareil \n" +
                        "Type de conditionnement  : lieux abrité \n\n" +
                        "Fin de la liste"
                , Categories.afficheCategorie());
        // suppresion de c2 dans la liste
        c2.supprCategorie();
        // instanciation d'une liste test
        ArrayList<Categories> altest = new ArrayList<Categories>();
        // ajout de c1 dans la liste test
        altest.add(c1);
        // test getLesCategories
        assertEquals("Erreur de traitement", altest, Categories.getLesCategories());
        // modification de id
        c1.setId("CA489");
        // test getId
        assertEquals("Doit etre edgale à 'CA489'", "CA489", c1.getId());
        // modfication de libelle
        c1.setLibelle("Corde");
        // test getLibelle
        assertEquals("Doit etre egale à 'Corde'", "Corde", c1.getLibelle());
        // modfication de typeVente
        c1.setTypeVente("au métre");
        // test getTypeVente
        assertEquals("Doit etre egale à 'au métre'", "au métre", c1.getTypeVente());
        // modification de typeCond
        c1.setTypeCond("n'importe ou");
        // test de typeCond
        assertEquals("Doit etre egale à 'n'importe ou'", "n'importe ou", c1.getTypeCond());

    }

    @Test
    public void testInventaire(){
        // inventaire
        // test getIdInventaire
        assertEquals("Doit etre egale à 'IV565'", "IV565", i1.getIdInventaire());
        // test getLibelleInventaire
        assertEquals("Doit etre egale à 'Intermarché'", "Intermarché", i1.getLibelleInventaire());
        // inventaire via ProdInvent
        // test getIdInventaire via ProdInvent
        assertEquals("Doit etre egale à 'IV565'", "IV565", pi2.getIdInvent().getIdInventaire());
        // test getLibelleInventaire via ProdInvent
        assertEquals("Doit etre egale à 'Intermarché'", "Intermarché", pi2.getIdInvent().getLibelleInventaire());
        // inventaire via toString
        // test toString
        assertEquals("Erreur de traitement" , "Réference inventaire : IV565 \n" +
                        "Libelle : Intermarché \n" +
                        "\n"
                , i1.toString());
        // inventaire via inventaire
        // ajout de i1 dans la liste
        i1.ajoutInventaire();
        // ajout de i2 dans la liste
        i2.ajoutInventaire();
        // test afficheInventaire
        assertEquals("Erreur de traitement", "Debut de la liste : \n\n" +
                        "Réference inventaire : IV565 \n" +
                        "Libelle : Intermarché \n\n" +
                        "Réference inventaire : OC874 \n" +
                        "Libelle : E-leclerc \n\n" +
                        "Fin de la liste"
                , Inventaires.afficheInventaire());
        // suppresion de i2 dans la liste
        i2.supprInventaire();
        // instnaciation de la liste test
        ArrayList<Inventaires> altest = new ArrayList<Inventaires>();
        // ajout de i1 dans la liste test
        altest.add(i1);
        // test getLesInventaires
        assertEquals("Erreur de traitement", altest, Inventaires.getLesInventaires());
        // modification de id
        i1.setId("OCS482");
        // test getIdInventaire
        assertEquals("Doit etre edgale à 'OCS482'", "OCS482", i1.getIdInventaire());
        // modification de libelle
        i1.setLibelleInventaire("Auchan");
        // test getLibelleInventaire
        assertEquals("Doit etre egale à 'Auchan'", "Auchan", i1.getLibelleInventaire());
    }

    @Test
    public void testProduit(){
        // produit
        // test getId
        assertEquals("Doit etre egale à 'ph246'", "ph246", p2.getId());
        // test getLibelleProduit
        assertEquals("Doit etre egale à 'telephone'", "telephone", p2.getLibelleProduit());
        // test getPrix
        assertEquals("Doit etre egale à '550'", 550, p2.getPrix(), 0);
        // test getDescription
        assertEquals("Doit etre egale à 'telephone portable'", "telephone portable", p2.getDescription());
        // test getIdCateg
        assertEquals("Doit etre egale à 'Electronique'", "Electronique", p2.getIdCateg());
        // produit via ProdInvent
        // test getId via ProdInvent
        assertEquals("Doit etre egale à 'ph246'", "ph246", pi2.getIdProd().getId());
        // test getLibelleProduit via ProdInvent
        assertEquals("Doit etre egale à 'telephone'", "telephone", pi2.getIdProd().getLibelleProduit());
        // test getPrix via ProdInvent
        assertEquals("Doit etre egale à '550'", 550, pi2.getIdProd().getPrix(), 0);
        // test getDescription via ProdInvent
        assertEquals("Doit etre egale à 'telephone portable'", "telephone portable", pi2.getIdProd().getDescription());
        // test getIdCateg via ProdInvent
        assertEquals("Doit etre egale à 'Electronique'", "Electronique", pi2.getIdProd().getIdCateg());
        // produit via toString
        // test toString
        assertEquals("Erreur de traitement" , "Réference Produit : PH245 \n" +
                        "Libelle : ordi \n" +
                        "Prix : 100.0 \n" +
                        "Description : ordinateur \n" +
                        "Categorie : Jouet \n" +
                        "\n"
                , p1.toString());
        //test toString
        assertEquals("Erreur de traitement" , "Réference Produit : ph246 \n" +
                        "Libelle : telephone \n" +
                        "Prix : 550.0 \n" +
                        "Description : telephone portable \n" +
                        "Categorie : Electronique \n" +
                        "\n"
                , p2.toString());
        // produit via liste
        // ajout de p1 dans la liste
        p1.ajoutProduit();
        // ajout de p2 dans la liste
        p2.ajoutProduit();
        // test afficheProduit
        assertEquals("Erreur de traitement", "Debut de la liste : \n\n" +
                        "Réference Produit : PH245 \n" +
                        "Libelle : ordi \n" +
                        "Prix : 100.0 \n" +
                        "Description : ordinateur \n" +
                        "Categorie : Jouet \n\n" +
                        "Réference Produit : ph246 \n" +
                        "Libelle : telephone \n" +
                        "Prix : 550.0 \n" +
                        "Description : telephone portable \n" +
                        "Categorie : Electronique \n\n" +
                        "Fin de la liste"
                , Produits.afficheProduit());
        // suppresion de p2 dans la liste
        p2.supprProduit();
        // instanciation de la liste test
        ArrayList<Produits> altest = new ArrayList<Produits>();
        // ajout de p1 dans la liste test
        altest.add(p1);
        // test getLesProduits
        assertEquals("Erreur de traitement", altest, Produits.getLesProduits());
        // modification de id
        p2.setId("PR8441");
        // test getId
        assertEquals("Doit etre edgale à 'PR8441'", "PR8441", p2.getId());
        // modification de libelle
        p2.setLibelleProduit("Corde");
        // test getLibelleProduit
        assertEquals("Doit etre egale à 'Corde'", "Corde", p2.getLibelleProduit());
        // modification de prix
        p2.setPrix(9999.99);
        // test getPrix
        assertEquals("Doit etre egale à '9999.99'", 9999.99, p2.getPrix(),0);
        // modification de description
        p2.setDescription("Corde en acier");
        // test getDescription
        assertEquals("Doit etre egale à 'Corde en acier'", "Corde en acier", p2.getDescription());
        // modification de idCateg
        p2.setIdcateg("ACV481");
        // test getIdCateg
        assertEquals("Doit etre egale à 'ACV481'", "ACV481", p2.getIdCateg());
        // test getIdCategorie
        assertEquals("Doit etre egale à 'null'", null, p2.getIdCategorie());
        // modification de idCategorie
        p2.setIdcategorie(c1);
        // test getIdCategorie via categorie
        assertEquals("Doit etre egale à 'ACV481'", "CA465", p2.getIdCategorie().getId());
        // test getIdCateg
        assertEquals("Doit etre egale à 'null'", null, p2.getIdCateg());
    }

    @Test
    public void testQteStock(){
        // ProdInvents
        // test getIdProduit
        assertEquals("Doit etre egale a 'PH495'", "PH495", pi1.getIdProduit());
        // test getIdInventaire
        assertEquals("Doit etre egale a 'IV945'", "IV945", pi1.getIdInventaire());
        // test getLimiteStockAlert
        assertEquals("Doit etre egale a '5'",5,pi1.getLimiteStockAlert());
        // test getQteStock
        assertEquals("Doit etre egale a '48'",48,pi1.getQteStock());
        // ProdInvents via toString
        // test toString
        assertEquals("Erreur de traitement" , "Référence Produit : PF487 \n" +
                        "Référence Inventaire : IV565 \n" +
                        "Limite Stock d'Alerte : 45 \n" +
                        "Quantité en stock : 89 \n" +
                        "\n"
                , pi4.toString());
        // test toString
        assertEquals("Erreur de traitement" , "Référence Produit : PH245 \n" +
                        "Référence Inventaire : OD842 \n" +
                        "Limite Stock d'Alerte : 45 \n" +
                        "Quantité en stock : 89 \n" +
                        "\n"
                , pi5.toString());
        // ProdInvents via liste
        // ajout de pi4 dans la liste
        pi4.ajoutProdInvent();
        // ajout de pi5 dans la liste
        pi5.ajoutProdInvent();
        // test afficheProdInvent
        assertEquals("Erreur de traitement", "Debut de la liste : \n\n" +
                        "Référence Produit : PF487 \n" +
                        "Référence Inventaire : IV565 \n" +
                        "Limite Stock d'Alerte : 45 \n" +
                        "Quantité en stock : 89 \n\n" +
                        "Référence Produit : PH245 \n" +
                        "Référence Inventaire : OD842 \n" +
                        "Limite Stock d'Alerte : 45 \n" +
                        "Quantité en stock : 89 \n\n" +
                        "Fin de la liste"
                , ProdInvents.afficheProdInvent());
        // suppresion de pi5 dans la liste
        pi5.supprProdInvent();
        // instanciation de la liste de test
        ArrayList<ProdInvents> altest = new ArrayList<ProdInvents>();
        // ajout de pi4 a la liste de test
        altest.add(pi4);
        // test getLesProdInvents
        assertEquals("Erreur de traitement", altest, ProdInvents.getLesProdInvents());
        // modification de idProd
        pi4.setIdProd(p1);
        // test getId via Produit
        assertEquals("Doit etre edgale à 'PH245'", "PH245", pi4.getIdProd().getId());
        // test getIdProduit
        assertEquals("Doit etre edgale à 'null'", null, pi4.getIdProduit());
        // modification de idProduit
        pi4.setIdProduit("PDF481");
        // test getIdProduit
        assertEquals("Doit etre egale à 'PDF481'", "PDF481", pi4.getIdProduit());
        // test getIdProd
        assertEquals("Doit etre edgale à 'null'", null, pi4.getIdProd());
        // modification de idInventaire
        pi4.setIdInventaire("OD4895");
        // test getIdInventaire
        assertEquals("Doit etre egale à 'OD4895'", "OD4895", pi4.getIdInventaire());
        // test getIdInvent
        assertEquals("Doit etre edgale à 'null'", null, pi4.getIdInvent());
        // modification de idInvent
        pi4.setIdInvent(i2);
        // test getIdInvent via Inventaire
        assertEquals("Doit etre egale à 'OC874'", "OC874", pi4.getIdInvent().getIdInventaire());
        // test getIdInventaire
        assertEquals("Doit etre edgale à 'null'", null, pi4.getIdInventaire());
        // modification de limiteStockAlert
        pi4.setLimiteStockAlert(150);
        // test getLimiteStockAlert
        assertEquals("Doit etre egale à '150'", 150, pi4.getLimiteStockAlert());
        // modification de qteStock
        pi4.setQteStock(487);
        // test getQteStock
        assertEquals("Doit etre egale à '487'", 487, pi4.getQteStock());
    }
}