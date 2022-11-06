package edu.northeastern.numad22fa_team8;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public String deviceId;
    public String token;
    public List<StickerMessage> sendHistory;
    public List<StickerMessage> receiveHistory;

    public User(String username, String deviceId, String token) {
        this.username = username;
        this.deviceId = deviceId;
        this.token = token;
        this.sendHistory = new ArrayList<StickerMessage>();
        this.receiveHistory = new ArrayList<StickerMessage>();
    }
}
