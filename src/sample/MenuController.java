package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends Main {

    @FXML
    void exitpush(ActionEvent event) throws IOException {
       System.exit(0);
    }

    @FXML
    void playpush(ActionEvent event) throws IOException {
        Stage started  = new Stage();
        Game game = new Game();
        game.start(started);
    }

    @FXML
    void settingpush(ActionEvent event) throws IOException {
        Parent settings = FXMLLoader.load(getClass().getResource("fxml/Settings.fxml"));
        Scene settingScene = new Scene(settings);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(settingScene);
        stage.show();
    }

}
