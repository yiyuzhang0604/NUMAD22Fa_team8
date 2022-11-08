package edu.northeastern.numad22fa_team8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class StickerApp extends AppCompatActivity {
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private Button btnRegister, btnSendSticker, btnSendHistory, btnReceiveHistory;
    private EditText enterSenderName, enterReceiverName;
    private ImageView imageView1, imageView2, imageView3;
    private ImageView selectedImage = null;
    private Map<Integer, Integer> imageIndex = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_app);
        btnRegister = findViewById(R.id.buttonUserRegister);
        btnSendSticker = findViewById(R.id.buttonSendSticker);
        btnSendHistory = findViewById(R.id.buttonSendHistory);
        btnReceiveHistory = findViewById(R.id.btnReceiveHistory);
        enterSenderName = findViewById(R.id.editTextEnterUserName);
        enterReceiverName = findViewById(R.id.editTextEnterFriendName);
        userRegister();
        createNotificationChannel();
        sendNotification();
        showStickersAvailable();

        registerSendBtnCallback();
        registerSendHistoryBtnCallback();
        registerReceiveHistoryBtnCallback();
    }

    private void registerReceiveHistoryBtnCallback() {
        btnReceiveHistory.setOnClickListener(view -> {
            Intent i = new Intent(StickerApp.this, ReceiveHistoryActivity.class);
            if (!enterSenderName.getText().toString().equals(""))
                i.putExtra("sender", enterSenderName.getText().toString());
            startActivity(i);
        });
    }

    private void registerSendHistoryBtnCallback() {
        btnSendHistory.setOnClickListener(view -> {
            Intent i = new Intent(StickerApp.this, SendHistoryActivity.class);
            if (!enterSenderName.getText().toString().equals("")) {
                i.putExtra("sender", enterSenderName.getText().toString());
            }
            startActivity(i);
        });
    }

    private void registerSendBtnCallback() {
        btnSendSticker.setOnClickListener(view -> {
            if (selectedImage == null) {
                Toast.makeText(StickerApp.this, "No image selected!", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedImageId = (int) selectedImage.getTag();

            String senderName = enterSenderName.getText().toString();
            String receiverName = enterReceiverName.getText().toString();
            if (senderName.equals(receiverName)) {
                Toast.makeText(StickerApp.this, "Can't send to yourself!", Toast.LENGTH_SHORT).show();
                return;
            }

            dbRef.child("users").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    return;
                }
                DataSnapshot users = task.getResult();
                if (!users.exists()) {
                    return;
                }
                if (!users.hasChild(receiverName)) {
                    Toast.makeText(StickerApp.this, "Friend's name doesn't exist!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!users.hasChild(senderName)) {
                    Toast.makeText(StickerApp.this, "Your name doesn't exist!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User sender = users.child(senderName).getValue(User.class);
                User receiver = users.child(receiverName).getValue(User.class);
                assert sender != null;
                assert receiver != null;

                Toast.makeText(StickerApp.this, "Sending sticker...", Toast.LENGTH_SHORT).show();

                // send history
                sender.incrementSentImage(imageIndex.get(selectedImageId));
                // receive history
                receiver.addReceiveHistory(senderName, selectedImageId);

                dbRef.child("users").child(senderName).setValue(sender);
                dbRef.child("users").child(receiverName).setValue(receiver);
                postToastMessage(
                        String.format(
                                Locale.getDefault(),
                                "Sent sticker \"%d\" from %s to %s",
                                selectedImageId, senderName, receiverName),
                        getApplicationContext());
            });
        });
    }

    private void showStickersAvailable() {
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.stickerImage);
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
        // record corresponding relationships between images and index
        imageIndex.put((int) imageView1.getTag(), 1);
        imageIndex.put((int) imageView2.getTag(), 2);
        imageIndex.put((int) imageView3.getTag(), 3);
    }

    private void imageOnClickListener(View view) {
        if (selectedImage != null) {
            selectedImage.setColorFilter(null);
        }
        selectedImage = (ImageView) view;
        selectedImage.setColorFilter(ContextCompat
                        .getColor(this.getApplicationContext()
                                , R.color.purple_200)
                , android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void userRegister() {
        btnRegister.setOnClickListener(view -> {
            dbRef = FirebaseDatabase.getInstance().getReference();
            String inputUserName = enterSenderName.getText().toString();
            String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            if (inputUserName.equals("")) {
                Toast.makeText(StickerApp.this, "Please enter a non-empty username", Toast.LENGTH_SHORT).show();
                return;
            }

            dbRef.child("users").child(inputUserName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // If user already exists, toast currently stored value.
                    if (dataSnapshot.exists()) {
                        Toast.makeText(StickerApp.this, "User already exists'" + inputUserName + "'", Toast.LENGTH_SHORT).show();
                    } else {
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
                    }

                    Toast.makeText(StickerApp.this, "You have successfully sign in!", Toast.LENGTH_SHORT).show();
                    btnRegister.setText(inputUserName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //TODO: Handle this
                }
            });
        });

    }

    public void sendNotification() {
        Intent intent = new Intent(this, ReceiveHistoryActivity.class);

        intent.putExtra("sender", enterSenderName.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Received New Message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText(enterSenderName.getText().toString() + "send you a message");

        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

    }

    public void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void postToastMessage(final String message, final Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}