package com.github.martinfrank.multiplayerclient.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.martinfrank.multiplayerclient.client.MultiPlayerAreaClient;
import com.github.martinfrank.multiplayerclient.client.MultiPlayerMetaClient;
import com.github.martinfrank.multiplayerclient.map.MapProvider;
import com.github.martinfrank.multiplayerclient.model.AreaModel;
import com.github.martinfrank.multiplayerclient.view.MapCanvas;
import com.github.martinfrank.multiplayerprotocol.area.Direction;
import com.github.martinfrank.multiplayerprotocol.area.Message;
import com.github.martinfrank.multiplayerprotocol.area.PlayerMovementRequest;
import com.github.martinfrank.multiplayerprotocol.area.PlayerRegistration;
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
import org.tiledreader.TiledMap;


public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String STEERING_PREFIX = "steering";
    private static final String CONNECT_BUTTON_ID = "connect";
    private MultiPlayerAreaClient areaClient;
    private MultiPlayerMetaClient metaClient;

    private PlayerMetaData playerMetaData;

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

    private Button connectButton;


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
        connectButton = (Button) connection.getChildren().stream()
                .filter(c -> CONNECT_BUTTON_ID.equals(c.getId())).findAny().orElse(null);
        if (connectButton != null) {
            connectButton.setOnAction(event -> connect());
        }
    }

    private void connect() {
        //FIXME make a connector that handles the logonConnect
        playerMetaData = metaClient.getPlayerData("Mosh", "swordFish");
        String areaId = playerMetaData.playerAreaId;
        LOGGER.debug("connect, Player Meta Data: {}", playerMetaData);
        try {
            TiledMap map = new MapProvider(metaClient).getMap();
            AreaModel model = new AreaModel(map);
            mapCanvas.setAreaModel(model);

            areaClient = new MultiPlayerAreaClient("192.168.0.69", 10523, model);
            new Thread(areaClient).start();
            PlayerRegistration playerRegistration = new PlayerRegistration(playerMetaData.userId);
            areaClient.register(playerRegistration);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void steer(String dirString) {
        try {
            Direction direction = Direction.valueOf(dirString);
            ObjectMapper mapper = new ObjectMapper();
            PlayerMovementRequest movement = new PlayerMovementRequest(playerMetaData.userId, direction);
            Message message = new Message();
            message.jsonContent = mapper.writeValueAsString(movement);
            message.className = PlayerMovementRequest.class.getName();
            String messageAsJson = mapper.writeValueAsString(message);
            areaClient.write(messageAsJson);
        } catch (JsonProcessingException | NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public MultiPlayerAreaClient getAreaClient() {
        return areaClient;
    }

    public void setMetaClient(MultiPlayerMetaClient metaClient) {
        this.metaClient = metaClient;
    }
}
