package ui.Container;

import ui.Dialog.CloseAppDialog;

public class UiCoreWindowContainer extends UiBaseContainer
{
	public UiCoreWindowContainer(  )
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
