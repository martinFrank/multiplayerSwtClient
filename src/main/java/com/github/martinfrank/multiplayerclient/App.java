package com.github.martinfrank.multiplayerclient;

import com.github.martinfrank.multiplayerclient.control.Controller;
import com.github.martinfrank.multiplayerclient.control.ControllerFactory;
import com.github.martinfrank.multiplayerclient.res.ResourceManager;
import com.github.martinfrank.multiplayerclient.client.MultiPlayerAreaClient;
import com.github.martinfrank.multiplayerclient.client.MultiPlayerMetaClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private Pane root;

    private  Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        try{
            Class clazz = Class.forName("com.github.martinfrank.multiplayerprotocol.area.MapChanges");
            LOGGER.debug("clazz: {}", clazz);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
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

        //
//        try{
        //FIXME: statt des Clients  wird nur die Config übermittelt
            controller.setMetaClient(new MultiPlayerMetaClient("192.168.0.69", 8080));
//            areaClient = new MultiPlayerAreaClient("192.168.0.69", 10523);
//            controller.setAreaClient(areaClient);
//            new Thread(areaClient).start();
//        }catch (IOException e){
//            LOGGER.debug("error", e);
//        }

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
        if(controller.getAreaClient() != null) {
            controller.getAreaClient().stop();
        }
        super.stop();
    }
}
