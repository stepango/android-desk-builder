package com.ucg.desk.util;

import android.content.Context;

import com.ucg.desk.Card;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sone on 25.02.14.
 */
public class CardUtils {

    public static final String MAIN_TYPE = "main";

    public static final String FILE_ANDROID_ASSET = "file:///android_asset/";

    private CardUtils() {
    }

    public static ArrayList<Card> getCards(final Context context, String... types){
        ArrayList<Card> cards = new ArrayList<Card>();
        for (String type : types) {
            try {
                final String[] files = context.getAssets().list(type);
                for (String file : files) {
                    cards.add(new Card(type, file));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cards;
    }

}
