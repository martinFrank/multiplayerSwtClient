package com.github.martinfrank.multiplayerSwtClient.control;

import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private Controller rootController;

    public ControllerFactory() {
    }

    @Override
    public Object call(Class<?> type) {
        if (type == Controller.class) {
            rootController = new Controller();
            return rootController;
        } else {
            // default behavior for controllerFactory:
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc); // fatal, just bail...
            }
        }
    }

    public Controller getRootController() {
        return rootController;
    }


}
