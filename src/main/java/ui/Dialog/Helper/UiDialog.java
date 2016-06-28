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

	public void showDialog()
	{
		onPrepareDialog();
		{
			show( );
		}
	}

	@Override
	public void onLoad( FXMLLoader loader )
	{
		loader.setController( getThisPtr( ) );
	}

	protected abstract void onPrepareDialog( );
	protected abstract Object getThisPtr();


}
