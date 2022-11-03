package edu.northeastern.numad22fa_team8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StickerApp extends AppCompatActivity {

    private static String SERVER_KEY;
    DatabaseReference dbRef;
    Button btn_register, btn_send_sticker;
    EditText enterUserName, enterFriendName;
    private FirebaseAuth mAuth;
    private ImageView imageView1, imageView2, imageView3;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
    private Map<String, String> userNameToUserIdMap = new HashMap<>();
    private Map<String, String> userIdToUserNameMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_app);
        dbRef = FirebaseDatabase.getInstance().getReference();
        btn_register = findViewById(R.id.buttonUserRegister);
        btn_send_sticker = findViewById(R.id.buttonSendSticker);
        enterUserName = findViewById(R.id.editTextEnterUserName);
        enterFriendName = findViewById(R.id.editTextEnterFriendName);
        mAuth = FirebaseAuth.getInstance();
        // get server key from google-service.json
        SERVER_KEY = "key=AIzaSyCKl7WKMTFEpQHfjbAs6tZJr_X-EcH_Qik";
        createNotificationChannel();
        dbRef.child("users").get().addOnCompleteListener((task) -> {
            HashMap<String, HashMap<String, String>> tempMap = (HashMap) task.getResult().getValue();
            List<String> userNames = new ArrayList<>();

            // populate user id and name
            for (String userId : tempMap.keySet()) {
                String userName = tempMap.get(userId).get("username");
                if (userName == null || userName.equals(enterUserName.toString())) {
                    continue;
                }
                userNames.add(userName);
                userIdToUserNameMap.put(userId, userName);
                userNameToUserIdMap.put(userName, userId);
            }
        });



        dbRef.setValue("here there").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Succesfully connected to Database", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Fail to connect to Database", Toast.LENGTH_SHORT).show();

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
                btn_register.setText(inputUserName);
            }
        });

        btn_send_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userNameToUserIdMap.containsKey(enterFriendName.toString())) {
                    Toast.makeText(StickerApp.this, "Friend's name not exist!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StickerApp.this, "Friend's name exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createNotificationChannel() {
        CharSequence name = CHANNEL_NAME;
        String description = CHANNEL_DESCRIPTION;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}