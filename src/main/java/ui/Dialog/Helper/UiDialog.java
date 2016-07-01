package ui.Dialog.Helper;

import Common.GlobalInstance;
import Common.ParamFunction;
import javafx.fxml.FXMLLoader;
import ui.Container.UiBaseDialog;

public abstract class UiDialog<T> extends UiBaseDialog
{
	public UiDialog( )
	{
		initOwner( GlobalInstance.getPrimaryStage( ) );
	}

	protected ParamFunction<T> resultCallback;

	public void showDialog( )
	{
		onPrepareDialog( );
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

	protected abstract Object getThisPtr( );




	public void setResultCallback( ParamFunction<T> resultCallback )
	{
		this.resultCallback = resultCallback;
	}
}
