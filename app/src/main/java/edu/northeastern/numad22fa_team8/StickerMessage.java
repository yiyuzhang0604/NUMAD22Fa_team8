package edu.northeastern.numad22fa_team8;

import java.util.Date;

public class StickerMessage implements Comparable<StickerMessage> {
    private String sender;
    private String receiver;
    private int stickerId;
    private Date timestamp;

    public StickerMessage() {}

    public StickerMessage(String sender, String receiver, int stickerId, Date timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.stickerId = stickerId;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getStickerId() {
        return stickerId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStickerId(int stickerId) {
        this.stickerId = stickerId;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(StickerMessage stickerMessage) {
        return timestamp.compareTo(stickerMessage.timestamp);
    }
}
