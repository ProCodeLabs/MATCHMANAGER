package ui.Dialog;

import ui.Dialog.Helper.UiDialog;

public class CloseAppDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/closeDialog.fxml";

	@Override
	protected void onPrepareDialog( )
	{
		{
			setContent( RESOURCE_ID );
			setDialogTitle( "Close this App?" );
		}

		/*addButtonEventHandler( ButtonType.YES, e -> {
			Platform.exit( );
			System.exit( 0 );
		} );*/

		//addButtonEventHandler( ButtonType.NO, e -> remoteClose( ) );

	}

	@Override
	protected Object getThisPtr( ) { return this; }
}
