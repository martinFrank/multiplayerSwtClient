package com.github.martinfrank.multiplayerclient.view;

import javafx.scene.canvas.Canvas;
import org.mapeditor.core.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MapCanvas extends Canvas {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MapCanvas.class);

    private Map map;

    public MapCanvas() {
        super();
    }


    public void setMap(Map map) {
        this.map = map;

    }
}
