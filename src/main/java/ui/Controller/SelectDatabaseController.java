package ui.Controller;

import Common.TaskManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectDatabaseController implements Initializable
{
	@FXML
	Button newButton;
	@FXML
	Button loadButton;
	@FXML
	Button deleteButton;

	@FXML
	ListView<String> dataList;

	@FXML
	public void buttonLoadClicked( )
	{
		System.out.println( "Load clicked" );
	}

	@FXML
	public void buttonNewClicked( )
	{
		System.out.println( "New Clicked" );
	}

	@FXML
	public void buttonDeleteClicked( )
	{
		System.out.println( "Delete Clicked" );
	}

	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		ObservableList<String> list = FXCollections.observableArrayList( );

		TaskManager.runUiTask( ()->{
			scanDataFolder( list );
		} );

		dataList.setItems( list );
	}

	public void scanDataFolder( ObservableList<String> data )
	{
		String path = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
		File f = new File( path );
		f.mkdir( );
		for( File i : f.listFiles( ) )
		{
			try
			{
			 Thread.sleep( 250 );
			}
			catch( Exception e )
			{

			}

			Platform.runLater( ( ) -> {
				data.add( i.getName( ) );
			} );
		}
	}
}
