package ui.Dialog;

import javafx.scene.control.ButtonType;
import ui.Dialog.Helper.UiDialog;

public class AddPlayerDialog extends UiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/addPlayerDialog.fxml";

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		getDialogPane().getButtonTypes().add( new ButtonType( "ADD" ) );
	}

	@Override
	protected Object getThisPtr( ) { return this; }

}
