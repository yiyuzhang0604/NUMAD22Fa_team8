package edu.northeastern.numad22fa_team8;

import java.util.Date;

public class StickerMessage implements Comparable<StickerMessage> {
    public String sender;
    public String receiver;
    public Integer stickerId;
    public Date timestamp;

    public StickerMessage() {
    }

    public StickerMessage(String sender, String receiver, int stickerId, Date timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.stickerId = stickerId;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getStickerId() {
        return stickerId;
    }

    public void setStickerId(int stickerId) {
        this.stickerId = stickerId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(StickerMessage stickerMessage) {
        return timestamp.compareTo(stickerMessage.timestamp);
    }
}
