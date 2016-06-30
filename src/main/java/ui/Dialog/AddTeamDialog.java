package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;

public class AddTeamDialog extends UiDialog<String>
{
	public static final String RESOURCE_ID = "fxml/dialogs/addTeamDialog.fxml";

	@FXML
	TextField teamName;

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		addButtonEventHandler( ButtonType.OK, e -> {
			if( teamName.getText( ).length( ) <= 0 )
			{
				e.consume( );
				return;
			}

			resultCallback.apply( teamName.getText( ) );
		} );
	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}
}
