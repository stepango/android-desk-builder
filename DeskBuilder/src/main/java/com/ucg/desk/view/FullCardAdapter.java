package com.ucg.desk.view;

import android.content.Context;

import com.ucg.desk.util.CardUtils;

/**
 * Created by sone on 18.02.14.
 */
public class FullCardAdapter extends CardAdapter {
    private final String mPlayerClass;

    public FullCardAdapter(final Context context, final String playerClass) {
        super(context);
        mPlayerClass = playerClass;
        setCards(CardUtils.getCards(context, CardUtils.MAIN_TYPE, playerClass));
    }
}
