package edu.northeastern.numad22fa_team8;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    public String username;
    public String deviceId;
    public String token;
    public Map<String, Integer> sendHistory = new HashMap<>(); // stickerId -> count
    public List<StickerMessage> receiveHistory = new ArrayList<>();

    public User() {}

    public Map<String, Integer> getSendHistory() {
        return sendHistory;
    }

    public void setSendHistory(Map<String, Integer> sendHistory) {
        this.sendHistory = sendHistory;
    }

    public User(String username, String deviceId, String token) {
        this.username = username;
        this.deviceId = deviceId;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<StickerMessage> getReceiveHistory() {
        return receiveHistory;
    }

    public void setReceiveHistory(List<StickerMessage> receiveHistory) {
        this.receiveHistory = receiveHistory;
    }

    public void incrementSentImage(Integer imageId) {
        String key = imageId.toString();
        sendHistory.put(key, sendHistory.getOrDefault(key, 0) + 1);
    }

    public void addReceiveHistory(String from, int imageId) {
        receiveHistory.add(new StickerMessage(from, this.username, imageId, new Date()));
    }
}
