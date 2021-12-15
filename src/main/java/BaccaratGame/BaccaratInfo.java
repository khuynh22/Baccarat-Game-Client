package BaccaratGame;

import java.io.Serializable;
import java.util.ArrayList;

// send information back and forth between the server and clients
public class BaccaratInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public double bid;
    public String hand;

    public ArrayList<String> playerHand = new ArrayList<>();
    public ArrayList<String> bankerHand = new ArrayList<>();

    public String winner = "";

    public double winnings = 0.0;

    // 2 args constructor for baccarat
    public BaccaratInfo(double bid, String hand) {
        this.bid = bid;
        this.hand = hand;
    }
}