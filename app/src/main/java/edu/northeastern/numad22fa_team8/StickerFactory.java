package edu.northeastern.numad22fa_team8;

import java.util.ArrayList;
import java.util.Date;
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

//    public boolean insert(String sender, String receiver) {
//        StickerMessage stickerMessage = new StickerMessage(sender, receiver, new Date(), );
//        if (stickerMessages.contains(stickerMessage)) {
//            return false;
//        }
//        stickerMessages.add(stickerMessage);
//        this.adapter.notifyItemInserted(stickerMessages.size() - 1);
//        return true;
//    }

    public StickerMessage get(int index) {
        return stickerMessages.get(index);
    }

    public int size() {
        return stickerMessages.size();
    }
}
