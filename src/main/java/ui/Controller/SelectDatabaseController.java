package ui.Controller;

import Common.LogLevel;
import Common.TaskManager;
import Database.Connection.DatabaseHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static Common.Files.path;

public class SelectDatabaseController implements Initializable
{
	public static final String RESOURCE_ID = "fxml/centerContent/selectDatabase.fxml";

	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );

	ObservableList<String> _fileList = FXCollections.observableArrayList( );

	@FXML
	Button newButton;

	@FXML
	Button loadButton;

	@FXML
	Button deleteButton;

	@FXML
	ListView<String> dataList;


	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		TaskManager.runUiTask( ( ) -> scanDataFolder( _fileList ) );
		dataList.setItems( _fileList );
	}



	@FXML
	public void buttonLoadClicked( )
	{
		//TODO: Load selected data
		String datapath = getSelectedPath( );
		System.out.println( "Load clicked" );
	}

	@FXML
	public void buttonNewClicked( )
	{
		//TODO: Touernamentname + File

		TextInputDialog dialog = new TextInputDialog( "" );
		dialog.setTitle( "Create Database" );
		dialog.setHeaderText( "Create new Database" );
		dialog.setContentText( "Please enter a Database name:" );
		Optional<String> result = dialog.showAndWait( );
		result.ifPresent( name -> {
			DatabaseHandler.createNewDatabase( name );
			_fileList.add( name );
		} );
	}

	@FXML
	public void buttonDeleteClicked( )
	{
		Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
		alert.setTitle( "Dalete" );
		alert.setHeaderText( "Delete" );
		alert.setContentText( "Are you sure you want to Delete this?" );

		Optional<ButtonType> result = alert.showAndWait( );
		if( result.get( ) == ButtonType.OK )
		{
			DatabaseHandler.deleteDatabase( getSelectedPath( ) );
			_fileList.remove( getSelected( ) );
		}


	}




	public void scanDataFolder( ObservableList<String> data )
	{
		String path = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
		File f = new File( path );
		if( !f.exists( ) )
		{
			f.mkdir( ) ;
		}

		try
		{
			for( File i : f.listFiles( ) )
			{
				if( i.getName().lastIndexOf( ".sqlite" ) > 0 )
				{
					String s = i.getName( ).replace( ".sqlite", "" );
					Platform.runLater( ( ) -> data.add( s ) );
				}
			}
		}
		catch( NullPointerException e )
		{
			log.log( LogLevel.ERROR, "File is null! " + e.getMessage( ) );
		}
	}



	public String getSelected( )
	{
		return dataList.getSelectionModel( ).getSelectedItem( );
	}

	public String getSelectedPath( )
	{
		return path + File.separator + getSelected( );
	}
}
