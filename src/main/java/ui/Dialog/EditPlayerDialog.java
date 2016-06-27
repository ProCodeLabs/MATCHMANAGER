package ui.Dialog;

import Common.GlobalInstance;
import ui.Dialog.Helper.IUiDialog;
import ui.Helper.UiBaseDialog;

public class EditPlayerDialog implements IUiDialog
{
	public static final String RESOURCE_ID = "fxml/dialogs/editPlayerDialog.fxml";


	@Override
	public void showDialog( )
	{
		UiBaseDialog dlg = new UiBaseDialog();
		{
			dlg.setContent( RESOURCE_ID );
			dlg.initOwner( GlobalInstance.getPrimaryStage() );
			dlg.setDialogTitle( "EDIT PLAYER" );
			dlg.addDefaultCloseButtonHandler( );
		}

		dlg.show();
	}
}
