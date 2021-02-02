package com.github.martinfrank.multiplayerclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.martinfrank.multiplayerprotocol.area.MapChanges;
import com.github.martinfrank.multiplayerprotocol.area.Message;
import com.github.martinfrank.multiplayerprotocol.area.PlayerRegistration;
import org.junit.Test;

public class JsonTest {

    @Test
    public void doIt(){
        String msJson = "{\"monsterMovements\":[{\"entityId\":\"06fe5fb4-f42d-40bf-906d-0e6b6232f9ae\",\"from\":{\"x\":14,\"y\":16},\"to\":{\"x\":15,\"y\":15}}]}";
        try {
            MapChanges mc = (MapChanges)((new ObjectMapper()).readValue(msJson, Class.forName("com.github.martinfrank.multiplayerprotocol.area.MapChanges")));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("MapChanges class: "+MapChanges.class.getName());
    }

    @Test
    public void doItAgain()  {

    }
}
