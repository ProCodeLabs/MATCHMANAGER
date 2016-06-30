package ui.StageCore;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Helper.UiBaseContainer;
import ui.StageCore.Helper.IUiStage;

public class DebugLogViewStage implements IUiStage
{


	@Override
	public void showWindow( )
	{
		Stage s = new Stage();

		s.setScene( new Scene( new UiBaseContainer()) );

		s.show();

	}


}
