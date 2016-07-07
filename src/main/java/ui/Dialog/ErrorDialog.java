package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import ui.Dialog.Helper.UiDialog;


public class ErrorDialog extends UiDialog<Void>
{
	public static final String RESOURCE_ID = "fxml/dialogs/errorDialog.fxml";

	private String errorMsg;

	@FXML
	Label errorLabel;


	public ErrorDialog( String error )
	{
		this.errorMsg = error;
	}

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );
		errorLabel.setText( errorMsg );

		addButtonEventHandler( ButtonType.CLOSE, e -> resultCallback.apply( null ) );

	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}

}
