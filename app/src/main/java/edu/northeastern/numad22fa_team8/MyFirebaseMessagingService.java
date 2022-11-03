package edu.northeastern.numad22fa_team8;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.database.FirebaseDatabase;
import android.provider.Settings;



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


}
