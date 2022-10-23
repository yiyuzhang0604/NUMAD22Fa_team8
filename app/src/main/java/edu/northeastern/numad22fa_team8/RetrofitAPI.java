package edu.northeastern.numad22fa_team8;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("users")
        //on below line we are creating a method to post our data.
    Call<PostModel> createPost(@Body PostModel postModel);

    @GET("/posts")
    Call<List<PostModel>> getPostList();

}
