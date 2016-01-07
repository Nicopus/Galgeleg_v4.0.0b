package com.attosec.galgeleg_v400b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenu extends Fragment implements View.OnClickListener {
    Button startSpil, ordListe, omAppen, hjaelp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Galgeleg");
        View mainView = inflater.inflate(R.layout.main_menu_frag, container, false);
/*
        startSpil = (Button) mainView.findViewById(R.id.btnStartGame);
        startSpil.setText("Start Spil");
        startSpil.setOnClickListener(this);


        ordListe = (Button) mainView.findViewById(R.id.btnWordList);
        ordListe.setText("Ordliste");
        ordListe.setOnClickListener(this);

        omAppen = (Button) mainView.findViewById(R.id.btnAbout);
        omAppen.setText("Om Appen");
        omAppen.setOnClickListener(this);

        hjaelp = (Button) mainView.findViewById(R.id.btnHelp);
        hjaelp.setText("Hjælp");
        hjaelp.setOnClickListener(this);

*/
        return mainView;
    }

    public void onClick(View v) {/*
        if (v == hjaelp) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Hjaelp_Frag())
                    .addToBackStack(null)
                    .commit();

        } else if (v == omAppen) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new OmAppen_Frag())
                    .addToBackStack(null)
                    .commit();

        } else if (v == startSpil) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Spil_Frag())
                    .addToBackStack(null)
                    .commit();


        } else if (v == ordListe) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Ordliste_Frag())
                    .addToBackStack(null)
                    .commit();
        }*/


    }

}