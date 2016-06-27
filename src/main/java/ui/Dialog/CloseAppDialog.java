package ui.Dialog;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import ui.Dialog.Helper.UiDialog;

public class CloseAppDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/closeDialog.fxml";

	@Override
	public void showDialog( )
	{
		{
			setContent( RESOURCE_ID );
			setDialogTitle( "Close this App?" );
		}

		addButtonEventHandler( ButtonType.YES, e -> {
			Platform.exit( );
			System.exit( 0 );
		} );

		addButtonEventHandler( ButtonType.NO, e -> remoteClose( ) );

		show( );
	}
}
