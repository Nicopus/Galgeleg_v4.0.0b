package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;


public class Scoreboard_Frag extends ListFragment {

    private InputMethodManager inputManager2;
    private ArrayList<String> nicknameList;
    private ArrayList<String> highscoreList;
    private CustomAdapter adapter;
    private ArrayList<RowItem> rowItems;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        getActivity().setTitle("Scoreboard");
        View rod = inflater.inflate(R.layout.scoreboard_frag, container, false);
        inputManager2 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(MainActivity.game == null){
            MainActivity.game = new HangmanLogic();
        }

        rowItems = new ArrayList<>();
        nicknameList = MainActivity.game.getTop30Nicknames();
        highscoreList = MainActivity.game.getTop30Highscores();
        Log.v("scoreboard frag test", String.valueOf(nicknameList.size()));

        for (int i = 0; i < nicknameList.size(); i++) {
            rowItems.add(new RowItem(nicknameList.get(i), highscoreList.get(i), i+1));
        }

        adapter = new CustomAdapter(getContext(), rowItems);
        setListAdapter(adapter);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Scoreboard");

        return rod;
    }
}
