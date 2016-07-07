package ui.Dialog;

import Core.Data.Player;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Dialog.Helper.UiDialog;
import ui.Helper.UiHelper;

public class AddPlayerDialog extends UiDialog<Player>
{
	public static final String RESOURCE_ID = "fxml/dialogs/addPlayerDialog.fxml";


	@FXML
	public TextField forenameText;

	@FXML
	public TextField surnameText;

	private Player player;

	private String teamName;

	public AddPlayerDialog( String teamName )
	{
		this.teamName = teamName;
	}

	public AddPlayerDialog( Player player )
	{
		this.player = player;
	}


	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );

		if( player != null )
		{
			forenameText.setText( player.getForename( ) );
			surnameText.setText( player.getSurname( ) );

			setDialogTitle( "EDIT PLAYER" );
		}
		else
		{
			setDialogTitle( "ADD PLAYER( " + teamName + " )" );
		}

		UiHelper.attachInvalidTextHandler( forenameText );
		UiHelper.attachInvalidTextHandler( surnameText );


		addButtonEventHandler( ButtonType.OK, e -> {
			if( forenameText.getText( ).isEmpty( ) )
			{
				UiHelper.addInvalidTextClass( forenameText );
				e.consume( );
			}
			else if( surnameText.getText( ).isEmpty( ) )
			{
				UiHelper.addInvalidTextClass( surnameText );
				e.consume( );
			}
			else
			{
				resultCallback.apply(
						new Player( forenameText.getText( ), surnameText.getText( ) )
				);
			}
		} );

	}

	@Override
	protected Object getThisPtr( )
	{
		return this;
	}

}
