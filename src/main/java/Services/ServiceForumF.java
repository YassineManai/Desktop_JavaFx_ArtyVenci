package services;
import entities.ForumEntity;
import utils.MyDBF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceForumF implements IServiceF<ForumEntity> {
    private Connection con;

    public ServiceForumF(){
        con = MyDBF.getInstance().getConnection();
    }

    @Override
    public void ajouter(ForumEntity forumEntity) throws SQLException {
        String req="Insert into forum(id_Forum,title,description,replies_number,date) values(?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,forumEntity.getId_forum());
        pre.setString(2,forumEntity.getTitle());
        pre.setString(3,forumEntity.getDescription());
        pre.setInt(4,forumEntity.getReplies_num());
        java.sql.Date mySQLDate = new java.sql.Date(forumEntity.getDate().getTime());
        pre.setDate(5,mySQLDate);
        pre.executeUpdate();
    }

    @Override
    public void modifier(ForumEntity forumEntity) throws SQLException {
        String req = "Update forum set title=? , description=?,replies_number=?,date=?  where id_forum=?";

        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1,forumEntity.getTitle());
        pre.setString(2,forumEntity.getDescription());
        pre.setInt(3,forumEntity.getReplies_num());
        java.sql.Date mySQLDate = new java.sql.Date(forumEntity.getDate().getTime());
        pre.setDate(4,mySQLDate);
        pre.setInt(5,forumEntity.getId_forum());
        pre.executeUpdate();


        pre.executeUpdate();
    }

    @Override
    public void supprimer(ForumEntity forumEntity) throws SQLException {
        String req ="DELETE FROM forum where id_forum="+forumEntity.getId_forum()+";";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public List<ForumEntity> afficher() throws SQLException {
        //Create List
        List<ForumEntity> forums = new ArrayList<>();
        //
        String req = "SELECT * from forum";

        PreparedStatement pre = con.prepareStatement(req);

        ResultSet res = pre.executeQuery();

        while(res.next())
        {
            ForumEntity f = new ForumEntity();
            f.setId_forum(res.getInt(1));
            f.setTitle(res.getString("title"));
            f.setDescription(res.getString("description"));
            f.setReplies_num(res.getInt("replies_number"));
            f.setDate(res.getDate(5));
            forums.add(f);
        }

        return forums;
    }
    public ForumEntity getbyid(int id) throws SQLException {
        ForumEntity f = null;
        String req = "SELECT * FROM forum WHERE id_forum=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            f = new ForumEntity();
            f.setId_forum(res.getInt("id_forum"));
            f.setTitle(res.getString("title"));
            f.setDescription(res.getString("description"));
            f.setReplies_num(res.getInt("Replies_number"));
            f.setDate(res.getDate("date"));
        }
        return f;
    }

    public int CountForums() throws SQLException {
        int number=0;
        List<ForumEntity> flist = new ArrayList<>(this.afficher());
        number = (int) flist.stream().count();
        return number;
    };
}
