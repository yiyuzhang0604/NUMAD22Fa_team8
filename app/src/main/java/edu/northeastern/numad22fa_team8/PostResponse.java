package edu.northeastern.numad22fa_team8;

public class PostResponse {
    private PostModel postModel;
    private String responseCode;

    public PostResponse(PostModel postModel, String responseCode) {
        this.postModel = postModel;
        this.responseCode = responseCode;
    }

    public PostModel getPostModel() {
        return postModel;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
