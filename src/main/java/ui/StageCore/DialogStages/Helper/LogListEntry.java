package ui.StageCore.DialogStages.Helper;


import javafx.beans.property.SimpleStringProperty;

public class LogListEntry
{
	private final SimpleStringProperty date;
	private final SimpleStringProperty thisClass;
	private final SimpleStringProperty type;
	private final SimpleStringProperty message;

	public LogListEntry( String date, String thisClass, String type, String message )
	{
		this.message = new SimpleStringProperty( message );
		this.thisClass = new SimpleStringProperty( thisClass );
		this.type = new SimpleStringProperty( type );
		this.date = new SimpleStringProperty( date );
	}

	public void setDate( String date )
	{
		this.date.set( date );
	}

	public String getDate( )
	{
		return date.get( );
	}

	public void setThisClass( String thisClass )
	{
		this.thisClass.set( thisClass );
	}

	public String getThisClass( )
	{
		return thisClass.get( );
	}

	public void setType( String type )
	{
		this.type.set( type );
	}

	public String getType( )
	{
		return type.get( );
	}


	public void setMessage( String message )
	{
		this.message.set( message );
	}

	public String getMessage( )
	{
		return message.get( );
	}
}
