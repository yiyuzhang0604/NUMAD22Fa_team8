package edu.northeastern.numad22fa_team8;

public class StickerMessage implements Comparable<StickerMessage> {
    private final String username;
    private final String friend;

    public StickerMessage(String name, String url) {
        this.username = name;
        this.friend = url;
    }

    public String getName() {
        return username;
    }

    public String getUrl() {
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
