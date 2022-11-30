package edu.northeastern.numad22fa_team8.MeowFinder.model;

import java.sql.Timestamp;

public class PostDetail {
    private String title;
    private String description;
    private String location;
    private String timestamp;
    private PostStatus status;
    private String authorName;
    private String authorEmail;

    public PostDetail(String title, String description, String location, String timestamp, String authorName, String authorEmail) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
        this.status = PostStatus.Open;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}
