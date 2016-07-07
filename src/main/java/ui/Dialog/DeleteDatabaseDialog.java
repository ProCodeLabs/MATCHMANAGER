package ui.Dialog;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import ui.Dialog.Helper.UiDialog;


public class DeleteDatabaseDialog extends UiDialog<Void>
{
	public static final String RESOURCE_ID = "fxml/dialogs/deleteDatabaseDialog.fxml";

	private String databaseName;

	@FXML
	Label databaseNameLabel;


	public DeleteDatabaseDialog(String databaseName )
	{
		this.databaseName = databaseName;
	}

	@Override
	protected void onPrepareDialog( )
	{
		setContent( RESOURCE_ID );
		databaseNameLabel.setText(String.format( "Are you sure that you want to Delete\n%s ?",databaseName ));

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
