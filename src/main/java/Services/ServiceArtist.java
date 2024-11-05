/*package services;

import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtist implements IService<Artist>{
    private Connection con;

    public ServiceArtist(){
        con = MyDB.getInstance().getConnection();
    }
    @Override


    public void ADD(Artist artist) throws SQLException {



        // Add user
        String userReq = "INSERT INTO User (Username, Email, Password, Role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement userStmt = con.prepareStatement(userReq, PreparedStatement.RETURN_GENERATED_KEYS)) {
            userStmt.setString(1, artist.getUsername());
            userStmt.setString(2, artist.getEmail());
            userStmt.setString(3, artist.getPassword());
            userStmt.setString(4, artist.getRole());
            userStmt.executeUpdate();

            // Generate UserId eli bch yethat fil Artist
            int userId;
            try (var generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            // ADD Artist bil Generated ID
            String artistReq = "INSERT INTO Artist (Id_Artist, Name, LastName, DOB) VALUES (?, ?, ?, ?)";
            try (PreparedStatement artistStmt = con.prepareStatement(artistReq)) {
                artistStmt.setInt(1, userId);
                artistStmt.setString(2, artist.getName());
                artistStmt.setString(3, artist.getLastName());
                artistStmt.setString(4, artist.getDOB());
                artistStmt.executeUpdate();
            }
        }
    }

    @Override
    public void UPDATE(Artist artist) throws SQLException {
        String Userreq = "update User set UserName=? , Email=? , Password=?, Role=? where Id_User =?";
        try (PreparedStatement userStmt  = con.prepareStatement(Userreq)) {
            userStmt.setString(1, artist.getUsername());
            userStmt.setString(2, artist.getEmail());
            userStmt.setString(3, artist.getPassword());
            userStmt.setString(4, artist.getRole());
            userStmt.setInt(5, artist.getId_Artist());
            userStmt.executeUpdate();


            String ArtistReq = "update Artist set Name=? , LastName=? , DOB=? where Id_Artist =?";
            try(PreparedStatement artistStmt  = con.prepareStatement(ArtistReq)){
                artistStmt.setString(1, artist.getName());
                artistStmt.setString(2, artist.getLastName());
                artistStmt.setString(3, artist.getDOB());
                artistStmt.setInt(4, artist.getId_Artist());
                artistStmt.executeUpdate();
            }

        }
        catch (SQLException e) {
            System.out.println("Error Updating artist: " + e.getMessage());
        }
    }






    @Override
    public void DELETE(Artist artist) throws SQLException{
        //Delete User
        String UserReq = "DELETE FROM User WHERE Id_User = ?";
        try (PreparedStatement userstmt = con.prepareStatement(UserReq)){
            userstmt.setInt(1, artist.getId_Artist());
            userstmt.executeUpdate();


            //Delete Artist
            String artistReq = "DELETE FROM Artist WHERE Id_Artist = ?";
            try (PreparedStatement pre = con.prepareStatement(artistReq)) {
                pre.setInt(1, artist.getId_Artist());
                pre.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }

    @Override
    public List<Artist> DISPLAY() throws SQLException {
        List<Artist> artists = new ArrayList<>();
        String artistReq = "SELECT Artist.Id_Artist, Artist.Name, Artist.LastName, Artist.DOB, User.Username, User.Email, User.Password, User.Role " +
                "FROM Artist INNER JOIN User ON Artist.Id_Artist = User.Id_User";
        try (PreparedStatement artistStmt = con.prepareStatement(artistReq)) {
            ResultSet res = artistStmt.executeQuery();
            while (res.next()) {
                Artist artist = new Artist();
                //Artist
                artist.setId_Artist(res.getInt(1));
                artist.setName(res.getString(2));
                artist.setLastName(res.getString(3));
                artist.setDOB(res.getString(4));
                //User
                artist.setUsername(res.getString(5));
                artist.setEmail(res.getString(6));
                artist.setPassword(res.getString(7));
                artist.setRole(res.getString(8));
                artists.add(artist);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return artists;
    }
}
*/