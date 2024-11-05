package controllers.Admin.Forum;

import entities.ForumEntity;
import entities.PostEntity;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import services.ServiceForumF;
import services.ServicePostF;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;


public class GestionPostsTableAdminController {
    @FXML
    private AnchorPane ConfirmationPage_id;

    @FXML
    private Button No_butt_id;

    @FXML
    private Button Yes_butt_id;

    @FXML
    private Button deleteForumButt_id;

    @FXML
    private TextField id_search;

    @FXML
    private TableColumn<PostEntity, String> username_cloumn;

    @FXML
    private TableColumn<PostEntity, Integer> like_column;

    @FXML
    private TableColumn<PostEntity, String> message_column;

    @FXML
    private Button GoBackButt_id;

    @FXML
    private Button searchbutt_id;

    @FXML
    private TableView<PostEntity> table_id;

    @FXML
    private TableColumn<PostEntity, Timestamp> time_column;

    @FXML
    private TableColumn<PostEntity, String> title_column;


    private ForumEntity forum;
    ServiceForumF SF = new ServiceForumF();

    ServiceUser serviceUser = new ServiceUser();
    ServicePostF SP = new ServicePostF();



    public void setData(ForumEntity f)
    {
        this.forum = f;
    }
    @FXML
    void initialize()
    {
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

    @FXML
    void ConfirmDeletion(ActionEvent event) {
        try {
            PostEntity f = table_id.getSelectionModel().getSelectedItem();
            SP.supprimer(f);
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
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/GestionForumAdmin.fxml"));
            GoBackButt_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void searchForPost(ActionEvent event) {
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
