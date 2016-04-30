/**
 * Created by JOHNY on 30.04.2016.
 */

//TODO DO STUFF!

import javafx.application.Application;
import javafx.stage.Stage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;

//Hello Worldasdasdwerwer asdasdwerwer

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SqlJetException {

        String DB_NAME = "DATABASE.sqlite";

        File dbFile = new File(DB_NAME);
        dbFile.delete();

        SqlJetDb db = SqlJetDb.open(dbFile, true);
        db.getOptions().setAutovacuum(true);
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
             db.getOptions().setUserVersion(1);
            } finally {
             db.commit();
            }
    }
}
