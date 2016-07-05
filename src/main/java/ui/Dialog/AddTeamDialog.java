package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;

public class AddTeamDialog extends UiDialog<String>
{
	public static final String RESOURCE_ID = "fxml/dialogs/addTeamDialog.fxml";

	@FXML
	public TextField teamNameField;

	private String teamName;



	public AddTeamDialog( )
	{
	}

	public AddTeamDialog( String teamName )
	{
		this.teamName = teamName;
	}

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		if( teamName != null )
		{
			teamNameField.setText( teamName );
			setDialogTitle( "EDIT TEAM" );
		}

		addButtonEventHandler( ButtonType.OK, e -> {
			if( teamNameField.getText( ).length( ) <= 0 )
			{
				e.consume( );
				return;
			}

			resultCallback.apply( teamNameField.getText( ) );
		} );
	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}
}
