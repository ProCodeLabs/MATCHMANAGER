package ui.Dialog;

import Common.GlobalInstance;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import ui.Helper.UiBaseDialog;

public class CloseAppDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/closeDialog.fxml";

	public void showDialog( )
	{
		UiBaseDialog dlg = new UiBaseDialog( );
		{
			dlg.initOwner( GlobalInstance.getPrimaryStage( ) );
			dlg.setContent( RESOURCE_ID );
			dlg.setDialogTitle( "Close this App?" );
		}

		dlg.addButtonEventHandler( ButtonType.YES, e -> {
			Platform.exit( );
			System.exit( 0 );
		} );

		dlg.addButtonEventHandler( ButtonType.NO, e -> dlg.remoteClose( ) );

		dlg.show( );
	}
}
