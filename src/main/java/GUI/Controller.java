package GUI;

import BaccaratGame.BaccaratInfo;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import BaccaratGame.ConnectionSocket;

import java.util.ArrayList;

public class Controller {
    protected ToggleGroup group;
    protected static ConnectionSocket connection;
    protected static ArrayList<BaccaratInfo> roundStatsList = new ArrayList<>();
    protected static Scene gameSceneState;
}
