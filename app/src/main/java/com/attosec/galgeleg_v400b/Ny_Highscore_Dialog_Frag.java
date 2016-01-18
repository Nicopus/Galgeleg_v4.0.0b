package com.attosec.galgeleg_v400b;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nicolaihansen on 18/01/16.
 */
public class Ny_Highscore_Dialog_Frag extends DialogFragment implements View.OnClickListener {


    private Button dialogOkBtn;
    private TextView dialogHighscoreTxt;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ny_highscore_dialog_frag, container, false);
        dialogOkBtn = (Button) rootView.findViewById(R.id.okButton);
        dialogHighscoreTxt = (TextView) rootView.findViewById(R.id.newhighscoreTxt);
        dialogOkBtn.setOnClickListener(this);
        getDialog().setTitle("Du fik " + MainActivity.game.getScore() + " point");

        dialogHighscoreTxt.setText("Du slog din gamle highscore på " + MainActivity.game.getHighscore(MainActivity.game.readFromFile(getContext()))
        + " point. Din nye highscore på " + MainActivity.game.getScore() + " point er blevet opdateret.");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //Hvis brugeren trykker OK gemmes nickname i en fil i appen og en nye bruger indsætte i firebase
        dismiss();

    }
}


