package ui.Dialog;

import Common.ParamFunction;
import Core.Database.StorageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;
import ui.Dialog.ModalEx.UiAlert;


public class CreateDatabaseDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/createDatabaseDialog.fxml";
	private static final String CSS_ERROR_CLS = "textfield-error";

	private ParamFunction<String> resultHandler;

	@FXML
	public TextField databaseName;


	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		databaseName.textProperty().addListener( e -> {
			if( databaseName.getStyleClass( ).contains( CSS_ERROR_CLS ) )
			{
				databaseName.getStyleClass( ).remove( CSS_ERROR_CLS );
			}
		} );

		addButtonEventHandler( ButtonType.OK, e -> {
			String name = databaseName.getText( );

			if( databaseName.getText( ).length( ) <= 0 )
			{
				databaseName.getStyleClass( ).add( CSS_ERROR_CLS );
				e.consume( );
				return;
			}

			StorageManager.createDatabase( name )
					.thenApply( r -> {
						Platform.runLater( ( ) -> {
							resultHandler.apply( name );
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

	public void setResultHandler( ParamFunction<String> handler )
	{
		resultHandler = handler;
	}


	@Override
	protected Object getThisPtr( ) { return this; }

}
