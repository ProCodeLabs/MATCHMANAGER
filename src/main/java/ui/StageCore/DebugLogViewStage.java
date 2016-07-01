package ui.StageCore;

import Common.UtilLogger.LogListEntry;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import ui.StageCore.Helper.UiStage;
import ui.StageCore.StageEvents.DebugLogEvent;

public class DebugLogViewStage extends UiStage
{
	public static final String RESOURCE_ID = "fxml/centerContent/debugLogView.fxml";
	private static final double WINDOW_POS = 50;

	private TableView tableView;

	@Override
	public void showWindow( )
	{
		if( createStage( "LOG", RESOURCE_ID, 600, 300 ) )
		{
			showStage( );

			tableView = ( TableView ) getContainer( ).getUiHelper( ).getElementById( "logTableView" );

			TableColumn columnDate = (TableColumn )tableView.getColumns().get( 0 );
			TableColumn columnType = (TableColumn )tableView.getColumns().get( 1 );
			TableColumn columnMessage = (TableColumn )tableView.getColumns().get( 2 );

			columnDate.setCellValueFactory( new PropertyValueFactory<LogListEntry, String >( "date" ) );
			columnType.setCellValueFactory( new PropertyValueFactory<LogListEntry, String >( "type" ) );
			columnMessage.setCellValueFactory( new PropertyValueFactory<LogListEntry, String >( "message" ) );

			tableView.setItems( DebugLogEvent.logList );
		}
	}

	@Override
	public void onCloseStage( )
	{
	}

	@Override
	public void onSetPosition( )
	{
		Pair<Double, Double> pos = getPrimaryWindowPos( );

		double windowWidth = pos.getKey( ) - ( getStage( ).getWidth( ) + WINDOW_POS );
		if( windowWidth > 0 )
		{
			getStage( ).setX( windowWidth );
		}
		else
		{
			getStage( ).setX( pos.getKey( ) );
		}

		getStage( ).setY( pos.getValue( ) );
	}
}
