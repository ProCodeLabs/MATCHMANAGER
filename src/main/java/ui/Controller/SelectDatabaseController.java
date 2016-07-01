package ui.Controller;

import Common.GlobalInstance;
import Core.Database.StorageManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import ui.Container.UiBaseContainer;
import ui.Dialog.CreateDatabaseDialog;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SelectDatabaseController implements Initializable
{
	public static final String RESOURCE_ID = "fxml/centerContent/selectDatabase.fxml";

	private ObservableList<String> fileList = FXCollections.observableArrayList( );

	@FXML
	public Button newButton;

	@FXML
	private Button loadButton;

	@FXML
	public Button deleteButton;

	@FXML
	public ListView<String> dataList;


	public SelectDatabaseController( )
	{
	}

	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		StorageManager.scanStorageFolderAsync( fileList )
				.thenApply( r -> {
					Platform.runLater( ( ) -> FXCollections.sort( fileList ) );
					return null;
				} )
				.exceptionally( e -> {
					GlobalInstance.fireGlobalEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );
					return null;
				} );

		dataList.setItems( fileList );
	}

	@FXML
	public void buttonLoadClicked( )
	{
		if( getSelectedName( ) == null )
		{
			return;
		}

		StorageManager.loadDatabase( getSelectedName( ) )
				.thenApply( manager -> {

					if( manager.isDatabaseEmpty( ) )
					{
						Platform.runLater( ( ) -> TeamSetupController.updateContainerStage(
								( UiBaseContainer ) loadButton.getScene( ).getRoot( ), manager
						) );

						return null;
					}

					return manager;
				} )
				.thenApply( manager -> {

					if( manager != null )
					{
						Platform.runLater( ( ) -> ResultViewController.updateContainerStage(
								( UiBaseContainer ) loadButton.getScene( ).getRoot( ), manager )
						);
					}

					return null;
				} )
				.exceptionally( e -> {
					Platform.runLater( ( ) -> {
						UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
						{
							msgBox.setHeaderText( "Failed to load database" );
							msgBox.setContentText( e.getMessage( ) );
						}

						msgBox.showAndWait( );
					} );

					return null;
				} );
	}

	@FXML
	public void buttonNewClicked( )
	{
		CreateDatabaseDialog dlg = new CreateDatabaseDialog( );
		{
			dlg.setResultCallback( r -> {
				fileList.add( r );
				FXCollections.sort( fileList );
			} );
		}
		dlg.showDialog( );
	}

	@FXML
	public void buttonDeleteClicked( )
	{
		if( getSelectedName( ) == null )
		{
			return;
		}

		UiAlert alert = new UiAlert( Alert.AlertType.CONFIRMATION );
		{
			alert.setTitle( "Delete" );
			alert.setHeaderText( "Delete" );
			alert.setContentText( "Are you sure you want to delete " + getSelectedName( ) + "? (This cannot be undone!)" );
		}

		Optional<ButtonType> result = alert.showAndWait( );
		if( result.get( ) == ButtonType.OK )
		{
			final String name = getSelectedName( );

			StorageManager.deleteDatabase( name )
					.thenApply( r -> {
						Platform.runLater( ( ) -> fileList.remove( name ) );
						return null;
					} )
					.exceptionally( e -> {
						Platform.runLater( ( ) -> {
							UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
							{
								msgBox.setHeaderText( "Failed to delete database" );
								msgBox.setContentText( e.getMessage( ) );
							}
							msgBox.showAndWait( );
						} );

						return null;
					} );
		}
	}


	public String getSelectedName( )
	{
		return dataList.getSelectionModel( ).getSelectedItem( );
	}

}
