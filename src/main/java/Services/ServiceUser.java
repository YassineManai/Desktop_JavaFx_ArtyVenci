package services;

import entities.User;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ServiceUser implements IService<User> {

    private Connection con;

    public ServiceUser(){
        con = MyDB.getInstance().getConnection();
    }


    @Override
    public void ADD(User user) throws SQLException {

        String UserReq = "INSERT INTO User (Username, Email, Password,Role,FirstName,Lastname,Adress,Phone,Gender,DOB,ImageURL) VALUES (?, ?, ?, ? , ?, ? , ? , ? , ? ,?,?)";
        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setString(1, user.getUsername());
            UserStmt.setString(2, user.getEmail());
            UserStmt.setString(3, user.getPassword());
            UserStmt.setString(4, user.getRole());
            UserStmt.setString(5, user.getFirstName());
            UserStmt.setString(6, user.getLastName());
            UserStmt.setString(7, user.getAdress());
            UserStmt.setInt(8, user.getPhone());
            UserStmt.setString(9, user.getGender());
            UserStmt.setString(10, user.getDOB());
            UserStmt.setString(11, user.getImageURL());

            UserStmt.executeUpdate();
        }

    }

    @Override
    public void UPDATE(User user) throws SQLException {
        String UserReq = "update User set Username=? , Email=? , Password=? , Role=? ,FirstName=? ,Lastname=? ,Adress=?  ,Phone=?  ,Gender=?,DOB=?,ImageURL=? where Id_User  =?";
        try(PreparedStatement artistStmt  = con.prepareStatement(UserReq)){
            artistStmt.setString(1, user.getUsername());
            artistStmt.setString(2, user.getEmail());
            artistStmt.setString(3, user.getPassword());
            artistStmt.setString(4, user.getRole());
            artistStmt.setString(5, user.getFirstName());
            artistStmt.setString(6, user.getLastName());
            artistStmt.setString(7, user.getAdress());
            artistStmt.setInt(8, user.getPhone());
            artistStmt.setString(9, user.getGender());
            artistStmt.setString(10, user.getDOB());
            artistStmt.setInt(12, user.getId_User());
            artistStmt.setString(11, user.getImageURL());
            artistStmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error Updating artist: " + e.getMessage());
        }

    }



    @Override
    public void DELETE(User user) throws SQLException {
        String UserReq = "DELETE FROM User WHERE Id_User  = ?";
        try (PreparedStatement pre = con.prepareStatement(UserReq)) {
            pre.setInt(1, user.getId_User());
            pre.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }


    @Override
    public List<User> DISPLAY() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress ,User.Phone ,User.Gender , User.DOB , User.ImageURL " +
                "FROM User";
        try (PreparedStatement UserStmt = con.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                user.setImageURL(res.getString(12));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;

    }

    public List<User> DISPLAYARTIST() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress,User.Phone ,User.Gender, User.DOB " +
                "FROM User WHERE User.Role = 'Artist'";
        try (PreparedStatement UserStmt = con.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                //Artist
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                //User
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;

    }

    public List<User> DISPLAYMember() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress,User.Phone ,User.Gender, User.DOB " +
                "FROM User WHERE User.Role = 'Member'";
        try (PreparedStatement UserStmt = con.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                //Artist
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                //User
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;

    }
    public  boolean authenticateUser(String username, String password) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Username = ? AND Password = ?";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setString(1, username);
            UserStmt.setString(2, password);

            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, authentication succeeds
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }

    }

    public boolean checkAdmin(String username, String password) {
        // SQL query to check if the provided username and password match a record in the database and the role is "Admin"
        String UserReq = "SELECT * FROM User WHERE Username = ? AND Password = ? AND Role = ?";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setString(1, username);
            UserStmt.setString(2, password);
            UserStmt.setString(3, "Admin"); // Set the role to "Admin"

            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, authentication succeeds
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }

    public void DELETEUser(int idUser) throws SQLException {
        String UserReq = "DELETE FROM User WHERE Id_User  = ?";
        try (PreparedStatement pre = con.prepareStatement(UserReq)) {
            pre.setInt(1, idUser);
            pre.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }
    public int CountUsers() throws SQLException {
        String UserReq = "SELECT COUNT(*) FROM User";
        try (PreparedStatement pre = con.prepareStatement(UserReq)) {
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

    public User GetUser(String username, String password) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Username = ? AND Password = ?";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setString(1, username);
            UserStmt.setString(2, password);

            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, retrieve the user details
                if (resultSet.next()) {
                    // Create a new User object with the retrieved details
                    int userId = resultSet.getInt("Id_User");

                    String email = resultSet.getString("Email");

                    String role = resultSet.getString("Role");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String adress = resultSet.getString("Adress");
                    int Phone = resultSet.getInt("Phone");
                    String gender = resultSet.getString("Gender");
                    LocalDate dob = LocalDate.parse(resultSet.getString("DOB"));
                    String imageUrl = resultSet.getString("ImageURL");

                    // You can retrieve other fields similarly

                    // Return the created User object
                    return new User(userId,username,email,password,role,firstName,lastName,adress,gender,Phone,dob,imageUrl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }

        // If no user found or error occurred, return null
        return null;
    }

    public User GetUserFromPhone(int phone) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Phone = ? ";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setInt(1, phone);


            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, retrieve the user details
                if (resultSet.next()) {
                    // Create a new User object with the retrieved details
                    int userId = resultSet.getInt("Id_User");
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");


                    String email = resultSet.getString("Email");

                    String role = resultSet.getString("Role");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String adress = resultSet.getString("Adress");
                    int Phone = resultSet.getInt("Phone");
                    String gender = resultSet.getString("Gender");
                    LocalDate dob = LocalDate.parse(resultSet.getString("DOB"));
                    String imageUrl = resultSet.getString("ImageURL");

                    // You can retrieve other fields similarly

                    // Return the created User object
                    return new User(userId,username,email,password,role,firstName,lastName,adress,gender,Phone,dob,imageUrl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }

        // If no user found or error occurred, return null
        return null;
    }



    public User GetUserById(int id) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Id_User = ? ";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setInt(1, id);


            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, retrieve the user details
                if (resultSet.next()) {
                    // Create a new User object with the retrieved details

                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");


                    String email = resultSet.getString("Email");

                    String role = resultSet.getString("Role");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String adress = resultSet.getString("Adress");
                    int Phone = resultSet.getInt("Phone");
                    String gender = resultSet.getString("Gender");
                    LocalDate dob = LocalDate.parse(resultSet.getString("DOB"));
                    String imageUrl = resultSet.getString("ImageURL");

                    // You can retrieve other fields similarly

                    // Return the created User object
                    return new User(id,username,email,password,role,firstName,lastName,adress,gender,Phone,dob,imageUrl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }

        // If no user found or error occurred, return null
        return null;
    }

    public User GetUserFromUsername(String username) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Username = ? ";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setString(1, username);


            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, retrieve the user details
                if (resultSet.next()) {
                    // Create a new User object with the retrieved details
                    int userId = resultSet.getInt("Id_User");

                    String password = resultSet.getString("Password");


                    String email = resultSet.getString("Email");

                    String role = resultSet.getString("Role");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String adress = resultSet.getString("Adress");
                    int Phone = resultSet.getInt("Phone");
                    String gender = resultSet.getString("Gender");
                    LocalDate dob = LocalDate.parse(resultSet.getString("DOB"));
                    String imageUrl = resultSet.getString("ImageURL");

                    // You can retrieve other fields similarly

                    // Return the created User object
                    return new User(userId,username,email,password,role,firstName,lastName,adress,gender,Phone,dob,imageUrl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }

        // If no user found or error occurred, return null
        return null;
    }
    public User GetUserFromEmail(String email) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Email = ? ";

        try (PreparedStatement UserStmt = con.prepareStatement(UserReq)) {
            UserStmt.setString(1, email);


            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, retrieve the user details
                if (resultSet.next()) {
                    // Create a new User object with the retrieved details
                    int userId = resultSet.getInt("Id_User");

                    String password = resultSet.getString("Password");


                    String username = resultSet.getString("Username");

                    String role = resultSet.getString("Role");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String adress = resultSet.getString("Adress");
                    int Phone = resultSet.getInt("Phone");
                    String gender = resultSet.getString("Gender");
                    LocalDate dob = LocalDate.parse(resultSet.getString("DOB"));
                    String imageUrl = resultSet.getString("ImageURL");

                    // You can retrieve other fields similarly

                    // Return the created User object
                    return new User(userId,username,email,password,role,firstName,lastName,adress,gender,Phone,dob,imageUrl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }

        // If no user found or error occurred, return null
        return null;
    }




}
