package ui.Controller;

import Core.Database.StorageManager;
import Core.GlobalInstance;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.IUiDialog;
import ui.Helper.UiBaseDialog;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


public class CreateDatabaseDialogController implements IUiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/createNewDatabaseDialog.fxml";

	private ObservableList<String> _target;


	public CreateDatabaseDialogController( ObservableList<String> target )
	{
		_target = target;
	}

	public void showDialog( )
	{
		UiBaseDialog dlg = new UiBaseDialog( );
		{
			dlg.setContent( RESOURCE_ID );
			dlg.initOwner( GlobalInstance.getPrimaryStage( ) );
			dlg.setDialogTitle( "CREATE DATABASE" );
			dlg.addDefaultCloseButtonHandler( );

			dlg.addButtonEventHandler( ButtonType.APPLY, e -> {
				Button btn = ( Button ) e.getSource( );

				String dbName = (( TextField ) dlg.getElementById( "ID_DB_NAME" )).getText();

				CompletableFuture.supplyAsync( ( ) -> {
					try
					{
						StorageManager.createDatabase( dbName );
					}
					catch( IOException ex )
					{
						System.out.println( ex.getMessage());
					}

					return "";
				} ).thenApply( ( String file ) -> {
					Platform.runLater( ( ) -> _target.add( file ) );

					return null;
				} );
			} );
		}

		dlg.show( );
	}

	public void addResultHandler( Runnable c )
	{

	}
}
