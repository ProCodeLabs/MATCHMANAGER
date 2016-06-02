package ui.Helper;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.logging.Logger;


public class UiBaseContainer extends BorderPane
{
	Logger log = Logger.getLogger( this.getClass( ).getName( ) );


	boolean _isDragged = false;

	double _xOffset = 0;
	double _yOffset = 0;


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
			if( e.getSceneY( ) <= 18 )
			{
				_xOffset = e.getSceneX( );
				_yOffset = e.getSceneY( );
				_isDragged = true;

				e.isConsumed( );
			}
		} );

		this.addEventFilter( MouseEvent.MOUSE_RELEASED, ( MouseEvent e ) -> {
			_isDragged = false;
		} );

		this.addEventFilter( MouseEvent.MOUSE_DRAGGED, ( MouseEvent e ) -> {
			if( _isDragged )
			{
				Stage parent = ( Stage ) getScene( ).getWindow( );

				parent.setX( e.getScreenX( ) - _xOffset );
				parent.setY( e.getScreenY( ) - _yOffset );
			}
		} );
	}


	void addTitleButtons( )
	{
		HBox box = new HBox( );
		{
			{
				//titleLabel.setStyle( "" );
			}
			box.getChildren( ).add( _titleLabel );

			Region region = new Region( );
			{

			}
			box.getChildren( ).add( region );


			HBox buttonBox = new HBox( );
			{
				Button btnClose = FontAwesomeHelper.createIconButton( FontAwesome.ICON_FOLDER_CLOSE, 2 );

				buttonBox.getChildren( ).add( btnClose );
			}
			box.getChildren( ).add( buttonBox );

			box.getChildren( ).add( FontAwesomeHelper.createIconLabel( FontAwesome.ICON_GLASS, 1 ) );

		}
		setTop( box );
	}


	public void setTitle( String title )
	{
		_titleLabel.setText( title );
	}


}
