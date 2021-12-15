package GUI;

import BaccaratGame.BaccaratInfo;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameScreenController extends Controller {
    @FXML private TextField playerBidText;
    @FXML private ToggleButton playerToggleButton;
    @FXML private ToggleButton bankerToggleButton;
    @FXML private ImageView bankerLeftCard;
    @FXML private ImageView bankerRightCard;
    @FXML private ImageView bankerExtraCard;
    @FXML private ImageView playerLeftCard;
    @FXML private ImageView playerRightCard;
    @FXML private ImageView playerExtraCard;
    @FXML private Pane bankerPane;
    @FXML private Pane playerPane;
    @FXML private Label bankerWinLabel;
    @FXML private Label playerWinLabel;
    @FXML private Label winningsLabel;

    private double winningsCount = 0;

    // private helper to get the right file root of the card
    private Image imageFromCard(String card) {
        String path = "/Cards/" + card + ".png";
        Image image = new Image(getClass().getResource(path).toExternalForm());
        return image;
    }
    public void toggleButton() {
        group = new ToggleGroup();
        playerToggleButton.setToggleGroup(group);
        bankerToggleButton.setToggleGroup(group);

        if (playerToggleButton.isSelected()) {
            playerToggleButton.setStyle("-fx-background-color: orange");
            bankerToggleButton.setStyle("-fx-background-color: black");
        }

        else if (bankerToggleButton.isSelected()) {
            bankerToggleButton.setStyle("-fx-background-color: orange");
            playerToggleButton.setStyle("-fx-background-color: blue");
        }

        else if (!(bankerToggleButton.isSelected() || playerToggleButton.isSelected())) {
            bankerToggleButton.setStyle("-fx-background-color: black");
            playerToggleButton.setStyle("-fx-background-color: blue");
        }
    }

    // quit button for fxml
    public void quitButton(Event e) throws IOException {
        Parent quitScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/QuitScreen.fxml")));
        Scene quitScene = new Scene(quitScreen);

        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(quitScene);
        window.setTitle("Quit game");
        window.show();
    }

    // round stats button for fxml
    public void roundStatsButton(Event e) throws IOException {
        Parent roundStatsScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RoundStatsScreen.fxml")));
        Scene roundStatsScene = new Scene(roundStatsScreen);
        Scene currentScene = ((Node)e.getSource()).getScene();
        this.gameSceneState = currentScene;
        Stage window = (Stage) currentScene.getWindow();
        window.setScene(roundStatsScene);
        window.setTitle("Round stats");
        window.show();
    }

    public void startButtonAction(Event e) {
        // Hide extra cards
        this.bankerExtraCard.setStyle("visibility: hidden;");
        this.playerExtraCard.setStyle("visibility: hidden;");

        double bid;
        try {
            bid = Double.parseDouble(playerBidText.getText());
        } catch (Exception err) {
            System.out.println("Bid value must be a number");
            return;
        }

        String hand = "Player";
        if (bankerToggleButton.isSelected())
            hand = "Banker";

        try {
            connection.send(bid, hand);
            BaccaratInfo res = connection.receive();

            this.roundStatsList.add(res);

            // Change banker cards
            this.bankerLeftCard.setImage(this.imageFromCard(res.bankerHand.get(0)));
            this.bankerRightCard.setImage(this.imageFromCard(res.bankerHand.get(1)));

            // Change player cards
            this.playerLeftCard.setImage(this.imageFromCard(res.playerHand.get(0)));
            this.playerRightCard.setImage(this.imageFromCard(res.playerHand.get(1)));

            // Show extra banker card
            if (res.bankerHand.size() == 3) {
                this.bankerExtraCard.setStyle("visibility: none;");
                this.bankerExtraCard.setImage(this.imageFromCard(res.bankerHand.get(2)));
            }

            // Show extra player card
            if (res.playerHand.size() == 3) {
                this.playerExtraCard.setStyle("visibility: none;");
                this.playerExtraCard.setImage(this.imageFromCard(res.playerHand.get(2)));
            }

            // Display total winnings
            winningsCount += res.winnings;
            winningsLabel.setText("Winnings: $" + String.format("%.2f", winningsCount));

            // Visual feedback showing winner
            if(res.winner.equals("Banker")) {
                bankerWinLabel.setText("Banker won!");
                bankerWinLabel.setStyle("-fx-color: green");
                playerWinLabel.setText("");
                bankerPane.setStyle("-fx-border-color: green; -fx-border-width: 5px");
                playerPane.setStyle("-fx-border-color: transparent");
            } else if(res.winner.equals("Player")) {
                playerWinLabel.setText("Player won!");
                playerWinLabel.setStyle("-fx-color: green");
                bankerWinLabel.setText("");
                playerPane.setStyle("-fx-border-color: green; -fx-border-width: 5px");
                bankerPane.setStyle("-fx-border-color: transparent");
            } else {
                playerWinLabel.setText("It's a Draw!");
                playerWinLabel.setStyle("-fx-text-fill: brown");
                bankerWinLabel.setText("It's a Draw!");
                bankerWinLabel.setStyle("-fx-text-fill: brown");
                bankerPane.setStyle("-fx-border-color: brown; -fx-border-width: 5px");
                playerPane.setStyle("-fx-border-color: brown; -fx-border-width: 5px");
            }

        } catch (Exception err) {
            System.out.println("Something went wrong. Cannot connect to the server. Please retry!");
            return;
        }
    }
}
