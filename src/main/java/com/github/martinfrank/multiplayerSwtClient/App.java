package com.github.martinfrank.multiplayerSwtClient;

import com.github.martinfrank.multiplayerSwtClient.control.Controller;
import com.github.martinfrank.multiplayerSwtClient.control.ControllerFactory;
import com.github.martinfrank.multiplayerSwtClient.res.ResourceManager;
import com.github.martinfrank.multiplayerclient.MultiplayerClient;
import com.github.martinfrank.multiplayerclient.MultiplayerClientConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private Pane root;

    private Controller controller;
    private MultiplayerClient multiPlayerClient;

    public static void main(String[] args) {
        launch(args);
    }

    //FIXME handle Exception from MultiplayerClient properly
    @Override
    public void init() throws Exception {
        Configuration config = ConfigFactory.create(Configuration.class);
        ResourceManager resourceManager = new ResourceManager(getClass().getClassLoader());
        ControllerFactory controllerFactory = new ControllerFactory();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resourceManager.getGuiRoot());
            fxmlLoader.setControllerFactory(controllerFactory);
            root = fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.debug("error", e);
        }
        controller = controllerFactory.getRootController();
        controller.init();

        //FIXME make MultiplayerConfig an interface and implement it per derivate client
        MultiplayerClientConfig clientConfig = new MultiplayerClientConfig(config.metaServerAddress(), config.metaServerPort(), "Mosh", "swordFish");
        multiPlayerClient = new MultiplayerClient(clientConfig);
        controller.setMultiPlayerClient(multiPlayerClient);


    }

    @Override
    public void start(Stage stage) {
        if (root != null) {
            stage.setScene(new Scene(root));
            stage.setTitle("tbd: set title");
            stage.show();
        } else {
            LOGGER.debug("error during init");
            Platform.exit();
            System.exit(0);
        }
    }

    @Override
    public void stop() throws Exception {
        if (multiPlayerClient.getAreaClient() != null) {
            multiPlayerClient.getAreaClient().stop();
        }
        super.stop();
    }
}
