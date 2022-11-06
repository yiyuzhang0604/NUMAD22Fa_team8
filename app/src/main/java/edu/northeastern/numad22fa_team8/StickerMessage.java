package edu.northeastern.numad22fa_team8;

import java.util.Date;

public class StickerMessage implements Comparable<StickerMessage> {
    private final String sender;
    private final String receiver;
    private final int stickerId;
    private final Date timestamp;

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

    @Override
    public int compareTo(StickerMessage stickerMessage) {
        return timestamp.compareTo(stickerMessage.timestamp);
    }
}
