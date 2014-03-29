package com.ucg.desk.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ucg.desk.Card;
import com.ucg.desk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sone on 13.02.14.
 */
public class CardAdapter extends BaseAdapter {

    private Context mContext;
    private List<Card> mCards = new ArrayList<>();

    public CardAdapter(Context context) {
        super();
        mContext = context;
    }

    public List<Card> getCards() {
        return mCards;
    }

    public void setCards(final List<Card> cards) {
        mCards = cards;
    }

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Card getItem(int i) {
        return mCards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mCards.get(position).getUri())
                .placeholder(R.drawable.list_stub)
                .into(imageView);
        return imageView;

    }
}
