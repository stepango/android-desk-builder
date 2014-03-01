package com.ucg.desk.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.ucg.desk.BuilderActivity;
import com.ucg.desk.R;

/**
 * Created by sone on 01.03.14.
 */
public class ChooseDeskFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fmt_class_choose, null, false);
        GridLayout layout = (GridLayout) v.findViewById(R.id.lay_buttons);
        String[] classes = getResources().getStringArray(R.array.classes);
        for (String aClass : classes) {
            ImageButton button = new ImageButton(inflater.getContext());
            button.setTag(aClass);
            button.setOnClickListener(this);
            button.setImageDrawable(getDrawableByName(aClass));
            layout.addView(button, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return v;
    }

    private Drawable getDrawableByName(String name) {
        return getResources().getDrawable(getResources()
                .getIdentifier(name, "drawable", getActivity().getPackageName()));
    }

    @Override
    public void onClick(final View v) {
        String tag = (String) v.getTag();
        Intent intent = new Intent(getActivity(), BuilderActivity.class);
        intent.putExtra("CLASS", tag);
        startActivity(intent);
    }

}
