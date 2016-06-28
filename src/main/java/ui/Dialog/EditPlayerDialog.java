package ui.Dialog;

import javafx.scene.control.ButtonType;
import ui.Dialog.Helper.UiDialog;

public class EditPlayerDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/editPlayerDialog.fxml";

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		setDialogTitle( "EDIT PLAYER" );
		addButtonEventHandler( ButtonType.CANCEL, e -> close( ) );
	}
}
