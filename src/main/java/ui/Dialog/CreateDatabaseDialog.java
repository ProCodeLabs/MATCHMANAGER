package ui.Dialog;

import Core.Database.StorageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiHelper;


public class CreateDatabaseDialog extends UiDialog<String>
{
	public static final String RESOURCE_ID = "fxml/dialogs/createDatabaseDialog.fxml";


	@FXML
	public TextField databaseName;


	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		UiHelper.attachInvalidTextHandler( databaseName );


		addButtonEventHandler( ButtonType.OK, e -> {
			String name = databaseName.getText( );

			if( databaseName.getText( ).isEmpty() )
			{
				UiHelper.addInvalidTextClass( databaseName );
				e.consume( );
				return;
			}

			StorageManager.createDatabase( name )
					.thenApply( r -> {
						Platform.runLater( ( ) -> {
							resultCallback.apply( name );
							close( );
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
	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}

}
