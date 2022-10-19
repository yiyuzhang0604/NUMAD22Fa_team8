package edu.northeastern.numad22fa_team8;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class A7 extends AppCompatActivity {

    private EditText catName, zipcode, descrption;
    private Button postButton;
    private ProgressBar progressBar;
    private TextView response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a7);
        catName = findViewById(R.id.idEdtName);
        zipcode = findViewById(R.id.idEditZipcode);
        descrption = findViewById(R.id.idEdtDescription);
        progressBar = findViewById(R.id.idLoadingPB);
        response = findViewById(R.id.idTVResponse);
        postButton = findViewById(R.id.idBtnPost);

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

        // passing data from our text fields to our modal class.
        PostModel modal = new PostModel(catName, zipcode, description);

        // calling a method to create a post and passing our modal class.
        Call<PostModel> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> apiresponse) {
                // this method is called when we get response from our api.
                Toast.makeText(A7.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                progressBar.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                //jobEdt.setText("");
                //nameEdt.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                PostModel responseFromAPI = apiresponse.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + apiresponse.code() + "\nName : " + responseFromAPI.getCatName() + "\n" + "Zipcode : " + responseFromAPI.getZipcode() + "\n" + "Description : " + responseFromAPI.getDescription() ;

                // below line we are setting our
                // string to our text view.
                response.setText(responseString);
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                response.setText("Error found is : " + t.getMessage());
            }
        });

}
}