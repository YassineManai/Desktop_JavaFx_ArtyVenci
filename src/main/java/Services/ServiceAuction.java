package services;

import entities.Auction;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import utils.MyDB;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ServiceAuction implements IServiceF<Auction>{

    private Connection con;

    public ServiceAuction(){
        con = MyDB.getInstance().getConnection();
    }


    @Override
    public void ajouter(Auction auction) throws SQLException {
        int id = 1 ;
        String query = "INSERT INTO auction (nom, date_cloture, date_lancement, prix_initial, id_produit, id_artist) VALUES (?, ?, ?, ?, ? ,?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, auction.getNom());
            preparedStatement.setDate(2, Date.valueOf(auction.getDate_cloture()));
            preparedStatement.setDate(3, Date.valueOf(auction.getDate_lancement()));
            preparedStatement.setInt(4, auction.getPrix_initial());
            preparedStatement.setInt(5, auction.getId_produit());
            preparedStatement.setInt(6,id);
            preparedStatement.executeUpdate();
        }
    }

    public void ajouter_by_artist(Auction auction, int id_user) throws SQLException {

        String query = "INSERT INTO auction (nom, date_cloture, date_lancement, prix_initial, id_produit, id_artist) VALUES (?, ?, ?, ?, ? ,?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, auction.getNom());
            preparedStatement.setDate(2, Date.valueOf(auction.getDate_cloture()));
            preparedStatement.setDate(3, Date.valueOf(auction.getDate_lancement()));
            preparedStatement.setInt(4, auction.getPrix_initial());
            preparedStatement.setInt(5, auction.getId_produit());
            preparedStatement.setInt(6,id_user);
            preparedStatement.executeUpdate();
        }
    }


    //hedhy tbadali auction lkol kif yheb l artiste yaaml modification aala auction mte3ou
    @Override
    public void modifier(Auction auction) throws SQLException {
        String req = "update Auction set nom=? , date_cloture=? , date_lancement=? , prix_initial=? , prix_final=? where id_Auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, auction.getNom());
        pre.setString(2, String.valueOf(Date.valueOf(auction.getDate_cloture())));
        pre.setString(3, String.valueOf(Date.valueOf(auction.getDate_lancement())));
        pre.setInt(4, auction.getPrix_initial());
        pre.setInt(5, auction.getPrix_final());
        pre.setInt(6, auction.getId());
        pre.executeUpdate();
    }


    @Override
    public void supprimer(Auction auction) throws SQLException{
        String req = "delete from Auction where id_auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, auction.getId());
        pre.executeUpdate();
    }

    public void supprimer_auction(int id_auction) throws SQLException{
        String req = "delete from Auction where id_auction=?";
        try{
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id_auction);
            pre.executeUpdate();
        }catch (SQLException e ){
            e.getMessage();
        }

    }

    //hedhy traja3 liste mtaa l auctions lkol
    @Override
    public List<Auction> afficher() throws SQLException {
        List<Auction> list = new ArrayList<>();
        String req = "select * from Auction";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            Auction a = new Auction();
            a.setId(res.getInt(1));
            a.setNom(res.getString("nom"));
            a.setDate_cloture(res.getDate("date_cloture").toLocalDate());
            a.setDate_lancement(res.getDate("date_lancement").toLocalDate());
            a.setPrix_initial(res.getInt("prix_initial"));
            a.setPrix_final(res.getInt("prix_final"));
            a.setId_produit(res.getInt("id_produit"));
            a.setId_artist(res.getInt("id_artist"));
            list.add(a);
        }
        return list;
    }
    //hedhy traja3li liste mtaa product mtaa user mou3ayen
    public List<String> loadProductValuesFromDatabase(int id) throws SQLException {
        List<String> productNames = new ArrayList<>();
        String req = "SELECT Title FROM product WHERE Id_User=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, id);
            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    String productName = res.getString("Title");
                    productNames.add(productName);
                }
            }
        }
        return productNames;
    }
    //hedhy traja3li esm product mtaa auction mou3ayen
    public String loadProductFromDatabase(int encher_id) throws SQLException {
        String req = "SELECT Title FROM product JOIN auction ON auction.id_produit = product.id_product WHERE id_Auction = ?";
        String productName = null;
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, encher_id);
            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    productName = res.getString("Title");
                }
            }
        }
        return productName;
    }
    //hedhy traja3 l id_product a partir men esmou
    public int getProductID(String productName) throws SQLException {
        String query = "SELECT Id_Product FROM product WHERE Title = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, productName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Id_Product");
                } else {
                    throw new SQLException("Product ID not found for product: " + productName);
                }
            }
        }
    }



    //hedhy tbadel l prix_final
    public void modifierPrixFinal(Auction auction) throws SQLException {
        ServiceParticipant serviceParticipant = new ServiceParticipant();
    float dernierPrix = serviceParticipant.getDernierPrix(auction.getId());
        String updateFinalPrice = "UPDATE Auction SET prix_final = ? WHERE id_Auction = ?";

        try (PreparedStatement updatePrixFinal = con.prepareStatement(updateFinalPrice)) {
            updatePrixFinal.setDouble(1,dernierPrix );
            updatePrixFinal.setInt(2, auction.getId());
            updatePrixFinal.executeUpdate();
        }catch (SQLException e ){
            e.getMessage();
        }
}
    //juste pour le test
    public float selectPrixFinal(int id_auction) throws SQLException {
        String req = "SELECT prix_final FROM auction WHERE id_Auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_auction);
        ResultSet resultSet = pre.executeQuery();

        if (resultSet.next()) {
            float prix_final = resultSet.getFloat("prix_final");
            return prix_final;
        } else {
            // Handle the case when there is no result
            throw new SQLException("No result found for auction with ID: " + id_auction);
            // or return a default value
            // return 0.0f;
        }
    }



    //hehdy traja3 auction b taswira
    public Auction getById(int id) throws SQLException {
        String req = "SELECT a.id_auction, p.ProductImage, a.nom, a.date_cloture, a.date_lancement, a.prix_initial, a.prix_final, a.id_produit, a.id_artist FROM auction a " +
                "JOIN product p ON a.id_produit = p.id_product " +
                "WHERE a.id_auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);

        ResultSet resultSet = pre.executeQuery();
        Auction auction = new Auction();

        if (resultSet.next()) {
            auction.setId(resultSet.getInt("id_auction"));
            auction.setNom(resultSet.getString("nom"));

            // Check for NULL before converting to LocalDate
            Date dateLancement = resultSet.getDate("date_lancement");
            if (dateLancement != null) {
                auction.setDate_lancement(dateLancement.toLocalDate());
            }

            auction.setPrix_initial(resultSet.getInt("prix_initial"));
            auction.setPrix_final(resultSet.getInt("prix_final"));
            auction.setId_produit(resultSet.getInt("id_produit"));
            auction.setId_artist(resultSet.getInt("id_artist"));
            auction.setCheminImageProduit(resultSet.getString("ProductImage"));
        }

        return auction;
    }

    //hedhy traja3 taswiret l produit mtaa auction
    public String loadImageFromDatabase(int id_produit) {
        String imagePath = "";

        try {
            String req= "SELECT ProductImage FROM product WHERE id_product=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1,id_produit);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                imagePath = resultSet.getString("ProductImage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imagePath;
    }


    public List<Auction> getMesEnchers(int idArtist) throws SQLException {
        List<Auction> list = new ArrayList<>();
        String req = "select * from Auction where id_artist=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,idArtist);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            Auction a = new Auction();
            a.setId(res.getInt(1));
            a.setNom(res.getString("nom"));
            a.setDate_cloture(res.getDate("date_cloture").toLocalDate());
            a.setDate_lancement(res.getDate("date_lancement").toLocalDate());
            a.setPrix_initial(res.getInt("prix_initial"));
            a.setPrix_final(res.getInt("prix_final"));
            a.setId_produit(res.getInt("id_produit"));
            a.setId_artist(res.getInt("id_artist"));
            list.add(a);
        }
        return list;
    }

    public List<Auction> getAutresEncheres(int idArtist) throws SQLException {
        List<Auction> list = new ArrayList<>();
        String req = "select * from Auction where id_artist!=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,idArtist);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            Auction a = new Auction();
            a.setId(res.getInt(1));
            a.setNom(res.getString("nom"));
            a.setDate_cloture(res.getDate("date_cloture").toLocalDate());
            a.setDate_lancement(res.getDate("date_lancement").toLocalDate());
            a.setPrix_initial(res.getInt("prix_initial"));
            a.setPrix_final(res.getInt("prix_final"));
            a.setId_produit(res.getInt("id_produit"));
            a.setId_artist(res.getInt("id_artist"));
            list.add(a);
        }
        return list;
    }


    public int getSituation(Auction auction) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateDebut = auction.getDate_lancement();
        LocalDate dateFin = auction.getDate_cloture();

        if (currentDate.isBefore(dateDebut)) {
            return -1;
        } else if ((currentDate.isEqual(dateDebut) || currentDate.isAfter(dateDebut)) && (currentDate.isEqual(dateFin) || currentDate.isBefore(dateFin)) ){
            return 0;
        } else if(currentDate.isAfter(dateFin) ) {
            return 1;
        }
        return 2;
    }

    public Double getPrixFinal(Auction auc) throws SQLException {
        String req = "select prix_final from auction where id_Auction =? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, auc.getId());
        ResultSet resultSet = pre.executeQuery();

        float prixFinal = 0.0F;

        while (resultSet.next()) {
            prixFinal = resultSet.getFloat("prix_final");
        }

        return Double.valueOf(prixFinal);
    }

    public String getNomProduit(int id_auction) throws SQLException {
        String nomProduit = null; // Initialize to null or any default value
        String req = "SELECT Title FROM product p JOIN auction a ON p.id_Product = a.id_produit WHERE a.id_auction=?";

        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, id_auction);

            try (ResultSet resultSet = pre.executeQuery()) {
                if (resultSet.next()) {
                    nomProduit = resultSet.getString("Title");
                }
            }
        }

        return nomProduit;
    }


    public int countAuction() throws SQLException {
        int count= 0;
        try {
            String req ="select COUNT(*) as number_auction from auction ";
            PreparedStatement pre = con.prepareStatement(req);
            ResultSet resultSet = pre.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("number_auction");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return count;
    }

    public String getRole(int idUser) throws SQLException {
        String role ="";
        String req = "select Role from user where id_User=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,idUser);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            role = res.getString("Role");
        }
        return role;
    }

    public int CountAuction() throws SQLException {
        String Auction = "SELECT COUNT(*) FROM Auction";
        try (PreparedStatement pre = con.prepareStatement(Auction)) {
            try (ResultSet resultSet = pre.executeQuery()) {
                if (resultSet.next()) {
                    int total = resultSet.getInt(1);
                    return total;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting Auction: " + e.getMessage());
            // Optionally, you may throw an exception or return a default value here
        }
        return 0; // Return 0 if an error occurs
    }
}
