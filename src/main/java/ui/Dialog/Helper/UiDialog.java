package ui.Dialog.Helper;

import Common.GlobalInstance;
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

	protected abstract void onPrepareDialog( );
}