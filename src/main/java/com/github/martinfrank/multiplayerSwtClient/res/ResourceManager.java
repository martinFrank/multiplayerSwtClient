package com.github.martinfrank.multiplayerSwtClient.res;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceManager {

    private final ClassLoader classLoader;

    public ResourceManager(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public URL getGuiRoot() throws MalformedURLException {
        return resourceURL("gui/root.fxml");
    }

    private URL resourceURL(String str) throws MalformedURLException {
        URL url = classLoader.getResource(str);
        if (url != null) {
            return new File(url.getPath()).toURI().toURL();
        }
        throw new MalformedURLException("url=null");
    }
}
