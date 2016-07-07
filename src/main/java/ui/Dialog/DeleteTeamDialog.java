package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import ui.Dialog.Helper.UiDialog;


public class DeleteTeamDialog extends UiDialog<Void>
{
	public static final String RESOURCE_ID = "fxml/dialogs/deleteTeamDialog.fxml";

	private String teamrname;

	@FXML
	Label teamNameLabel;


	public DeleteTeamDialog( String teamname )
	{
		this.teamrname = teamname;
	}

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );
		teamNameLabel.setText(String.format( "Are you sure that you want to Delete\n%s ?",teamrname ));

		addButtonEventHandler( ButtonType.YES, e -> {
			resultCallback.apply(null);
		} );

	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}

}
