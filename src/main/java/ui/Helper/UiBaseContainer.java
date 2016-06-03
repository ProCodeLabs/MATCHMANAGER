package ui.Helper;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

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
				Button btnMinimize = FontAwesomeHelper.createIconButton( FontAwesome.ICON_MINUS, 2 );
				{
					btnMinimize.setOnAction( e -> ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );
				}

				Button btnClose = FontAwesomeHelper.createIconButton( FontAwesome.ICON_REMOVE, 2 );
				{

				}
				buttonBox.getChildren( ).addAll( btnMinimize, btnClose );


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
