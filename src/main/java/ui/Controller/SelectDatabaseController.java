package ui.Controller;

import Core.Database.StorageManager;
import Database.Connection.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import ui.Helper.UiBaseContainer;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static Common.Files.PATH;

public class SelectDatabaseController implements Initializable {
    public static final String RESOURCE_ID = "fxml/centerContent/selectDatabase.fxml";

    final Logger logger = Logger.getLogger( this.getClass().getName());

    ObservableList<String> _fileList = FXCollections.observableArrayList();

    @FXML
    Button newButton;

    @FXML
    Button loadButton;

    @FXML
    Button deleteButton;

    @FXML
    ListView<String> dataList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StorageManager.scanStorageFolderAsync( _fileList );
        dataList.setItems(_fileList);
    }


    @FXML
    public void buttonLoadClicked() {
        //TODO: Load selected data
        String datapath = getSelectedPath();

        UiBaseContainer container = (UiBaseContainer) loadButton.getScene().getRoot();
        {
            try
            {
                container.setCenter("OVERVIEW", MainViewController.RESOURCE_ID);
            } catch (Exception ex) {

            }

        }
    }

    @FXML
    public void buttonNewClicked() {

        CreateDatabaseDialogController dlg = new CreateDatabaseDialogController( _fileList );
        {
            dlg.addResultHandler( ( /*res*/ ) -> {

            } );

            dlg.showDialog();
        }


       /* TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create Database");
        dialog.setHeaderText("Create new Database");
        dialog.setContentText("Please enter a Database name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent( name -> {
            StorageManager.createStorageFile( name );
            _fileList.add(name);
        });*/
    }

    @FXML
    public void buttonDeleteClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dalete");
        alert.setHeaderText("Delete");
        alert.setContentText("Are you sure you want to Delete this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DatabaseHandler.deleteDatabase(getSelectedPath());
            _fileList.remove(getSelected());
        }


    }

    public String getSelected() {
        return dataList.getSelectionModel().getSelectedItem();
    }

    public String getSelectedPath() {
        return PATH + File.separator + getSelected();
    }
}
