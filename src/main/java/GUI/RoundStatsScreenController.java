package GUI;

import BaccaratGame.BaccaratInfo;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RoundStatsScreenController extends Controller {
    @FXML private ListView roundStatsPane;

    public void backButtonAction(Event e) throws IOException {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(this.gameSceneState);
        window.setTitle("Play game");
        window.show();
    }

    public void buildRoundInfoBox() {
        this.roundStatsPane.getItems().clear();
        for (int i = 0; i < this.roundStatsList.size(); i++) {
            this.roundStatsPane.getItems().add(this.getRoundInfo(this.roundStatsList.get(i), i+1));
        }
    }

    public Text getInfoText(String text) {
        Text info = new Text(text);
        info.setStyle("-fx-font-weight: bold;");
        return info;
    }

    public VBox getRoundInfo(BaccaratInfo roundInfo, int round) {
        double winnings = roundInfo.winnings;
        double bid = roundInfo.bid;
        String hand = roundInfo.hand;
        String winner = roundInfo.winner;

        VBox displayRoundInfo = new VBox(
                new Text("Round: " + round),
                new HBox(10, this.getInfoText("Hand:"), new Text(hand)),
                new HBox(10, this.getInfoText("Bid placed:"), new Text(String.format("$%.2f", bid))),
                new HBox(10, this.getInfoText("Winner:"), new Text(winner)),
                new HBox(10, this.getInfoText("Winnings:"), new Text(String.format("$%.2f", winnings)))
        );
        return displayRoundInfo;
    }
}
