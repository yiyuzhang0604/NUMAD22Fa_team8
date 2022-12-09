package edu.northeastern.numad22fa_team8.MeowFinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.northeastern.numad22fa_team8.MeowFinder.model.PostDetail;
import edu.northeastern.numad22fa_team8.MeowFinder.search.SearchResultBroker;
import edu.northeastern.numad22fa_team8.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPostsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MEOW_FINDER = "MeowFinderAppPosts";
    private static final GenericTypeIndicator<Map<String, PostDetail>> POSTS_TYPE = new GenericTypeIndicator<>() {
    };
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private final SearchResultBroker broker = SearchResultBroker.getInstance();
    Button searchBn;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchPostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchPostsFragment newInstance(String param1, String param2) {
        SearchPostsFragment fragment = new SearchPostsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_posts, container, false);
        List<EditText> filterInputs = List.of(
                view.findViewById(R.id.search_owner_name),
                view.findViewById(R.id.search_owner_email),
                view.findViewById(R.id.search_location),
                view.findViewById(R.id.search_title)
        );

        searchBn = view.findViewById(R.id.bt_search);
        searchBn.setOnClickListener(view1 -> {
            dbRef.child(MEOW_FINDER).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Search task failed...Try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                DataSnapshot snapshot = task.getResult();
                Map<String, PostDetail> posts = snapshot.getValue(POSTS_TYPE);

                List<String> filterGroup = new ArrayList<>();
                for (EditText filterInput : filterInputs) {
                    String filter = filterInput.getText().toString();
                    filterGroup.add(filter.isEmpty() ? ".*" : filter);
                }

                List<PostDetail> results = posts.values().stream().filter(
                        postDetail -> postDetail.matches(filterGroup)).collect(Collectors.toList());

                broker.setResults(results);
                Toast.makeText(getActivity(), "Finish searching.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this.getActivity(), SearchResult.class);
                startActivity(intent);
            });
        });

        return view;
    }
}