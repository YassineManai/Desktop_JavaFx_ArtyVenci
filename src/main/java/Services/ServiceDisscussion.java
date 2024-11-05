package services;

import entities.Disscussion;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDisscussion implements IServiceF<Disscussion> {

    private Connection conn;

    public ServiceDisscussion() {conn = MyDB.getInstance().getConnection();}

    @Override
    public void ajouter(Disscussion disscussion) throws SQLException {
        String req = "INSERT INTO discussion (idReciever, idSender) VALUES (?, ? )";
        PreparedStatement pre = conn.prepareStatement(req);

        pre.setInt(2, disscussion.getIdSender());
        pre.setInt(1, disscussion.getIdReceiver());

        pre.executeUpdate();
//        pre.close();
    }

    public int CountDisc() throws SQLException {
        String UserReq = "SELECT COUNT(*) FROM `discussion` WHERE `Sig` IS NOT NULL ";
        try (PreparedStatement pre = conn.prepareStatement(UserReq)) {
            try (ResultSet resultSet = pre.executeQuery()) {
                if (resultSet.next()) {
                    int total = resultSet.getInt(1);
                    return total;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting users: " + e.getMessage());
            // Optionally, you may throw an exception or return a default value here
        }
        return 0; // Return 0 if an error occurs
    }



    @Override
    public void modifier(Disscussion disscussion) throws SQLException {

        String req = "UPDATE discussion SET  idSender=? , idReceiver =? , Sig =? WHERE idDis= ?";
        PreparedStatement pre = conn.prepareStatement(req);

        pre.setInt(1, disscussion.getIdSender());
        pre.setInt(2, disscussion.getIdReceiver());
        pre.setString(3, disscussion.getSignal());

        pre.setInt(4, disscussion.getIdDis());

        pre.executeUpdate();
        pre.close();
    }

    @Override
    public void supprimer(Disscussion disscussion) throws SQLException {
        String Req = "DELETE  FROM discussion WHERE idDis  = ?";
        try (PreparedStatement pre = conn.prepareStatement(Req)) {

            pre.setInt(1, disscussion.getIdDis());
            pre.executeUpdate();
            System.out.println("Discussion deleted successfully");
        }
        catch (SQLException e) {
            System.out.println("Error deleting message: " + e.getMessage());
        }
    }



    @Override
    public List<Disscussion> afficher() throws SQLException {
        List<Disscussion> dis = new ArrayList<>();

        String req = "SELECT idDis, idSender, idReciever , 'Sig'  FROM discussion";
        PreparedStatement pre = conn.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Disscussion d = new Disscussion() ;

            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReciever"));
            d.setSignal(res.getString("Sig"));

            dis.add(d);
    }
        return dis;

    }

    public Disscussion getDiscussionById (int id) throws SQLException{
        Disscussion d = new Disscussion() ;
        String req = "SELECT * FROM discussion WHERE idDis=?";

        PreparedStatement pre = conn.prepareStatement(req);
        pre.setInt(1, id);

        ResultSet res = pre.executeQuery();
        if (res.next()) {
            d = new Disscussion();

            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReciever"));
            d.setSignal(res.getString("Sig"));
        }

        return d ;
    }

    public Disscussion getDiscussionByReceiverId (int id  , int sender) throws SQLException{
        Disscussion d = new Disscussion() ;
        String req = "SELECT * FROM discussion WHERE idReciever=? AND idSender=? ";

        PreparedStatement pre = conn.prepareStatement(req);
        pre.setInt(1, id);
        pre.setInt(2, sender);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            d = new Disscussion();

            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReciever"));
            d.setSignal(res.getString("Sig"));
        }

        return d ;
    }



    public Disscussion getDisByContacts (String sender , String receiver) throws SQLException {
        Disscussion d = new Disscussion() ;
        String req = "SELECT * FROM discussion WHERE (idReciever=? AND idSender=? ) OR (idReciever=? AND idSender=?)";

        PreparedStatement pre = conn.prepareStatement(req);

        pre.setString(1, receiver);
        pre.setString(2, sender);
        pre.setString(4, receiver);
        pre.setString(3, sender);

        ResultSet res = pre.executeQuery();
        if (res.next()) {
            d = new Disscussion();

            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReciever"));
            d.setSignal(res.getString("Sig"));
        }
        return d ;
    }



    public List<Disscussion> getDisifByContacts(int sender) throws SQLException {
        List<Disscussion> dis = new ArrayList<>();
        String req = "SELECT * FROM discussion WHERE idReciever=? OR idSender=?";
        PreparedStatement pre = conn.prepareStatement(req);
        pre.setInt(1, sender);
        pre.setInt(2, sender);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Disscussion d = new Disscussion();
            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReciever"));
            d.setSignal(res.getString("Sig"));
            dis.add(d);
        }
        // Close the ResultSet and PreparedStatement
        res.close();
        pre.close();
        return dis;
    }


    public boolean Compare(Disscussion disscussion) throws SQLException {

        String req = "SELECT * FROM discussion WHERE idSender= ? AND idReciever= ?";
        PreparedStatement pre = conn.prepareStatement(req);

        pre.setInt(1, disscussion.getIdSender());
        pre.setInt(2, disscussion.getIdReceiver());

        ResultSet res = pre.executeQuery();

        pre.executeQuery();
        pre.close();

        if (res != null) return true ;
        else return false ;
//        return res != null;
    }

    public void signal (Disscussion disscussion  , String sig) throws SQLException{
        String req = "UPDATE discussion SET  Sig =? WHERE idDis= ?";

        PreparedStatement pre = conn.prepareStatement(req);

        pre.setString(1, sig);
        pre.setInt(2, disscussion.getIdDis());

        pre.executeUpdate();
        pre.close();
    }



    public List<Disscussion> afficherSig() throws SQLException {

        List<Disscussion> dis = new ArrayList<>();

        String req = "SELECT * FROM `discussion` WHERE `Sig` IS NOT NULL";
        PreparedStatement pre = conn.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Disscussion d = new Disscussion() ;

            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReciever"));
            d.setSignal(res.getString("Sig"));

            dis.add(d);
        }
        return dis;

    }


    public List<Disscussion> getByReceiver(int IdReceiver) throws SQLException {

        List<Disscussion> dis = new ArrayList<>();

        String req = "SELECT * FROM discussion WHERE idReciever= ?";
        PreparedStatement pre = conn.prepareStatement(req);

        pre.setInt(1, IdReceiver);

        ResultSet res = pre.executeQuery();

        pre.executeQuery();
        pre.close();

        return dis ;

    }

    public void annulerSignal(Disscussion disscussion) throws SQLException {
        String Req = "UPDATE discussion SET  Sig =NULL WHERE idDis= ?";
        try (PreparedStatement pre = conn.prepareStatement(Req)) {

            pre.setInt(1, disscussion.getIdDis());
            pre.executeUpdate();
            System.out.println("signal Discussion deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error canceling message: " + e.getMessage());
        }
    }

}
