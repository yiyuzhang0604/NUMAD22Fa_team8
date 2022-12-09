package edu.northeastern.numad22fa_team8.MeowFinder.search;

import java.util.List;

import edu.northeastern.numad22fa_team8.MeowFinder.model.PostDetail;

public class SearchResultBroker {
    private static final SearchResultBroker INSTANCE = new SearchResultBroker();
    protected SearchResultBroker() {
    }
    public static SearchResultBroker getInstance() {
        return INSTANCE;
    }

    private List<PostDetail> results = List.of();

    public PostDetail getResult(int position) {
        return results.get(position);
    }

    public void setResults(List<PostDetail> toStore) {
        results = toStore;
    }

    public int getSize() {
        return results.size();
    }
}
