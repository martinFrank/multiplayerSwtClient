package com.github.martinfrank.multiplayerclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.martinfrank.multiplayerprotocol.meta.PlayerCredentials;
import com.github.martinfrank.multiplayerprotocol.meta.PlayerMetaData;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MultiPlayerMetaClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPlayerMetaClient.class);

    public static final String MAP_DOWNLOAD_FILE = "C:\\Users\\fmar\\IdeaProjects\\multiplayerSwtClient\\maps\\download.zip";

    private final String server;
    private final int port;


    public MultiPlayerMetaClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public PlayerMetaData getPlayerData(String user) {
        PlayerCredentials playerCredentials = new PlayerCredentials("Mosh", "snakeitsasnake");
        ObjectMapper mapper = new ObjectMapper();
        String logonAsString = "";
        try {
            logonAsString = mapper.writeValueAsString(playerCredentials);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response2 = Unirest.post("http://" + server + ":" + port + "/metadata/player")
//                .queryString("userName", "Mosh")
//                .queryString("userPass", "snakeitsasnake")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body(playerCredentials)
                .asJson();

//                .queryString("apiKey", "123")
//                .field("parameter", "value")
//                .field("firstname", "Gary")
//                .asJson();

//        String jsonString = "";
//        if(response2.getBody().isPresent()){
//            response2.asJson()
//            LOGGER.debug("respose: {}", jsonString);
//            jsonString = response2.asJson();
//        }



//        HttpResponse<JsonNode> response = Unirest.get("http://" + server + ":" + port + "/playerMetaData/player")
//                .queryString("userName", "Mosh")
//                .queryString("userPass", "snakeitsasnake")
//                .header("accept", "application/json")
//
////                .queryString("apiKey", "123")
////                .field("parameter", "value")
////                .field("firstname", "Gary")
//                .asJson();
//
        String jsonString = response2.getBody().toString();
        LOGGER.debug("respose: {}", jsonString);
//
        PlayerMetaData playerMetaData = null;
        try {
            ObjectMapper mapper2 = new ObjectMapper();
            playerMetaData = mapper2.readValue(jsonString, PlayerMetaData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return playerMetaData;
    }

    public File downloadMap(String fileName, String mapId) {
        return Unirest.get("http://" + server + ":" + port + "/mapdata/download")
                .header("accept", "application/octet-stream")
                .queryString("mapid", mapId)
                .asFile(fileName).getBody();
    }

//    public AreaDescription getAreaDescription(String areaId) {
//        ObjectMapper mapper = new ObjectMapper();//FIXME make this class member
//
//        //FIXME: proper path!
//        HttpResponse<JsonNode> response = Unirest.get("http://" + server + ":" + port + "/playerMetaData")
//                .header("accept", "application/json")
//                .asJson();
//        try {
//            return mapper.readValue(response.getBody().toString(), AreaDescription.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
