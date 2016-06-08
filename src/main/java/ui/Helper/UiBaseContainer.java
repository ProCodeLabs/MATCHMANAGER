package ui.Helper;

import Common.ResourceLoader;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;



public class UiBaseContainer extends BorderPane
{
	static final int RESIZE_RECT_SIZE = 18;
	static final int RESIZE_RECT_MIN_SIZE = 50;

	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );


	boolean _isDragged = false;
	boolean _isResizing = false;

	double _xOffset = 0;
	double _yOffset = 0;


	HBox _titleBar = new HBox( );
	Label _titleLabel = new Label( );



	public UiBaseContainer( )
	{
		addEventFilters( );
		addTitleButtons( );
		_titleLabel.setPadding( new Insets( 10 ) );
		_titleLabel.getStyleClass().add( "heading" );
	}

	void addEventFilters( )
	{
		addEventFilter( MouseEvent.MOUSE_PRESSED, ( MouseEvent e ) -> {
			Stage parent = ( Stage ) getScene( ).getWindow( );

			if( e.getSceneY( ) <= _titleBar.getHeight( ) )
			{
				_xOffset = e.getSceneX( );
				_yOffset = e.getSceneY( );
				_isDragged = true;
				_isResizing = false;

				e.isConsumed( );
			}
			else if( e.getSceneX( ) >= parent.getWidth( ) - RESIZE_RECT_SIZE &&
					e.getSceneY( ) >= parent.getHeight( ) - RESIZE_RECT_SIZE &&
					e.getSceneX( ) <= parent.getWidth( ) &&
					e.getSceneY( ) <= parent.getHeight( )
					)
			{
				_xOffset = parent.getWidth( ) - e.getX( );
				_yOffset = parent.getHeight( ) - e.getY( );
				_isDragged = false;
				_isResizing = true;

				e.isConsumed( );
			}
		} );


		addEventFilter( MouseEvent.MOUSE_RELEASED, ( MouseEvent e ) -> {
			_isDragged = false;
			_isResizing = false;

			e.consume();
		} );

		addEventFilter( MouseEvent.MOUSE_DRAGGED, ( MouseEvent e ) -> {
			Stage parent = ( Stage ) getScene( ).getWindow( );

			if( _isDragged )
			{
				parent.setX( e.getScreenX( ) - _xOffset );
				parent.setY( e.getScreenY( ) - _yOffset );

				e.consume();
			}
			else if( _isResizing )
			{
				if( e.getX() + _xOffset >= RESIZE_RECT_MIN_SIZE )
				{
					parent.setWidth( e.getX( ) + _xOffset );
				}

				if( e.getY() + _yOffset >= RESIZE_RECT_MIN_SIZE )
				{
					parent.setHeight( e.getY( ) + _yOffset );
				}

				e.consume();
			}

		} );
	}


	void addTitleButtons( )
	{
		{
			Region region = new Region( );
			{
				_titleBar.setHgrow( region, Priority.ALWAYS );
			}

			Button btnClose = new Button( );
			{
				btnClose.setPadding( new Insets( 10 ) );
				btnClose.setStyle( "-fx-font-size: 1.2em" );
				btnClose.setOnAction( e -> {
					Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
					alert.setTitle( "Close?" );
					alert.setHeaderText( "Close?" );
					alert.setContentText( "Are you sure you want to Close this Window?" );

					Optional<ButtonType> result = alert.showAndWait( );
					if( result.get( ) == ButtonType.OK )
					{
						Platform.exit( );
						System.exit( 0 );
					}
				} );

			}
			btnClose.setStyle( "-fx-font-family: FontAwesome;" );
			btnClose.getStyleClass( ).add( "button-icon" );
			btnClose.setText( FontAwesome.ICON_REMOVE );


			Button btnMinimize = new Button( );
			{
				btnMinimize.setOnAction( e -> ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );
			}
			btnMinimize.setStyle( "-fx-font-size: 1.2em" );
			btnMinimize.setStyle( "-fx-font-family: FontAwesome" );
			btnMinimize.getStyleClass( ).add( "button-icon" );
			btnMinimize.setText( FontAwesome.ICON_MINUS );

			_titleBar.getChildren( ).addAll( _titleLabel, region, btnMinimize, btnClose );
		}
		setTop( _titleBar );
	}

	public void setCenter( String title, String resourceId ) throws IOException
	{
		FXMLLoader loader = new FXMLLoader( ResourceLoader.getResourceClass().getResource( resourceId ) );

		setTitle( title );
		setCenter( loader.load() );
	}

	public void setTitle( String title )
	{
		_titleLabel.setText( title );
	}


}
