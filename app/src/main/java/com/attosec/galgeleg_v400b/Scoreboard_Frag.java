package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard_Frag extends Fragment {

    private InputMethodManager inputManager2;
    private ArrayList<String> nicknameList;
    private ArrayList<String> highscoreList;
    private ListView highscoreListView;
    private ListView nicknameListView;
    private ArrayAdapter<String> nicknameArrayAdapter;
    private ArrayAdapter<String> highscoreArrayAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        getActivity().setTitle("Scoreboard");
        View rod = inflater.inflate(R.layout.scoreboard_frag, container, false);
        inputManager2 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(MainActivity.game == null){
            MainActivity.game = new HangmanLogic();
        }
        highscoreListView = (ListView) rod.findViewById(R.id.highscoreList);
        nicknameListView = (ListView) rod.findViewById(R.id.nicknameList);
        //nicknameList = MainActivity.game.getTop30Nicknames();
        //highscoreList = MainActivity.game.getTop30Highscores();

        nicknameList = new ArrayList<>();
        highscoreList = new ArrayList<>();
        nicknameList.add("Nicolai");
        nicknameList.add("Mathias");
        nicknameList.add("Saz");
        highscoreList.add("100");
        highscoreList.add("200");
        highscoreList.add("300");

        Log.v("afaeaf", String.valueOf(nicknameList.size()));
        Log.v("afaeaf", String.valueOf(highscoreList.size()));
        if (!nicknameList.isEmpty()) {
            nicknameArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nicknameList);
            highscoreArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.scoreboard_row, R.id.highscore, highscoreList);
            nicknameListView.setAdapter(nicknameArrayAdapter);
            highscoreListView.setAdapter(highscoreArrayAdapter);
        }
            // Inflate the layout for this fragment
        return rod;
    }


}
