package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;
import ui.Helper.UiHelper;

public class AddTeamDialog extends UiDialog<String>
{
	public static final String RESOURCE_ID = "fxml/dialogs/addTeamDialog.fxml";
	private static final String CSS_ERROR_CLS = "textfield-error";

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

		UiHelper.attachInvalidTextHandler( teamNameField );

		addButtonEventHandler( ButtonType.OK, e -> {
			if( teamNameField.getText( ).isEmpty( ) )
			{
				UiHelper.addInvalidTextClass( teamNameField );
				e.consume( );
			}
			else
			{
				resultCallback.apply( teamNameField.getText( ) );
			}
		} );
	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}
}
