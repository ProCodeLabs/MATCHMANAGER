package ui.Dialog;

import ui.Dialog.Helper.UiDialog;

public class EditMatchDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/editMatchDialog.fxml";


	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );
	}


	@Override
	protected Object getThisPtr( ) { return this; }

}
