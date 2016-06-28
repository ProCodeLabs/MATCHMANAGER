package ui.Dialog;

import ui.Dialog.Helper.UiDialog;

public class EditTeamDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/editTeamDialog.fxml";

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );
		setDialogTitle( "Edit Team" );
	}
}
