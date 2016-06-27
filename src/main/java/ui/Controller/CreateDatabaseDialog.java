package ui.Controller;

import Common.GlobalInstance;
import Common.ParamFunction;
import Core.Database.StorageManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.IUiDialog;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiBaseDialog;


public class CreateDatabaseDialog implements IUiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/createDatabaseDialog.fxml";

	private ParamFunction<String> resultHandler;

	public void showDialog( )
	{
		UiBaseDialog dlg = new UiBaseDialog( );
		{
			dlg.setContent( RESOURCE_ID );
			dlg.initOwner( GlobalInstance.getPrimaryStage( ) );
			dlg.setDialogTitle( "CREATE DATABASE" );
			dlg.addDefaultCloseButtonHandler( );
		}

		dlg.addButtonEventHandler( ButtonType.OK, e -> {
			String name = ( ( TextField ) dlg.getElementById( "ID_DB_NAME" ) ).getText( );

			StorageManager.createDatabase( name )
					.thenApply( r -> {
						Platform.runLater( () -> {
							resultHandler.apply( name );
							dlg.remoteClose();
						} );

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

		dlg.show( );
	}

	public void setResultHandler( ParamFunction<String> handler )
	{
		resultHandler = handler;
	}

}
