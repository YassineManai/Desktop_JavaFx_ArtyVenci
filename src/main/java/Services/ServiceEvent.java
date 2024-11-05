package services;
import entities.Event;
import utils.MyDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvent implements IServiceE<Event> {
    private final Connection cnx;

    public ServiceEvent() {
        cnx = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Event event) {
        try {
            String query = "INSERT INTO event (E_Name, Place, E_Date, Ticket_Price, image) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, event.getE_Name());
            pst.setString(2, event.getPlace());
            pst.setTimestamp(3, new Timestamp(event.getE_Date().getTime()));
            pst.setDouble(4, event.getTicket_Price());
            pst.setString(5, event.getImage());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }


    public int CountMesg() throws SQLException {
        String UserReq = "SELECT COUNT(*) FROM event";
        try (PreparedStatement pre = cnx.prepareStatement(UserReq)) {
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
    public void modifier(Event event) {
        try {
            String query = "UPDATE event SET E_Name=?, Place=?, E_Date=?, Ticket_Price=?, image=? WHERE Id_Event=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, event.getE_Name());
            pst.setString(2, event.getPlace());
            pst.setDate(3, new Date(event.getE_Date().getTime()));
            pst.setDouble(4, event.getTicket_Price());
            pst.setString(5, event.getImage());
            pst.setInt(6, event.getId_Event());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public void supprimer(Event event) {
        try {
            String query = "DELETE FROM event WHERE Id_Event=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, event.getId_Event());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public List<Event> afficher() {
        List<Event> eventList = new ArrayList<>();
        try {
            String query = "SELECT * FROM event";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Event e = new Event();
                e.setId_Event(rs.getInt("Id_Event"));
                e.setE_Name(rs.getString("E_Name"));
                e.setPlace(rs.getString("Place"));
                e.setE_Date(new java.util.Date(rs.getDate("E_Date").getTime()));
                e.setTicket_Price(rs.getDouble("Ticket_Price"));
                e.setImage(rs.getString("image"));
                eventList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return eventList;
    }
    public List<String> getEventIdsAndNames(){
        List<String> eventList = new ArrayList<>();
        try {
            String query = "SELECT Id_Event, E_Name FROM event";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id=rs.getInt("Id_Event");
                String name=rs.getString("E_Name");

                eventList.add(id+" - "+name);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return eventList;
    }

}
