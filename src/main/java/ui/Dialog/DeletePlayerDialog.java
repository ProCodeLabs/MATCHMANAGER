package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import ui.Dialog.Helper.UiDialog;


public class DeletePlayerDialog extends UiDialog<Void>
{
	public static final String RESOURCE_ID = "fxml/dialogs/deletePlayerDialog.fxml";

	private String playerName;

	@FXML
	Label playerNameLabel;


	public DeletePlayerDialog( String playerName )
	{
		this.playerName = playerName;
	}

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );
		playerNameLabel.setText( String.format( "Are you sure that you want to Delete\n%s ?", playerName ) );

		addButtonEventHandler( ButtonType.YES, e -> resultCallback.apply( null ) );

	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}

}
