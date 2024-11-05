package controllers.Admin.Forum;

import entities.ForumEntity;
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
import services.ServiceForumF;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GestionForumAdminController {

    @FXML
    private TableView<ForumEntity> table_id;
    @FXML
    private TableColumn<ForumEntity, Date> date_id;

    @FXML
    private TableColumn<ForumEntity, String> desc_id;

    @FXML
    private TableColumn<ForumEntity, Integer> f_id;

    @FXML
    private TableColumn<ForumEntity, Integer> rep_id;

    @FXML
    private TableColumn<ForumEntity, String> title_id;

    ServiceForumF SF = new ServiceForumF();
    @FXML
    void initialize()
    {

        try {
            ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
            table_id.setItems(observableList);
            f_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("id_forum"));
            title_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("title"));
            desc_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("description"));
            rep_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("replies_num"));
            date_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Date>("date"));

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
            SF.supprimer(f);
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/GestionForumAdmin.fxml"));
            deleteForumButt_id.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
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
    void searchForForm(ActionEvent event) {
        try {
            table_id.getItems().clear();
            String searchText = id_search.getText();
            ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());

            List<ForumEntity> filteredList = observableList.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            ObservableList<ForumEntity> newList = FXCollections.observableList(filteredList);

            table_id.setItems(newList);

            f_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("id_forum"));
            title_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("title"));
            desc_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,String>("description"));
            rep_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Integer>("replies_num"));
            date_id.setCellValueFactory(new PropertyValueFactory<ForumEntity,Date>("date"));


        }catch ( SQLException e) {
            e.printStackTrace();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Admin/GestionPostsTableAdmin.fxml"));
        GestionPostsTableAdminController controller = new GestionPostsTableAdminController();
        loader.setController(controller);
        controller.setData(table_id.getSelectionModel().getSelectedItem());
        Parent root = loader.load();
        return root;
    }
}
