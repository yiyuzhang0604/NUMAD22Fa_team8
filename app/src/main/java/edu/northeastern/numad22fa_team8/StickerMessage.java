package edu.northeastern.numad22fa_team8;

public class StickerMessage implements Comparable<StickerMessage> {
    private final String username;
    private final String friend;

    public StickerMessage(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }

    public String getUsername() {
        return username;
    }

    public String getFriend() {
        return friend;
    }

    @Override
    public int compareTo(StickerMessage stickerMessage) {
        if (username.compareTo(stickerMessage.username) == 0) {
            return friend.compareTo(stickerMessage.friend);
        }
        return username.compareTo(stickerMessage.username);
    }
}
