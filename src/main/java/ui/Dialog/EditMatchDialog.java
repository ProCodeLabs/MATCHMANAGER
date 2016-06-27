package ui.Dialog;

import ui.Dialog.Helper.UiDialog;

public class EditMatchDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/editMatchDialog.fxml";


	@Override
	public void showDialog( )
	{
		setContent( RESOURCE_ID );

		setDialogTitle( "Edit Match" );

		show();
	}
}
