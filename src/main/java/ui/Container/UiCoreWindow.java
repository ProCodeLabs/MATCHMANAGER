package ui.Container;

import ui.Dialog.CloseAppDialog;

public class UiCoreWindow extends UiBaseContainer
{
	public UiCoreWindow(  )
	{
		super();

		desc.setOnCloseButton( ( ) -> {
			CloseAppDialog dlg = new CloseAppDialog( );
			{
				dlg.showDialog( );
			}
		} );
	}
}
