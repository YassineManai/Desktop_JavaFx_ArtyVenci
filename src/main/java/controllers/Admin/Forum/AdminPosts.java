package controllers.Admin.Forum;
import entities.ForumEntity;
import entities.PostEntity;
import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.ServiceForumF;
import services.ServicePostF;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPosts {
    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Messages;

    @FXML
    private Label Num_Forum;

    @FXML
    private Label Num_Users;

    @FXML
    private ImageView logout;

    ////// LOGIC FOR TABLE

    @FXML
    private TableColumn<PostEntity, String> username_cloumn;

    @FXML
    private TableColumn<PostEntity, Integer> like_column;

    @FXML
    private TableColumn<PostEntity, String> message_column;

    @FXML
    private Button GoBackButt_id;

    @FXML
    private TableView<PostEntity> table_id;

    @FXML
    private TableColumn<PostEntity, Timestamp> time_column;

    @FXML
    private TableColumn<PostEntity, String> title_column;

    @FXML
    private TextField id_search;


    private ForumEntity forum;
    ServiceForumF SF = new ServiceForumF();

    ServiceUser serviceUser = new ServiceUser();
    ServicePostF SP = new ServicePostF();

    public void setData(ForumEntity f)
    {
        this.forum = f;
    }




    @FXML
    void Go_To_Auction(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Auction.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Event(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Event.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }



    @FXML
    void Go_To_Forum(MouseEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/Admin_Interface/Admin_Forum.fxml"));
            GoBackButt_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Go_To_Messages(MouseEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Message.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Product(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Produit.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    @FXML
    void Go_To_User(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/AdminUI.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void logout(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    void initialize() throws SQLException {
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
        Num_Forum.setText(""+SF.CountForums());
        System.out.println("Welcome Posts");
        Refresh();
    }
    public void Refresh()
    {
        try {
            ObservableList<PostEntity> observableList = FXCollections.observableList(SP.afficher());

            //FilterByEvent
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == forum.getId_forum())
                    .collect(Collectors.toList());

            ObservableList<PostEntity> newList = FXCollections.observableList(filteredList);

            //GET UserNAMES
            ObservableList<User> UserList = FXCollections.observableList(serviceUser.DISPLAY());
            table_id.setItems(newList);
            username_cloumn.setCellValueFactory(e -> {
                        int userId = e.getValue().getId_user();
                        User userFound = null;
                        for (User user : UserList) {
                            if (user.getId_User() == userId) {
                                userFound =user;
                            }
                        }
                        return  new SimpleStringProperty(userFound.getUsername());
                    }
            );
            title_column.setCellValueFactory(new PropertyValueFactory<PostEntity,String>("title"));
            message_column.setCellValueFactory(new PropertyValueFactory<PostEntity,String>("text"));
            like_column.setCellValueFactory(new PropertyValueFactory<PostEntity,Integer>("like_num"));
            time_column.setCellValueFactory(new PropertyValueFactory<PostEntity, Timestamp>("time"));

        }catch ( SQLException e) {
            e.printStackTrace();
        }
    }



    //ADDING FORUMS LOGIC
    @FXML
    private Button addForumButt_id;

    @FXML
    void AddForum(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/AddForumAdmin.fxml"));
            addForumButt_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    //DELETE FORUMS LOGIC
    @FXML
    private Button deleteForumButt_id;

    @FXML
    private AnchorPane ConfirmationPage_id;
    @FXML
    private Button Yes_butt_id;
    @FXML
    private Button No_butt_id;


    @FXML
    void ConfirmDeletion(ActionEvent event) {
        try {
            PostEntity f = table_id.getSelectionModel().getSelectedItem();
            SP.supprimer(f);
            ConfirmationPage_id.setVisible(false);
            Refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void DeletePost(ActionEvent event) {
        if(table_id.getSelectionModel().getSelectedItem() != null)
        {
            ConfirmationPage_id.setVisible(true);
        }
    }

    @FXML
    void DonotDelete(ActionEvent event) {
        ConfirmationPage_id.setVisible(false);
    }


    @FXML
    void GoBack(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/Admin_Interface/Admin_Forum.fxml"));
            GoBackButt_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void searchForPost(MouseEvent event) {
        try {
            table_id.getItems().clear();
            String searchText = id_search.getText().trim();
            ObservableList<PostEntity> observableList = FXCollections.observableList(SP.afficher());

            // FilterByEvent
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == forum.getId_forum())
                    .collect(Collectors.toList());

            // FilterByTitle
            List<PostEntity> secondFilteredList = filteredList.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            ObservableList<PostEntity> observableSecondFilteredList = FXCollections.observableList(secondFilteredList);

            // GET UserNAMES
            ObservableList<User> userList = FXCollections.observableList(serviceUser.DISPLAY());

            table_id.setItems(observableSecondFilteredList);
            username_cloumn.setCellValueFactory(e -> {
                int userId = e.getValue().getId_user();
                User userFound = null;
                for (User user : userList) {
                    if (user.getId_User() == userId) {
                        userFound = user;
                        break; // once found, no need to continue loop
                    }
                }
                return new SimpleStringProperty(userFound != null ? userFound.getUsername() : "");
            });

            title_column.setCellValueFactory(new PropertyValueFactory<>("title"));
            message_column.setCellValueFactory(new PropertyValueFactory<>("text"));
            like_column.setCellValueFactory(new PropertyValueFactory<>("like_num"));
            time_column.setCellValueFactory(new PropertyValueFactory<>("time"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
