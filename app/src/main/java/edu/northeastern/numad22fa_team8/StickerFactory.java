package edu.northeastern.numad22fa_team8;

import java.util.ArrayList;
import java.util.List;

public class StickerFactory {
    private static StickerFactory INSTANCE = new StickerFactory();
    private final List<StickerMessage> stickerMessages = new ArrayList<>();
    private StickerAdapter adapter = null;

    protected StickerFactory() {
    }

    public static StickerFactory getInstance() {
        return INSTANCE;
    }

    public void setAdapter(final StickerAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean insert(String name, String url) {
        StickerMessage stickerMessage = new StickerMessage(name, url);
        if (stickerMessages.contains(stickerMessage)) {
            return false;
        }
        stickerMessages.add(stickerMessage);
        this.adapter.notifyItemInserted(stickerMessages.size() - 1);
        return true;
    }

    public void onTop(String url) {
        for (int i = 0; i < stickerMessages.size(); i++) {
            if (stickerMessages.get(i).getUrl().equals(url)) {
                StickerMessage top = stickerMessages.get(i);
                stickerMessages.set(i, stickerMessages.get(0));
                stickerMessages.set(0, top);
                this.adapter.notifyItemMoved(i, 0);
            }
        }
    }

    public StickerMessage get(int index) {
        return stickerMessages.get(index);
    }

    public int size() {
        return stickerMessages.size();
    }
}
