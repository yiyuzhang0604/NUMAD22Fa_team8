package edu.northeastern.numad22fa_team8.MeowFinder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import edu.northeastern.numad22fa_team8.MeowFinder.model.PostDetail;
import edu.northeastern.numad22fa_team8.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    public static final String MeowFinderAppPosts = "MeowFinderAppPosts";
    private CatAdapter catAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<PostDetail> options =
                new FirebaseRecyclerOptions.Builder<PostDetail>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(MeowFinderAppPosts), PostDetail.class)
                        .build();

        catAdapter = new CatAdapter(options);
        recyclerView.setAdapter(catAdapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        catAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        catAdapter.stopListening();
    }
}