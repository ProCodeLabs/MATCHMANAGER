package ui.Controller;

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

import static Common.Files.PATH;

public class SelectDatabaseController implements Initializable
{
	private ObservableList<String> list = FXCollections.observableArrayList( );

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
			list.add( name );
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
			list.remove( getSelected( ) );
		}


	}

	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		TaskManager.runUiTask( ( ) -> {
			scanDataFolder( list );
		} );
		dataList.setItems( list );
	}


	public String getSelected( )
	{
		return dataList.getSelectionModel( ).getSelectedItem( );
	}

	public String getSelectedPath( )
{
	return PATH + File.separator + getSelected( );
}

	public void scanDataFolder( ObservableList<String> data )
	{
		String path = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
		File f = new File( path );
		if( f.exists( ) && !f.isDirectory( ) )
		{
			f.mkdir( );
		}

		for( File i : f.listFiles( ) )
		{
			String s = i.getName( ).replace( ".sqlite", "" );
			Platform.runLater( ( ) -> {

				data.add( s );
			} );
		}
	}
}
