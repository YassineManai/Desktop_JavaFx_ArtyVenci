package services;

import entities.ForumEntity;
import entities.PostEntity;
import utils.MyDBF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePostF implements IServiceF<PostEntity> {
    private Connection con;

    public ServicePostF(){
        con = MyDBF.getInstance().getConnection();
    }
    @Override
    public void ajouter(PostEntity postEntity) throws SQLException {
        String req="Insert into post(id_Forum,id_User,title,textmessage,like_number,timeofcreation) values(?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,postEntity.getId_forum());
        pre.setInt(2,postEntity.getId_user());
        pre.setString(3,postEntity.getTitle());
        pre.setString(4,postEntity.getText());
        pre.setInt(5,postEntity.getLike_num());
        pre.setTimestamp(6,postEntity.getTime());

        pre.executeUpdate();
        updateRepliesCount(postEntity.getId_forum(),1);
    }
    int getRepliesCount(long forumId) throws SQLException {
        String sqlGetRepliesCount = "SELECT replies_number FROM forum WHERE id_forum = ?";
        try (PreparedStatement statement = con.prepareStatement(sqlGetRepliesCount)) {
            statement.setLong(1, forumId);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return 0;
            } else {
                return rs.getInt("replies_number");
            }
        }
    }

    void updateRepliesCount(long forumId,int i) throws SQLException {
        int oldRepliesCount = getRepliesCount(forumId);
        int newRepliesCount = oldRepliesCount + i;
        String sqlUpdateRepliesCount = "UPDATE forum SET replies_number = ? WHERE id_forum = ?";
        try (PreparedStatement statement = con.prepareStatement(sqlUpdateRepliesCount)) {
            statement.setInt(1, newRepliesCount);
            statement.setLong(2, forumId);
            statement.executeUpdate();
        }
    }
    @Override
    public void modifier(PostEntity postEntity) throws SQLException {
        String req = "Update post set id_forum=? , id_user=? ,title=?,textmessage=?,like_number=?,timeofcreation=?  where id_post=?";

        PreparedStatement pre = con.prepareStatement(req);

        pre.setInt(1,postEntity.getId_forum());
        pre.setInt(2,postEntity.getId_user());
        pre.setString(3,postEntity.getTitle());
        pre.setString(4,postEntity.getText());
        pre.setInt(5,postEntity.getLike_num());
        pre.setTimestamp(6,postEntity.getTime());

        pre.setInt(7,postEntity.getId_post());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(PostEntity postEntity) throws SQLException {
        String req ="DELETE FROM post where id_post="+postEntity.getId_post()+";";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
        updateRepliesCount(postEntity.getId_forum(),-1);
    }

    @Override
    public List<PostEntity> afficher() throws SQLException {
        //Create List
        List<PostEntity> posts = new ArrayList<>();
        //
        String req = "SELECT * from post";

        PreparedStatement pre = con.prepareStatement(req);

        ResultSet res = pre.executeQuery();

        while(res.next())
        {
            PostEntity f = new PostEntity();
            f.setId_post(res.getInt(1));
            f.setId_forum(res.getInt(2));
            f.setId_user(res.getInt(3));
            f.setTitle(res.getString(4));
            f.setText(res.getString(5));
            f.setLike_num(res.getInt(6));
            f.setTime(res.getTimestamp(7));

            posts.add(f);
        }

        return posts;
    }

    public PostEntity getbyid(int id) throws SQLException {
        PostEntity f = null;
        String req = "SELECT * FROM post WHERE id_post=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            f.setId_post(res.getInt(1));
            f.setId_forum(res.getInt(2));
            f.setId_user(res.getInt(3));
            f.setTitle(res.getString(4));
            f.setText(res.getString(5));
            f.setLike_num(res.getInt(6));
            f.setTime(res.getTimestamp(7));
        }
        return f;
    }

    public int CountPosts() throws SQLException {
        int number=0;
        List<PostEntity> flist = new ArrayList<>(this.afficher());
        number = (int) flist.stream().count();
        return number;
    };
}
