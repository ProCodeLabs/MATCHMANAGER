package ui.StageCore.DialogStages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import ui.StageCore.DialogStages.Helper.LogListEntry;
import ui.StageCore.Helper.UiStage;

public class DebugLogViewStage extends UiStage
{
	private static final ObservableList<LogListEntry> logList = FXCollections.observableArrayList( );

	private static final String RESOURCE_ID = "fxml/centerContent/debugLogView.fxml";
	private static final double WINDOW_POS = 50;

	private TableView tableView;

	@Override
	public void showWindow( )
	{
		if( createStage( "LOG", 600, 300 ) )
		{
			tableView = ( TableView ) getContainer( ).getUiHelper( ).getElementById( "logTableView" );

			TableColumn columnDate = ( TableColumn ) tableView.getColumns( ).get( 0 );
			TableColumn columnClass = ( TableColumn ) tableView.getColumns( ).get( 1 );
			TableColumn columnType = ( TableColumn ) tableView.getColumns( ).get( 2 );
			TableColumn columnMessage = ( TableColumn ) tableView.getColumns( ).get( 3 );

			columnDate.setCellValueFactory( new PropertyValueFactory<LogListEntry, String>( "date" ) );
			columnClass.setCellValueFactory( new PropertyValueFactory<LogListEntry, String>( "thisClass" ) );
			columnType.setCellValueFactory( new PropertyValueFactory<LogListEntry, String>( "type" ) );
			columnMessage.setCellValueFactory( new PropertyValueFactory<LogListEntry, String>( "message" ) );

			tableView.setItems( logList );
		}
	}

	@Override
	public String getResourceId( )
	{
		return RESOURCE_ID;
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

	public static void addLogListEntry( LogListEntry entry )
	{
		logList.add( entry );
	}
}
