package entities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Auction {
    int id_artist;

    private String cheminImageProduit;

    int id , id_produit;
    String nom;
    LocalDate date_lancement , date_cloture ;
    int prix_initial , prix_final ;


    public Auction( String nom, LocalDate date_cloture, LocalDate date_lancement,  int prix_initial , int id_produit , int id_artist) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.date_lancement = date_lancement;
        this.date_cloture = date_cloture;
        this.prix_initial = prix_initial;

        this.id_artist = id_artist;
    }
    public Auction(String nom, LocalDate date_cloture, LocalDate date_lancement, int prix_initial, int id_produit) {
        this.nom= nom;
        this.date_cloture=date_cloture;
        this.date_lancement=date_lancement;
        this.prix_initial= prix_initial;
        this.id_produit=id_produit;

    }
    public Auction(  String nom, LocalDate date_cloture, LocalDate date_lancement,  int prix_initial) {
        this.nom = nom;
        this.date_lancement = date_lancement;
        this.date_cloture = date_cloture;
        this.prix_initial = prix_initial;
  }

    public Auction( int id ,  String nom,  int prix_initial, int id_produit) {
        this.id=id;
        this.id_produit = id_produit;
        this.nom = nom;
        this.prix_initial = prix_initial;
    }

    public Auction() {

    }



    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    public String getCheminImageProduit() {
        return cheminImageProduit;
    }

    public void setCheminImageProduit(String cheminImageProduit) {
        this.cheminImageProduit = cheminImageProduit;
    }

    public LocalDate getDate_cloture() {
        return date_cloture;
    }

    public void setDate_cloture(LocalDate date_cloture) {
        this.date_cloture = date_cloture;
    }

    public LocalDate getDate_lancement() {
        return date_lancement;
    }

    public void setDate_lancement(LocalDate date_lancement) {
        this.date_lancement = date_lancement;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getPrix_final() {
        return prix_final;
    }

    public void setPrix_final(int prix_final) {
        this.prix_final = prix_final;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", id_produit=" + id_produit +
                ", date_cloture=" + date_cloture +
                ", date_lancement=" + date_lancement +
                ", prix_initial=" + prix_initial +
                ", prix_final=" + prix_final +
                '}';
    }


}
