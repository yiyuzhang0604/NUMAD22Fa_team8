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

    private List<PostDetail> results;

    public List<PostDetail> getResults() {
        return results;
    }

    public void setResults(List<PostDetail> toStore) {
        results = toStore;
    }
}
