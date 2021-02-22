package com.github.martinfrank.multiplayerSwtClient.control;

import com.github.martinfrank.multiplayerSwtClient.view.MapCanvas;
import com.github.martinfrank.multiplayerclient.MultiplayerClient;
import com.github.martinfrank.multiplayerprotocol.meta.PlayerMetaData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String STEERING_PREFIX = "steering";
    private static final String LOGIN_BUTTON_ID = "login";
    private static final String ENTER_BUTTON_ID = "enter";


    @FXML
    public ScrollPane mapScrollPane;

    @FXML
    public VBox vbox;

    @FXML
    public HBox hbox;

    @FXML
    private MapCanvas mapCanvas;

    @FXML
    private Pane steering;

    @FXML
    private Pane connection;

    private MultiplayerClient multiplayerClient;


    Controller() {
    }


    public void init() {
        VBox.setVgrow(hbox, Priority.ALWAYS);
        HBox.setHgrow(mapScrollPane, Priority.ALWAYS);

        includeSteering();
        includeConnection();

        mapCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            LOGGER.debug("klicked");
            int x = (int) mouseEvent.getX();
            int y = (int) mouseEvent.getY();
        });

    }

    private void includeSteering() {
        steering.getChildren().forEach(n -> ((Button) n).setOnAction(
                event -> steer(n.getId().replace(STEERING_PREFIX, ""))));
    }

    private void includeConnection() {
        Button enterButton = (Button) connection.getChildren().stream()
                .filter(c -> ENTER_BUTTON_ID.equals(c.getId())).findAny().orElse(null);
        if (enterButton != null) {
            enterButton.setOnAction(event -> connect());
        }
        Button loginButton  = (Button) connection.getChildren().stream()
                .filter(c -> LOGIN_BUTTON_ID.equals(c.getId())).findAny().orElse(null);
        if (loginButton != null) {
            loginButton.setOnAction(event -> login());
        }
    }

    private void connect() {
        //FIXME better naming =)
        multiplayerClient.connectToArea();

    }

    private void login() {
        //TODO remove it - not necessary
        PlayerMetaData playerMetaData = multiplayerClient.getPlayerData();
        LOGGER.debug("connect, Player Meta Data: {}", playerMetaData);
    }

    private void steer(String dirString) {
//        try {
//            Direction direction = Direction.valueOf(dirString);
//            ObjectMapper mapper = new ObjectMapper();
//            PlayerMovementRequest movement = new PlayerMovementRequest(playerMetaData.userId, direction);
//            Message message = new Message();
//            message.jsonContent = mapper.writeValueAsString(movement);
//            message.className = PlayerMovementRequest.class.getName();
//            String messageAsJson = mapper.writeValueAsString(message);
//            areaClient.write(messageAsJson);
//        } catch (JsonProcessingException | NullPointerException | IllegalArgumentException e) {
//            e.printStackTrace();
//        }
    }
//
//    public MultiPlayerAreaClient getAreaClient() {
//        return areaClient;
//    }

//    public void setMetaClient(MultiPlayerMetaClient metaClient) {
//        this.metaClient = metaClient;
//    }

    public void setMultiPlayerClient(MultiplayerClient multiPlayerClient) {
        this.multiplayerClient = multiPlayerClient;
    }
}
