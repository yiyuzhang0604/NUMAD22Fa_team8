package edu.northeastern.numad22fa_team8;


public class Sticker {
    public String imageId;
    public String fromUser;
    public String toUser;

    public Sticker(String imageId, String fromUser, String toUser) {
        this.imageId = imageId;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public String getImageId() {
        return imageId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }
}

