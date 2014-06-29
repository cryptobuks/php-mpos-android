package com.kosherbacon.mmcfe_ng;

public class ServerItem {
    public String serverURL, apiKey, userID;
    public boolean lastItem;
    public ServerItem(String serverURL, String apiKey, String userID, boolean lastItem) {
        this.serverURL = serverURL;
        this.apiKey = apiKey;
        this.userID = userID;
        this.lastItem = lastItem;
    }
}