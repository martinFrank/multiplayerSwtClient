package com.github.martinfrank.multiplayerSwtClient;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:ClientConfig.properties"})
public interface Configuration extends Config {

    @Key("meta.server.address")
    String metaServerAddress();

    @Key("meta.server.port")
    int metaServerPort();

}
