package ui.Helper;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class UiStyleDesc
{
	private static final int RESIZE_RECT_SIZE = 18;
	private static final int RESIZE_RECT_MIN_SIZE = 50;

	private boolean isContainer = false;
	private boolean isDragged = false;
	private boolean isResizing = false;

	private double xOffset = 0;
	private double yOffset = 0;


	private HBox titleBar = new HBox( );
	private Label titleLabel = new Label( );
	private Pane targetPane;


	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public UiStyleDesc( Pane target, boolean isContainer )
	{
		titleLabel.setPadding( new Insets( 10 ) );
		titleLabel.getStyleClass( ).add( "h1" );
		this.isContainer = isContainer;
		targetPane = target;

		initializeTitleButtons( );
		initializeEventFilter( target );
	}

	private void initializeTitleButtons( )
	{
		Region region = new Region( );
		{
			HBox.setHgrow( region, Priority.ALWAYS );
		}
		titleBar.getChildren( ).addAll( titleLabel, region );

		if( isContainer )
		{
			Button btnMinimize = new Button( );
			{
				btnMinimize.setStyle( "-fx-font-size: 1.2em" );
				btnMinimize.setStyle( "-fx-font-family: FontAwesome" );
				btnMinimize.getStyleClass( ).add( "button-icon" );
				btnMinimize.setText( FontAwesome.ICON_MINUS );
				btnMinimize.setOnAction( e ->
					targetPane.fireEvent( new Event( UiEvent.MINIMIZE_WINDOW ) )
				);
			}
			titleBar.getChildren( ).add( btnMinimize );
		}

		Button btnClose = new Button( );
		{
			btnClose.setPadding( new Insets( 10 ) );
			btnClose.setStyle( "-fx-font-size: 1.2em" );
			btnClose.setStyle( "-fx-font-family: FontAwesome;" );
			btnClose.getStyleClass( ).add( "button-icon" );

			btnClose.setOnAction( e ->
				targetPane.fireEvent( new Event( UiEvent.CLOSE_WINDOW ) )
			 );

			btnClose.setText( FontAwesome.ICON_REMOVE );
		}
		titleBar.getChildren( ).add( btnClose );
	}

	private void initializeEventFilter( Pane target )
	{
		target.addEventFilter( MouseEvent.MOUSE_PRESSED, ( MouseEvent e ) -> {

			Stage parent = ( Stage ) target.getScene( ).getWindow( );

			if( e.getSceneY( ) <= titleBar.getHeight( ) )
			{
				xOffset = e.getSceneX( );
				yOffset = e.getSceneY( );
				isDragged = true;
				isResizing = false;
			}
			else if( e.getSceneX( ) >= parent.getWidth( ) - RESIZE_RECT_SIZE &&
					e.getSceneY( ) >= parent.getHeight( ) - RESIZE_RECT_SIZE &&
					e.getSceneX( ) <= parent.getWidth( ) &&
					e.getSceneY( ) <= parent.getHeight( ) &&
					isContainer
					)
			{
				xOffset = parent.getWidth( ) - e.getX( );
				yOffset = parent.getHeight( ) - e.getY( );
				isDragged = false;
				isResizing = true;
			}
		} );


		target.addEventFilter( MouseEvent.MOUSE_RELEASED, ( MouseEvent e ) -> {
			isDragged = false;
			isResizing = false;

			target.fireEvent( new Event( UiEvent.FINISH_RESIZE ) );
		} );

		target.addEventFilter( MouseEvent.MOUSE_DRAGGED, ( MouseEvent e ) -> {

			Stage parent = ( Stage ) target.getScene( ).getWindow( );

			if( isDragged )
			{
				parent.setX( e.getScreenX( ) - xOffset );
				parent.setY( e.getScreenY( ) - yOffset );

				e.consume( );
			}
			else if( isResizing )
			{
				if( e.getX( ) + xOffset >= RESIZE_RECT_MIN_SIZE )
				{
					parent.setWidth( e.getX( ) + xOffset );
				}

				if( e.getY( ) + yOffset >= RESIZE_RECT_MIN_SIZE )
				{
					parent.setHeight( e.getY( ) + yOffset );
				}

				e.consume( );
			}
		} );
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////


	public HBox getTitleBar( )
	{
		return titleBar;
	}

	public void setTitle( String title )
	{
		titleLabel.setText( title );
	}


}
