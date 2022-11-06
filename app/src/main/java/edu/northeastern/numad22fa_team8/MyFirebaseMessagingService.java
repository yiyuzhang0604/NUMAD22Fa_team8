package edu.northeastern.numad22fa_team8;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.core.Context;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String username;
    private String deviceId;
    private DatabaseReference dbRef;


    @Override
    public void onCreate() {
        super.onCreate();
        dbRef = FirebaseDatabase.getInstance().getReference();
        deviceId = Settings.Secure.getString(
                getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
