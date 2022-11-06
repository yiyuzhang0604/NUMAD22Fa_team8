package edu.northeastern.numad22fa_team8;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class A7 extends AppCompatActivity {

    private EditText catName, zipcode, descrption;
    private Button postButton;
    private ProgressBar progressBar;
    private RecyclerView responseView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostModel> postCardList;
    private PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a7);
        catName = findViewById(R.id.cat_name);
        zipcode = findViewById(R.id.zipcode);
        descrption = findViewById(R.id.description);
        progressBar = findViewById(R.id.progressBar);
        postButton = findViewById(R.id.button2);
        progressBar.setVisibility(View.INVISIBLE);
        postCardList = new ArrayList<>();

        createResponseView();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catName.getText().toString().isEmpty() || zipcode.getText().toString().isEmpty()
                        || descrption.getText().toString().isEmpty()) {
                    Toast.makeText(A7.this, "Please enter all the information", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(catName.getText().toString(), zipcode.getText().toString(), descrption.getText().toString());

            }
        });
        fetchPost();
    }

    private void createResponseView() {
        // create recycler view
        layoutManager = new LinearLayoutManager(this);
        responseView = findViewById(R.id.response_view);
//        responseView.setHasFixedSize(true);
        postAdapter = new PostAdapter(postCardList);
        responseView.setAdapter(postAdapter);
        responseView.setLayoutManager(layoutManager);
    }

    private void fetchPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        retrofitAPI.getPostList().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postCardList.addAll(response.body());
                    postAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(A7.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void postData(String catName, String zipcode, String description) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        PostModel model = new PostModel(catName, zipcode, description);
        Call<PostModel> call = retrofitAPI.createPost(model);
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> apiresponse) {
                progressBar.setVisibility(View.GONE);
                PostModel responseFromAPI = apiresponse.body();
                postCardList.add(responseFromAPI);
                postAdapter = new PostAdapter(postCardList);
                responseView.setAdapter(postAdapter);
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Toast.makeText(A7.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

}

    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Are you sure you want to leave the app?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                finish();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    }
}