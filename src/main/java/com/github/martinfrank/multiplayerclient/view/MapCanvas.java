package com.github.martinfrank.multiplayerclient.view;

import com.github.martinfrank.multiplayerclient.model.AreaModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MapCanvas extends Canvas {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapCanvas.class);

    private AreaModel areaModel;

    private Image background;

    public MapCanvas() {
        super();
    }


    public void setAreaModel(AreaModel areaModel) {
        this.areaModel = areaModel;
        try {
            String imagePath = areaModel.getBackgroundFilename();//
            LOGGER.debug("imagePath: {}", imagePath);
            FileInputStream inputStream = new FileInputStream(imagePath);
            background = new Image(inputStream);
            LOGGER.debug("image: {}", background);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        render();
    }

    public void render() {
        getGraphicsContext2D().drawImage(background, 0, 0);
    }
}
