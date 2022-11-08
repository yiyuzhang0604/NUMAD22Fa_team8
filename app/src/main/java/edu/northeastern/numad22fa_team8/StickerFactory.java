package edu.northeastern.numad22fa_team8;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StickerFactory {
    private static StickerFactory INSTANCE = new StickerFactory();
    private final List<StickerMessage> stickerMessages = new ArrayList<>();
//    private StickerAdapter adapter = null;

    protected StickerFactory() {
    }

    public static StickerFactory getInstance() {
        return INSTANCE;
    }

//    public void setAdapter(final StickerAdapter adapter) {
//        this.adapter = adapter;
//    }

    public StickerMessage get(int index) {
        return stickerMessages.get(index);
    }
}
