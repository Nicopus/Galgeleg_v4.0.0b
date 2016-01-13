package com.attosec.galgeleg_v400b;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolaihansen on 12/01/16.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;

    CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    @Override
    public int getCount() {

        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {

        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {

        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.scoreboard_row, null);
        }

        TextView nicknameTxt = (TextView) convertView.findViewById(R.id.nicknameText);
        TextView highscoreTxt = (TextView) convertView.findViewById(R.id.highscoreText);
        TextView rankTxt = (TextView) convertView.findViewById(R.id.rankText);

        RowItem row_pos = rowItem.get(position);
        nicknameTxt.setText(row_pos.getNickname());
        highscoreTxt.setText(row_pos.getHighscore());
        rankTxt.setText(row_pos.getRank() + ":");


        return convertView;

    }
}
