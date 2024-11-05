package services;

import entities.Disscussion;
import entities.Message;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessage implements IServiceF<Message>{

    private Connection conn;

    public ServiceMessage() {
        conn = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(Message message) throws SQLException {
        String req = "INSERT INTO message ( datasent , idSender , idDis , content, reaction) VALUES (?, ? , ?, ?, ?)";
        PreparedStatement pre = conn.prepareStatement(req);
        pre.setTimestamp(1, message.getTime());
        pre.setInt(2, message.getIdSender());
        pre.setInt(3, message.getIdDis());
        pre.setString(4, message.getContent());
        pre.setString(5, message.getReaction());

        pre.executeUpdate();
        pre.close();
    }

    @Override
    public void modifier(Message message) throws SQLException {
        String req = "UPDATE message SET  dataSent = ? , idSender=? , idDis =? , content=?, reaction=? WHERE idMsg= ?";
        PreparedStatement pre = conn.prepareStatement(req);
        pre.setTimestamp(1, message.getTime());
        pre.setInt(2, message.getIdSender());
        pre.setInt(3, message.getIdDis());
        pre.setString(4, message.getContent());
        pre.setString(5, message.getReaction());
        pre.setInt(6, message.getIdMsg());

        pre.executeUpdate();
        pre.close();
    }

    @Override
    public void supprimer(Message message) throws SQLException {
        String Req = "DELETE FROM message WHERE IdMsg  = ?";
        try (PreparedStatement pre = conn.prepareStatement(Req)) {
            
            pre.setInt(1, message.getIdMsg());
            System.out.println(message.getIdMsg());
            pre.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error deleting message: " + e.getMessage());
        }
    }

    public void supprimerDis(Disscussion disscussion) throws SQLException {
        String Req = "DELETE FROM message WHERE idDis  = ?";
        try (PreparedStatement pre = conn.prepareStatement(Req)) {

            pre.setInt(1, disscussion.getIdDis());
            System.out.println(disscussion.getIdDis());
            pre.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error deleting message: " + e.getMessage());
        }
    }


    @Override
    public List<Message> afficher() throws SQLException {
        List<Message> msg = new ArrayList<>();
        String req = "SELECT idMsg, datasent , idSender, idDis ,  content, reaction FROM message";
        PreparedStatement pre = conn.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Message m = new Message();
            m.setIdMsg(res.getInt("idMsg"));
            m.setTime(res.getTimestamp("datasent"));
            m.setIdSender(res.getInt("idSender"));
            m.setIdDis(res.getInt("idDis"));
            m.setContent(res.getString("content"));
            m.setReaction(res.getString("reaction"));
            msg.add(m);
        }
        return msg;
    }

    public List<Message> afficheridDis(int idDis) throws SQLException {
        List<Message> msg = new ArrayList<>();
        String req = "SELECT idMsg, datasent, idSender, idDis ,  content, reaction FROM message where idDis = ?";
        PreparedStatement pre = conn.prepareStatement(req);
        pre.setInt(1, idDis);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Message m = new Message();
            m.setIdMsg(res.getInt("idMsg"));
            m.setTime(res.getTimestamp("datasent"));
            m.setIdSender(res.getInt("idSender"));
            m.setIdDis(res.getInt("idDis"));
            m.setContent(res.getString("content"));
            m.setReaction(res.getString("reaction"));
            msg.add(m);
        }
        return msg;
    }

    public Message getContentById (int id) throws SQLException{
        Message m = new Message();
        String req = "SELECT * FROM message WHERE idmsg=?";

            PreparedStatement pre = conn.prepareStatement(req);
            pre.setInt(1, id);

            ResultSet res = pre.executeQuery();
            if (res.next()) {
                m = new Message();
                m.setIdMsg(res.getInt("idmsg"));
                m.setContent(res.getString("content"));
                m.setTime(res.getTimestamp("datasent"));
                m.setIdSender(res.getInt("idSender"));
                m.setIdDis(res.getInt("idDis"));
                m.setContent(res.getString("content"));
                m.setReaction(res.getString("reaction"));
            }

        return m ;
    }


}
