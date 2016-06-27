package ui.Dialog;

import Common.ParamFunction;
import Core.Database.StorageManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;
import ui.Dialog.ModalEx.UiAlert;


public class CreateDatabaseDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/createDatabaseDialog.fxml";

	private ParamFunction<String> resultHandler;

	@Override
	public void showDialog( )
	{
		{
			setContent( RESOURCE_ID );
			setDialogTitle( "CREATE DATABASE" );
			addDefaultCloseButtonHandler( );
		}

		addButtonEventHandler( ButtonType.OK, e -> {
			String name = ( ( TextField ) getElementById( "ID_DB_NAME" ) ).getText( );

			StorageManager.createDatabase( name )
					.thenApply( r -> {
						Platform.runLater( () -> {
							resultHandler.apply( name );
							remoteClose();
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

		show( );
	}

	public void setResultHandler( ParamFunction<String> handler )
	{
		resultHandler = handler;
	}

}
