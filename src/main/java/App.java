/**
 * Created by JOHNY on 30.04.2016.
 */

//TODO DO STUFF!
//TODO commit me

//TODO last commit today xD

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.logging.Logger;

//Test

public class App extends Application {

    Logger log = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SqlJetException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/layout.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception ex) {
            log.info("Error in start: "+ex);
        }

        databaseSetup();

    }

    public void databaseSetup() throws SqlJetException {
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
