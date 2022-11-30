package edu.northeastern.numad22fa_team8.MeowFinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad22fa_team8.MeowFinder.model.PostDetail;
import edu.northeastern.numad22fa_team8.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    public static final String MeowFinderAppPosts = "MeowFinderAppPosts";

    private Button submitBtn;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText locationEditText;
    private EditText authorNameEditText;
    private EditText authorEmailEditText;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePostFragment newInstance(String param1, String param2) {
        CreatePostFragment fragment = new CreatePostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Initialize variables
    Button locationBtn;
    FusedLocationProviderClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        locationBtn = view.findViewById(R.id.bt_create_post_get_location);
        submitBtn = view.findViewById(R.id.bt_create_post_submit);
        titleEditText = (EditText) view.findViewById(R.id.new_post_title);
        descriptionEditText = (EditText) view.findViewById(R.id.new_post_description);
        locationEditText = (EditText) view.findViewById(R.id.new_post_location);
        authorNameEditText = (EditText) view.findViewById(R.id.new_post_author_name);
        authorEmailEditText = (EditText) view.findViewById(R.id.new_post_author_email);

        // Initialize location client
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        // register button callbacks
        registerGetCurrentLocationBtnCallback();
        registerSubmitBtnCallback();

        // Return view
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0)
                && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call method
            getCurrentLocation();
        }
        else {
            // When permission are denied
            // Display toast
            Toast.makeText(getActivity(),"Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Initialize Location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    // Initialize location
                    Location location = task.getResult();
                    // Check condition
                    if (location != null) {
                        // get city and postal code
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String city = addresses.get(0).getLocality();
                            String postalCode = addresses.get(0).getPostalCode();
                            locationEditText.setText(city + ", " + postalCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // TODO: When location result is null

                    }
                }
            });
        } else {
            // When location service is not enabled open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void registerGetCurrentLocationBtnCallback() {
        locationBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        // check condition
                        if (ContextCompat.checkSelfPermission(
                                getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            // When permission is granted
                            // Call method
                            getCurrentLocation();
                        }
                        else {
                            // When permission is not granted
                            // Call method
                            requestPermissions(
                                    new String[] {
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION },
                                    100);
                        }
                    }
            });
    }

    private void registerSubmitBtnCallback() {
        submitBtn.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String location = locationEditText.getText().toString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            String authorName = authorNameEditText.getText().toString();
            String authorEmail = authorEmailEditText.getText().toString();

            PostDetail postDetail = new PostDetail(title, description, location, time, authorName, authorEmail);

            if (title.isEmpty() || description.isEmpty() || location.isEmpty()) {
                Toast.makeText(getContext(), "Please provide more information. \n(Only Author info is optional.)", Toast.LENGTH_SHORT).show();
                return;
            }

            dbRef.child(MeowFinderAppPosts).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "it is not successful yet", Toast.LENGTH_SHORT).show();
                    return;
                }
                DataSnapshot posts = task.getResult();
                if (posts.exists() && posts.hasChild(title)) {
                    Toast.makeText(getActivity(), "This title already exists", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task1 -> {
                        if (!task1.isSuccessful()) {
                            Log.w("MeowFinderAppPosts", "Fetching FCM token failed", task1.getException());
                            return;
                        }
                        dbRef.child(MeowFinderAppPosts).child(title).setValue(postDetail);
                    });
                }

                Toast.makeText(getContext(), "Submitting post...", Toast.LENGTH_SHORT).show();
                dbRef.child(MeowFinderAppPosts).child(title).setValue(postDetail);
                Toast.makeText(getContext(), "You successfully created a new post!", Toast.LENGTH_SHORT).show();
            });
        });
    }

}