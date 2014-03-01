package com.ucg.desk;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ucg.desk.view.CardAdapter;
import com.ucg.desk.view.FullCardAdapter;

public class BuilderActivity extends Activity {

    private int mColumnWidth;

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (v.getParent() != null) {
                ((ViewGroup)v.getParent()).removeView(v);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        initGrids();
    }

    private void initGrids() {
        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        mColumnWidth = point.x / 4;
        final GridLayout desk = (GridLayout) findViewById(R.id.grid_cards_desk);
        desk.setColumnCount(2);

        final GridView cards = (GridView) findViewById(R.id.grid_cards_all);
        cards.setColumnWidth(mColumnWidth);
        cards.setAdapter(new FullCardAdapter(this, getIntent().getStringExtra("CLASS")));
        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                Card s = ((CardAdapter) cards.getAdapter()).getCards().get(i);
                addCardToDesk(s, desk);
            }
        });
    }

    private void addCardToDesk(final Card s, final GridLayout desk) {
        final ImageView imageView = new ImageView(this);
        imageView.setOnClickListener(mListener);
        imageView.setMinimumWidth(mColumnWidth);
        imageView.setMaxWidth(mColumnWidth);
        Picasso.with(this).load(s.getUri()).placeholder(R.drawable.ic_launcher).into(imageView);

        desk.addView(imageView);
    }

}
