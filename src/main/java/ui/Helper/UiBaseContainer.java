package ui.Helper;

import javafx.application.Platform;
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

import java.util.Optional;
import java.util.logging.Logger;


public class UiBaseContainer extends BorderPane
{
	static final int RESIZE_RECT_SIZE = 18;

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
	}

	void addEventFilters( )
	{
		//TODO: fix multiple monitor movement bug
		this.addEventFilter( MouseEvent.MOUSE_PRESSED, ( MouseEvent e ) -> {
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
					e.getSceneX( ) <= parent.getWidth( ) && e.getSceneY( ) <= parent.getHeight( )
					)
			{
				_xOffset = parent.getWidth( ) - e.getX( );
				_yOffset = parent.getHeight( ) - e.getY( );
				_isDragged = false;
				_isResizing = true;

				e.isConsumed( );
			}
		} );


		this.addEventFilter( MouseEvent.MOUSE_RELEASED, ( MouseEvent e ) -> {
			_isDragged = false;
			_isResizing = false;
		} );

		this.addEventFilter( MouseEvent.MOUSE_DRAGGED, ( MouseEvent e ) -> {
			Stage parent = ( Stage ) getScene( ).getWindow( );

			if( _isDragged )
			{
				parent.setX( e.getScreenX( ) - _xOffset );
				parent.setY( e.getScreenY( ) - _yOffset );
			}
			else if( _isResizing )
			{
				parent.setWidth( e.getX( ) + _xOffset );
				parent.setHeight( e.getY( ) + _yOffset );
			}

		} );
	}


	void addTitleButtons( )
	{
		{
			{
				//titleLabel.setStyle( "" );
			}

			Region region = new Region( );
			{
				_titleBar.setHgrow( region, Priority.ALWAYS );
			}

			HBox buttonBox = new HBox( );
			{

				Button btnClose = new Button( );
				{

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
				btnClose.getStyleClass().add( "button-icon" );
				btnClose.setText( FontAwesome.ICON_REMOVE );


				Button btnMinimize = new Button(  );
				{
					btnMinimize.setOnAction( e -> ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );
				}
				btnMinimize.setStyle( "-fx-font-family: FontAwesome" );
				btnMinimize.getStyleClass().add( "button-icon" );
				btnMinimize.setText( FontAwesome.ICON_MINUS );

				buttonBox.getChildren( ).addAll( btnMinimize,btnClose);

			}
			_titleBar.getChildren( ).addAll( _titleLabel, region, buttonBox );
		}
		setTop( _titleBar );
	}


	public void setTitle( String title )
	{
		_titleLabel.setText( title );
	}


}
