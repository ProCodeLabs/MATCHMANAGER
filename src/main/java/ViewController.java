import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JOHNY on 30.04.2016.
 */
public class ViewController implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    Button closeButton;

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
