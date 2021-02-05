package com.github.martinfrank.multiplayerclient.client;

import com.github.martinfrank.multiplayerclient.model.AreaModel;
import com.github.martinfrank.multiplayerprotocol.area.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwtMessageParser extends BaseMessageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwtMessageParser.class);

    private final AreaModel model;

    public SwtMessageParser(AreaModel model) {
        super();
        this.model = model;
    }

    @Override
    public void handleAreaTotal(AreaTotal areaTotal) {
        super.handleAreaTotal(areaTotal);
        LOGGER.debug("handle AreaTotal: {}", areaTotal);
    }

    //FIXME wird nicht gebraucht
    @Override
    public void handlePlayerMovementRequest(PlayerMovementRequest playerMovementRequest) {
        super.handlePlayerMovementRequest(playerMovementRequest);
        LOGGER.debug("handle PlayerMovementRequest: {}", playerMovementRequest);
    }

    //FIXME wird nicht gebraucht
    @Override
    public void handlePlayerRegistration(PlayerRegistration playerRegistration) {
        super.handlePlayerRegistration(playerRegistration);
        LOGGER.debug("handle PlayerRegistration: {}", playerRegistration);
    }

    @Override
    public void handleMapChanges(MapChanges mapChanges) {
        super.handleMapChanges(mapChanges);
        LOGGER.debug("handle MapChanges: {}", mapChanges);
    }

    @Override
    public void handleMonsters(Monsters monsters) {
        super.handleMonsters(monsters);
        LOGGER.debug("handle Monsters: {}", monsters);
    }

    @Override
    public void handlePlayer(Player player) {
        super.handlePlayer(player);
        LOGGER.debug("handle Player: {}", player);
    }


}
