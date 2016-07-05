package Common;

import java.io.File;

public class Constants
{
	public static final String STORAGE_FOLDER = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
	public static final String FILE_EXT = ".sqlite";

	public static final String LOG_FILE_PATH = STORAGE_FOLDER + File.separator + "manager.log";


	public static final String PATH_FONT_RESOURCE = "fonts/fontawesome-webfont.ttf";
	public static final String PATH_STYLE_RESOURCE = "styles/style.css";

	public static final String ICON = "images/mm_icon.png";
	public static final String ACTIVE = "images/blue_active.png";
	public static final String DONE = "images/blue_done.png";
}
