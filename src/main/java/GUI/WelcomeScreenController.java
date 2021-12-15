package GUI;

import  BaccaratGame.ConnectionSocket;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController extends Controller {
    @FXML private TextField portNumberText;
    @FXML private TextField ipAddressText;

    public void connectButtonAction(Event e) throws IOException {
        System.out.println("Connect button clicked");
        String ipAddress = ipAddressText.getText();
        int portNumber = 5555;

        try {
            portNumber = Integer.parseInt(portNumberText.getText());
        } catch (Exception err) {
            System.out.println("Port number should be an integer");
            return;
        }

        connection = new ConnectionSocket(data -> {
            Platform.runLater(() -> {
                System.out.println(data);
            });
        }, ipAddress, portNumber);

        Parent gameScreen = FXMLLoader.load(getClass().getResource("/GameScreen.fxml"));
        Scene gameScene = new Scene(gameScreen);

        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.setTitle("Play new game!");
        window.show();
    }
}
