package ui.Container;

import ui.Dialog.CloseAppDialog;
import ui.Helper.UiEvent;

public class UiCoreWindow extends UiBaseContainer
{
	public UiCoreWindow(  )
	{
		super();

		addEventHandler( UiEvent.CLOSE_WINDOW, e ->{
			CloseAppDialog dlg = new CloseAppDialog( );
			{
				dlg.showDialog( );
			}
		} );
	}
}
