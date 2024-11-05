package controllers.Admin.Forum;
import entities.ForumEntity;
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
import services.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminForum {
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
    private TableView<ForumEntity> table_id;
    @FXML
    private TableColumn<ForumEntity, Date> date_id;

    @FXML
    private TableColumn<ForumEntity, String> desc_id;

//    @FXML
//    private TableColumn<ForumEntity, Integer> f_id;

    @FXML
    private TableColumn<ForumEntity, Integer> rep_id;

    @FXML
    private TableColumn<ForumEntity, String> title_id;



    ServiceProduct p = new ServiceProduct();
    ServiceUser serviceUser = new ServiceUser();
    ServiceForumF serviceForum = new ServiceForumF();
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceEvent serviceEvent = new ServiceEvent();
    public void initialize() throws SQLException {
        Num_Forum.setText(""+serviceForum.CountForums());
        Num_Arts.setText(String.valueOf(p.CountProduct()));
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
        Num_Auction.setText(String.valueOf(serviceAuction.CountAuction()));
        Num_Messages.setText(String.valueOf(serviceDisscussion.CountDisc()));
        Num_Events.setText(String.valueOf(serviceEvent.CountMesg()));
        System.out.println("Welcome Forum");

        Refresh();

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
    void DeleteForum(ActionEvent event) {
        if(table_id.getSelectionModel().getSelectedItem() != null)
        {
            ConfirmationPage_id.setVisible(true);
        }
    }
    @FXML
    void ConfirmDeletion(ActionEvent event)
    {
        try {
            ForumEntity f = table_id.getSelectionModel().getSelectedItem();
            serviceForum.supprimer(f);
            ConfirmationPage_id.setVisible(false);
            Refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void DonotDelete(ActionEvent event)
    {
        ConfirmationPage_id.setVisible(false);
    }
    //EDIT FORUMS LOGIC
    @FXML
    private Button editForumButt_id;
    @FXML
    void EditForum(ActionEvent event) {
        if(table_id.getSelectionModel().getSelectedItem() != null)
        {
            try {
                ForumEntity f = table_id.getSelectionModel().getSelectedItem();
                Parent root= loadRootLayout(f);
                deleteForumButt_id.getScene().setRoot(root);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Parent loadRootLayout(ForumEntity f) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Admin/EditForumAdmin.fxml"));
        EditForumAdminController controller = new EditForumAdminController();
        loader.setController(controller);
        Parent root = loader.load();
        controller.setData(f); // Add data to the controller
        return root;
    }

    //SEARCHING LOGIC

    @FXML
    private TextField id_search;

    @FXML
    void searchForForm(MouseEvent event) {
        try {
            table_id.getItems().clear();
            String searchText = id_search.getText();
            ObservableList<ForumEntity> observableList = FXCollections.observableList(serviceForum.afficher());

            List<ForumEntity> filteredList = observableList.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            ObservableList<ForumEntity> newList = FXCollections.observableList(filteredList);

            table_id.setItems(newList);

//            f_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("id_forum"));
            title_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("title"));
            desc_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("description"));
            rep_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("replies_num"));
            date_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Date>("date"));


        }catch ( SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //VIEW POSTS LOGIC
    @FXML
    private Button postViewButt_id;

    @FXML
    void GoToPosts(ActionEvent event) {
        if(table_id.getSelectionModel().getSelectedItem() != null)
        {
            try{
                Parent root = loadTablePosts();
                postViewButt_id.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Parent loadTablePosts() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Posts.fxml"));
        AdminPosts controller = new AdminPosts();
        loader.setController(controller);
        controller.setData(table_id.getSelectionModel().getSelectedItem());
        Parent root = loader.load();
        return root;
    }

    //Refresh Logic
    private void Refresh()
    {
        try {
            ObservableList<ForumEntity> observableList = FXCollections.observableList(serviceForum.afficher());
            table_id.setItems(observableList);
//            f_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("id_forum"));
            title_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("title"));
            desc_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("description"));
            rep_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("replies_num"));
            date_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Date>("date"));

        }catch ( SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //FILTERING LOGIC
    @FXML
    private ComboBox<ForumEntity> id_Choice;

}
