package ui.Helper;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class UiStyleDesc
{
	static final int RESIZE_RECT_SIZE = 18;
	static final int RESIZE_RECT_MIN_SIZE = 50;

	boolean _isContainer = false;
	boolean _isDragged = false;
	boolean _isResizing = false;

	double _xOffset = 0;
	double _yOffset = 0;


	HBox _titleBar = new HBox( );
	Label _titleLabel = new Label( );

	Runnable _onCloseButton = ( ) -> {
		throw new NotImplementedException( );
	};
	Runnable _onMinimizeButton = ( ) -> {
		throw new NotImplementedException( );
	};



	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public UiStyleDesc( Pane target, boolean isContainer )
	{
		_titleLabel.setPadding( new Insets( 10 ) );
		_titleLabel.getStyleClass( ).add( "h1" );
		_isContainer = isContainer;

		initializeTitleButtons( );
		initializeEventFilter( target );
	}

	void initializeTitleButtons(  )
	{
		Region region = new Region( );
		{
			_titleBar.setHgrow( region, Priority.ALWAYS );
		}
		_titleBar.getChildren().addAll( _titleLabel, region );

		if(_isContainer )
		{
			Button btnMinimize = new Button( );
			{
				btnMinimize.setStyle( "-fx-font-size: 1.2em" );
				btnMinimize.setStyle( "-fx-font-family: FontAwesome" );
				btnMinimize.getStyleClass( ).add( "button-icon" );
				btnMinimize.setText( FontAwesome.ICON_MINUS );
				btnMinimize.setOnAction( e -> _onMinimizeButton.run( ) );
			}
			_titleBar.getChildren().add( btnMinimize );
		}

		Button btnClose = new Button( );
		{
			btnClose.setPadding( new Insets( 10 ) );
			btnClose.setStyle( "-fx-font-size: 1.2em" );
			btnClose.setStyle( "-fx-font-family: FontAwesome;" );
			btnClose.getStyleClass( ).add( "button-icon" );

			btnClose.setOnAction( e -> _onCloseButton.run( ) );
			btnClose.setText( FontAwesome.ICON_REMOVE );
		}
		_titleBar.getChildren().add( btnClose );
	}

	void initializeEventFilter( Pane target )
	{
		target.addEventFilter( MouseEvent.MOUSE_PRESSED, ( MouseEvent e ) -> {

			Stage parent = ( Stage ) target.getScene( ).getWindow( );

			if( e.getSceneY( ) <= _titleBar.getHeight( ) )
			{
				_xOffset = e.getSceneX( );
				_yOffset = e.getSceneY( );
				_isDragged = true;
				_isResizing = false;
			}
			else if( e.getSceneX( ) >= parent.getWidth( ) - RESIZE_RECT_SIZE &&
					e.getSceneY( ) >= parent.getHeight( ) - RESIZE_RECT_SIZE &&
					e.getSceneX( ) <= parent.getWidth( ) &&
					e.getSceneY( ) <= parent.getHeight( ) &&
					_isContainer
					)
			{
				_xOffset = parent.getWidth( ) - e.getX( );
				_yOffset = parent.getHeight( ) - e.getY( );
				_isDragged = false;
				_isResizing = true;
			}
		} );


		target.addEventFilter( MouseEvent.MOUSE_RELEASED, ( MouseEvent e ) -> {
			_isDragged = false;
			_isResizing = false;
		} );

		target.addEventFilter( MouseEvent.MOUSE_DRAGGED, ( MouseEvent e ) -> {

			Stage parent = ( Stage ) target.getScene( ).getWindow( );

			if( _isDragged )
			{
				parent.setX( e.getScreenX( ) - _xOffset );
				parent.setY( e.getScreenY( ) - _yOffset );

				e.consume( );
			}
			else if( _isResizing )
			{
				if( e.getX( ) + _xOffset >= RESIZE_RECT_MIN_SIZE )
				{
					parent.setWidth( e.getX( ) + _xOffset );
				}

				if( e.getY( ) + _yOffset >= RESIZE_RECT_MIN_SIZE )
				{
					parent.setHeight( e.getY( ) + _yOffset );
				}

				e.consume( );
			}
		} );
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////


	public HBox getTitleBar( )
	{
		return _titleBar;
	}

	public void setTitle( String title )
	{
		_titleLabel.setText( title );
	}


	public void setOnCloseButton( Runnable cb )
	{
		_onCloseButton = cb;
	}

	public void setOnMinimizeButton( Runnable cb )
	{
		_onMinimizeButton = cb;
	}


}
