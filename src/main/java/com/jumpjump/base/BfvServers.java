package com.jumpjump.base;

import lombok.Data;

@Data
public class BfvServers {
    private String prefix;
    private String description;
    private int playerAmount;
    private int maxPlayers;
    private int inQue;
    private String serverInfo;
    private String url;
    private String mode;
    private String currentMap;

    private String region;

    private String ownerId;

    private String smallMode;

    private String teams;

    private String official;

    private String gameId;
}
