package ui.Dialog;

import ui.Dialog.Helper.UiDialog;

public class EditPlayerDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/editPlayerDialog.fxml";

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		setDialogTitle( "EDIT PLAYER" );
		addDefaultCloseButtonHandler( );

		show( );
	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}
}
