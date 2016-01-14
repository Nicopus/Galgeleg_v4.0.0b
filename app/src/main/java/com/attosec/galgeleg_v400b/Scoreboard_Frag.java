package com.attosec.galgeleg_v400b;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.attosec.galgeleg_v400b.baseadapter.CustomAdapter;
import com.attosec.galgeleg_v400b.baseadapter.RowItem;
import java.util.ArrayList;


public class Scoreboard_Frag extends ListFragment {

    private ArrayList<String> nicknameList;
    private ArrayList<String> highscoreList;
    private CustomAdapter adapter;
    private ArrayList<RowItem> rowItems;
    private DrAsync firebaseOrdliste;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        getActivity().setTitle("Scoreboard");
        View rod = inflater.inflate(R.layout.scoreboard_frag, container, false);

        if(MainActivity.game == null){
            MainActivity.game = new HangmanLogic();
        }

        firebaseOrdliste = new DrAsync();
        firebaseOrdliste.execute();

        rowItems = new ArrayList<>();
        nicknameList = MainActivity.game.getTop30Nicknames();
        highscoreList = MainActivity.game.getTop30Highscores();

        for (int i = 0; i < nicknameList.size(); i++) {
            rowItems.add(new RowItem(nicknameList.get(i), highscoreList.get(i), i+1));
        }

        adapter = new CustomAdapter(getContext(), rowItems);
        setListAdapter(adapter);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Scoreboard");

        MainActivity.game.opdaterScoreboard();

        return rod;
    }
    private class DrAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (highscoreList.size() < 10) {
                try {
                    MainActivity.game.opdaterScoreboard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            MainActivity.game.getTop30Highscores();
            MainActivity.game.getTop30Nicknames();
        }

    }
}
