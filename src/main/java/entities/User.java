package entities;

import java.time.LocalDate;

public class User {
int Id_User;
String Username;
    String Email;
    String Password;
    String Role;
    String FirstName;
    String LastName;
    String Adress;
    String Gender;
    int Phone;
    String DOB;

    String ImageURL;
  ;

    public User(int id_User, String username, String email, String password, String role, String firstName, String lastName, String adress,String gender, int phone, LocalDate DOB, String imageURL) {
        Id_User = id_User;
        Username = username;
        Email = email;
        Password = password;
        Role = role;
        FirstName = firstName;
        LastName = lastName;
        Adress = adress;
        Gender = gender;
        Phone = phone;
        this.DOB = String.valueOf(DOB);
        ImageURL = imageURL;
    }

    public User(String username, String email, String password, String role, String firstName, String lastName, String adress, String gender, int phone, LocalDate DOB ,String imageURL) {
        Username = username;
        Email = email;
        Password = password;
        Role = role;
        FirstName = firstName;
        LastName = lastName;
        Adress = adress;
        Gender = gender;
        Phone = phone;
        this.DOB = String.valueOf(DOB);
        ImageURL = imageURL;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public User(){}

    public User(int id_User) {
        Id_User = id_User;
    }

    @Override
    public String toString() {
        return
                "User{" +
                        "UserId=" + Id_User +
                        ", Username=" + Username +
                        ", Email ='" + Email + '\'' +
                        ", Role='" + Role + '\'' +
                        ", FirstName='" + FirstName + '\'' +
                        ", LastName='" + LastName + '\'' +
                        ", Adress='" + Adress + '\'' +
                        ", Phone='" + Phone + '\'' +
                        ", Gender='" + Gender + '\'' +
                        ", Password='" + Password + '\'' +
                        ", DOB='" + DOB + '\'' +
                        '}';
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
