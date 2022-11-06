package edu.northeastern.numad22fa_team8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StickerApp extends AppCompatActivity {

    private static String SERVER_KEY;
    DatabaseReference dbRef;
    Button btn_register, btn_send_sticker, btnHistory;
    EditText enterUserName, enterFriendName;
    private ImageView imageView1, imageView2, imageView3;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
    private Map<String, String> userNameToUserIdMap = new HashMap<>();
    private Map<String, String> userIdToUserNameMap = new HashMap<>();
    private ImageView selectedImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_app);
        dbRef = FirebaseDatabase.getInstance().getReference();
        btn_register = findViewById(R.id.buttonUserRegister);
        btn_send_sticker = findViewById(R.id.buttonSendSticker);
        btnHistory = findViewById(R.id.buttonHistory);
        enterUserName = findViewById(R.id.editTextEnterUserName);
        enterFriendName = findViewById(R.id.editTextEnterFriendName);
        // get server key from google-service.json
        SERVER_KEY = "key=AIzaSyCKl7WKMTFEpQHfjbAs6tZJr_X-EcH_Qik";
        userRegister();
        createNotificationChannel();
//        sendNotification();
        showStickersAvailable();

        btn_send_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFriendsUsernameInDatabase();
                if (!userNameToUserIdMap.containsKey(enterFriendName.getText().toString())) {
                    Toast.makeText(StickerApp.this, "Friend's name not exist!", Toast.LENGTH_SHORT).show();
                } else {
                    // send Image
                    Toast.makeText(StickerApp.this, "Friend's name exist!", Toast.LENGTH_SHORT).show();
                    // get Drawable Id
                    int selectedImageId = (int) selectedImage.getTag();
                    Date timestamp = new Date();
                    StickerMessage msg = new StickerMessage(enterUserName.getText().toString(), enterFriendName.getText().toString(), selectedImageId, timestamp);
                    // sendHistory
                    DatabaseReference sendHistory = dbRef.child("users").child(enterUserName.getText().toString()).child("sendHistory");
                    sendHistory.push().setValue(msg);
                    // receiveHistory
                    DatabaseReference receiveHistory = dbRef.child("users").child(enterFriendName.getText().toString()).child("receiveHistory");
                    receiveHistory.push().setValue(msg);
                    postToastMessage("Sent sticker from " + enterUserName.getText().toString() + " to " + enterFriendName.getText().toString() + ", stickerId: " + selectedImageId, getApplicationContext());
                }
            }
        });

        // click History Button to get send and receive history
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });
    }

    private void openHistory() {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    private void showStickersAvailable() {
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        // make it clickable
        imageView1.setClickable(true);
        imageView2.setClickable(true);
        imageView3.setClickable(true);
        // add onclick listener
        imageView1.setOnClickListener((view) -> imageOnClickListener(view));
        imageView2.setOnClickListener((view) -> imageOnClickListener(view));
        imageView3.setOnClickListener((view) -> imageOnClickListener(view));
        // works for sending sticker message and retrieving history
        imageView1.setTag(R.drawable.joey);
        imageView2.setTag(R.drawable.rick);
        imageView3.setTag(R.drawable.simpsons);
    }

    private void imageOnClickListener(View view) {
        if (selectedImage != null) selectedImage.setColorFilter(null);
        selectedImage = (ImageView) view;
        selectedImage.setColorFilter(ContextCompat
                            .getColor(this.getApplicationContext()
                                    , R.color.purple_200)
                    , android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void userRegister() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
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
                                dbRef.child("users").child(inputUserName).setValue(user);
                            }
                        });
                Toast.makeText(StickerApp.this, "You have successfully sign in!", Toast.LENGTH_SHORT).show();
                btn_register.setText(inputUserName);
            }
        });

    }

    private void validateFriendsUsernameInDatabase() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("users").get().addOnCompleteListener((task) -> {
            HashMap<String, HashMap<String, String>> tempMap = (HashMap) task.getResult().getValue();
            //System.out.println("usermap: " + tempMap.toString());
            //Toast.makeText(StickerApp.this, "map" + tempMap.toString(), Toast.LENGTH_SHORT).show();
            List<String> userNames = new ArrayList<>();
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
    }

//    public void sendNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Received New Message")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setContentText(enterUserName.getText().toString() + "send you a message");
//
//        createNotificationChannel();
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
//
//    }

    public void createNotificationChannel() {
        CharSequence name = CHANNEL_NAME;
        String description = CHANNEL_DESCRIPTION;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static void postToastMessage(final String message, final Context context){
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}