package ui.Dialog.Helper;

import Common.GlobalInstance;
import javafx.fxml.FXMLLoader;
import ui.Helper.UiBaseDialog;

public abstract class UiDialog extends UiBaseDialog
{
	public UiDialog( )
	{
		initOwner( GlobalInstance.getPrimaryStage( ) );
	}

	@Override
	public void onContentLoad( FXMLLoader loader )
	{
		loader.setController( getThisPtr( ) );
	}

	public void showDialog()
	{
		onPrepareDialog();
		{
			show( );
		}
	}

	protected abstract void onPrepareDialog( );

	protected abstract Object getThisPtr( );

}
