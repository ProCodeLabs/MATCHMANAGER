package ui.Dialog;

import Common.ParamFunction;
import ui.Dialog.Helper.UiDialog;


public class CreateDatabaseDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/createDatabaseDialog.fxml";

	private ParamFunction<String> resultHandler;

	@Override
	protected void onPrepareDialog( )
	{
		{
			setContent( RESOURCE_ID );
			setDialogTitle( "CREATE DATABASE" );
		}

		/*addButtonEventHandler( ButtonType.OK, e -> {
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
		} );*/

		//show( );
	}

	@Override
	public Object getThisPtr( ) { return this; }

	public void setResultHandler( ParamFunction<String> handler )
	{
		resultHandler = handler;
	}

}
