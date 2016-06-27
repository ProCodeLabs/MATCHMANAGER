package ui.Controller;

import Common.GlobalInstance;
import Core.Database.StorageManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiBaseContainer;
import ui.Helper.UiBaseDialog;
import ui.Helper.UiEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SelectDatabaseController implements Initializable
{
	public static final String RESOURCE_ID = "fxml/centerContent/selectDatabase.fxml";
	public static final String RESOURCE_ID_DLG_DBCR = "fxml/dialogs/createDatabaseDialog.fxml";

	final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );

	ObservableList<String> _fileList = FXCollections.observableArrayList( );

	@FXML
	Button newButton;

	@FXML
	Button loadButton;

	@FXML
	Button deleteButton;

	@FXML
	ListView<String> dataList;


	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		StorageManager.scanStorageFolderAsync( _fileList )
				.exceptionally( e -> {
					GlobalInstance.getPrimaryStage( ).fireEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );

					return null;
				} );
		dataList.setItems( _fileList );
	}


	@FXML
	public void buttonLoadClicked( )
	{
		if( getSelectedName( ) == null )
		{
			return;
		}

		StorageManager
				.loadDatabase( getSelectedName( ) )
				.thenApply( result -> {
					Platform.runLater( ( ) -> {
						UiBaseContainer container = ( UiBaseContainer ) loadButton.getScene( ).getRoot( );
						{
							container.setCenter( "OVERVIEW", MainViewController.RESOURCE_ID );
							container.<MainViewController> getController( ).setMatchManager( result );
						}
					} );

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
		UiBaseDialog dlg = new UiBaseDialog( );
		{
			dlg.setContent( RESOURCE_ID_DLG_DBCR );
			dlg.initOwner( GlobalInstance.getPrimaryStage( ) );
			dlg.setDialogTitle( "CREATE DATABASE" );
			dlg.addDefaultCloseButtonHandler( );
		}

		dlg.addButtonEventHandler( ButtonType.APPLY, e -> {
			String name = ( ( TextField ) dlg.getElementById( "ID_DB_NAME" ) ).getText( );

			StorageManager.createDatabase( name )
					.thenApply( r -> {
						Platform.runLater( ( ) -> _fileList.add( name ) );
						return null;
					} )
					.exceptionally( ex -> {
						Platform.runLater( ( ) -> {
							UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
							{
								msgBox.setHeaderText( "Failed to create database" );
								msgBox.setContentText( ex.getMessage( ) );
							}
							msgBox.showAndWait( );
						} );

						return null;
					} );
		} );

		dlg.showAndWait( );
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
			String name = getSelectedName( );

			StorageManager.deleteDatabase( name )
					.thenApply( r -> {
						Platform.runLater( ( ) -> _fileList.remove( name ) );

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
