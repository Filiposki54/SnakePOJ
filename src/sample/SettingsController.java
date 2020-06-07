package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Enums.Difficulty;

import java.io.IOException;

public class SettingsController {

    public Button up_stetting;
    public Button down_setting;
    public Button left_setting;
    public Button right_setting;

    @FXML
    void easypush(ActionEvent event) {
        Game.difficulty = Difficulty.EASY;
    }

    @FXML
    void mediumpush(ActionEvent event) {
        Game.difficulty = Difficulty.MEDIUM;
    }

    @FXML
    void hardpush(ActionEvent event) {
        Game.difficulty = Difficulty.HARD;
    }


    @FXML
    void return_push(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("fxml/Menu.fxml"));
        Scene exit = new Scene(menu);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(exit);
        stage.show();
    }

    KeyCode upKey = KeyCode.W;
    KeyCode downKey = KeyCode.S;
    KeyCode leftKey = KeyCode.A;
    KeyCode rightKey = KeyCode.D;

    public void savepush(ActionEvent event) {
        Game.RightKey = rightKey;
        Game.LeftKey = leftKey;
        Game.UpKey = upKey;
        Game.DownKey = downKey;
    }

    public void GetUPKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()!= downKey && keyEvent.getCode() != leftKey && keyEvent.getCode() != rightKey) {
            upKey = keyEvent.getCode();
            String UP = upKey.toString();
            up_stetting.setText(UP);
        }
    }

    public void GetDownKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()!= upKey && keyEvent.getCode() != leftKey && keyEvent.getCode() != rightKey) {
            downKey = keyEvent.getCode();
            String UP = downKey.toString();
            down_setting.setText(UP);
        }
    }

    public void GetLeftKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()!= downKey && keyEvent.getCode() != upKey && keyEvent.getCode() != rightKey) {
            leftKey = keyEvent.getCode();
            String UP = leftKey.toString();
            left_setting.setText(UP);
        }
    }

    public void GetRightKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()!= downKey && keyEvent.getCode() != leftKey && keyEvent.getCode() != upKey){
        rightKey = keyEvent.getCode();
        String UP = rightKey.toString();
        right_setting.setText(UP);
        }
    }
}

