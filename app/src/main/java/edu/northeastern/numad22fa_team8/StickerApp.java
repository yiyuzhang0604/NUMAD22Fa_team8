package edu.northeastern.numad22fa_team8;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;



public class StickerApp extends AppCompatActivity {
    DatabaseReference dbRef;
    Button btn_register;
    EditText enterUserName;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_app);
        dbRef = FirebaseDatabase.getInstance().getReference();
        btn_register = findViewById(R.id.buttonUserRegister);
        enterUserName = findViewById(R.id.editTextEnterUserName);
        mAuth = FirebaseAuth.getInstance();

        dbRef.setValue("here there").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Succesfully connected to Database", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUserName = enterUserName.getText().toString();
                String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                if (inputUserName == null) {
                    Toast.makeText(StickerApp.this, "Please enter a non-empty username", Toast.LENGTH_SHORT).show();
                    return;
                }
                // retrieve current token
                // Reference: https://firebase.google.com/docs/cloud-messaging/android/client
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("StickerApp", "Fetching FCM token failed", task.getException());
                                    return;
                                }
                                // Get new FCM registration token
                                String token = task.getResult();
                                // create user
                                User user = new User(inputUserName, device_id, token);
                                // add to db
                                dbRef.child("users").child(device_id).setValue(user);
                            }
                        });
                Toast.makeText(StickerApp.this, "You have successfully sign in!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}