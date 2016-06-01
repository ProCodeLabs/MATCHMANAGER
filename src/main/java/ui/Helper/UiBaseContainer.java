package ui.Helper;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.util.logging.Logger;


public class UiBaseContainer extends BorderPane
{
	Logger log = Logger.getLogger( this.getClass( ).getName( ) );


	boolean _isDragged = false;

	double _xOffset = 0;
	double _yOffset = 0;


	Label _titleLabel = new Label(  );


	public UiBaseContainer( )
	{
		addEventFilters( );
		addTitleButtons();
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



	void addTitleButtons()
	{
		HBox box = new HBox(  );
		{
			{
				//titleLabel.setStyle( "" );
			}
			box.getChildren().add( _titleLabel );

			Region region = new Region();
			{

			}
			box.getChildren().add( region );







		}
		setTop( box );
	}


	public void setTitle( String title )
	{
		_titleLabel.setText( title );
	}


}
