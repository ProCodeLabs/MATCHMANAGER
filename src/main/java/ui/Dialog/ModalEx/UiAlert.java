package ui.Dialog.ModalEx;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;


public class UiAlert extends Alert
{
	public UiAlert( AlertType alertType )
	{
		super( alertType );
	}


	public void addStackTraceArea( Exception e )
	{
		StringWriter sw = new StringWriter( );
		PrintWriter pw = new PrintWriter( sw );
		e.printStackTrace( pw );
		String exceptionText = sw.toString( );

		Label label = new Label( "Stacktrace:" );

		TextArea textArea = new TextArea( exceptionText );
		{
			textArea.setMaxWidth( Double.MAX_VALUE );
			textArea.setMaxHeight( Double.MAX_VALUE );

			textArea.setEditable( false );
			textArea.setWrapText( true );

			GridPane.setVgrow( textArea, Priority.ALWAYS );
			GridPane.setHgrow( textArea, Priority.ALWAYS );
		}


		GridPane pane = new GridPane( );
		{
			pane.setMaxWidth( Double.MAX_VALUE );

			pane.add( label, 0, 0 );
			pane.add( textArea, 0, 1 );
		}

		getDialogPane( ).setExpandableContent( pane );
	}


}
