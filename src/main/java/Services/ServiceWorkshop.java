package services;
import entities.Workshop;
import utils.MyDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceWorkshop implements IServiceE<Workshop> {
    private final Connection cnx;

    public ServiceWorkshop() {
        cnx = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Workshop workshop) {
        try {
            String query = "INSERT INTO workshop (Title, Details, image, Id_Event) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, workshop.getTitle());
            pst.setString(2, workshop.getDetails());
            pst.setString(3, workshop.getImage());
            pst.setInt(4, workshop.getId_Event());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public void modifier(Workshop workshop) {
        try {
            String query = "UPDATE workshop SET Title=?, Details=?, image=?, Id_Event=? WHERE Id_Workshop=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, workshop.getTitle());
            pst.setString(2, workshop.getDetails());
            pst.setString(3, workshop.getImage());
            pst.setInt(4, workshop.getId_Event());
            pst.setInt(5, workshop.getId_Workshop());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public void supprimer(Workshop workshop) {
        try {
            String query = "DELETE FROM workshop WHERE Id_Workshop=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, workshop.getId_Workshop());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public List<Workshop> afficher() {
        List<Workshop> workshopList = new ArrayList<>();
        try {
            String query = "SELECT * FROM workshop";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Workshop w = new Workshop();
                w.setId_Workshop(rs.getInt("Id_Workshop"));
                w.setTitle(rs.getString("Title"));
                w.setDetails(rs.getString("Details"));
                w.setImage(rs.getString("image"));
                w.setId_Event(rs.getInt("Id_Event"));
                workshopList.add(w);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return workshopList;
    }
    public List<Workshop> afficherParIdEvent(int idEvent){
        /*List<Workshop> lw=new ArrayList<>();
        for(Workshop w:afficher()){
            if(w.getId_Event()==idEvent){
                lw.add(w);
            }
        }
        return lw;*/
        return afficher().stream().filter(w->w.getId_Event()==idEvent).collect(Collectors.toList());
    }
}
