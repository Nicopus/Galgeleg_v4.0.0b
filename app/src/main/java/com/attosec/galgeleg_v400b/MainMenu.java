package com.attosec.galgeleg_v400b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenu extends Fragment implements View.OnClickListener {
    Button startSpil, highscore, omAppen, hjaelp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Galgeleg");
        View mainView = inflater.inflate(R.layout.main_menu_frag, container, false);

        startSpil = (Button) mainView.findViewById(R.id.btnStartGame);
        startSpil.setText("Start Spil");
        startSpil.setOnClickListener(this);


        highscore = (Button) mainView.findViewById(R.id.btnHighscore);
        highscore.setText("Highscore");
        highscore.setOnClickListener(this);
/*
        omAppen = (Button) mainView.findViewById(R.id.btnAbout);
        omAppen.setText("Om Appen");
        omAppen.setOnClickListener(this);

        hjaelp = (Button) mainView.findViewById(R.id.btnHelp);
        hjaelp.setText("Hjælp");
        hjaelp.setOnClickListener(this);

*/
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Galgeleg");
        return mainView;
    }

    public void onClick(View v) {

    if (v == startSpil) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Spil_Frag(), "SPIL_FRAG")
                    .addToBackStack(null)
                    .commit();

        /*

        } else if (v == omAppen) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new OmAppen_Frag())
                    .addToBackStack(null)
                    .commit();

        } else if (v == hjaelp) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Hjaelp_Frag())
                    .addToBackStack(null)
                    .commit();
*/

        } else if (v == highscore) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Scoreboard_Frag())
                    .addToBackStack(null)
                    .commit();
        }


    }

}