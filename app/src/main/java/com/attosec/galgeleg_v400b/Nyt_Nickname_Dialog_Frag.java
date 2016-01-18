package com.attosec.galgeleg_v400b;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by nicolaihansen on 13/01/16.
 */
public class Nyt_Nickname_Dialog_Frag extends DialogFragment implements View.OnClickListener {

    private Button dialogOkBtn;
    private Button dialogAnnullerBtn;
    private EditText dialogNicknameTxt;
    private TextView dialogErrorTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nyt_nickname_dialog_frag, container, false);
        dialogOkBtn = (Button) rootView.findViewById(R.id.okBtn);
        dialogAnnullerBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        dialogNicknameTxt = (EditText) rootView.findViewById(R.id.nicknameEditText);
        dialogErrorTxt = (TextView) rootView.findViewById(R.id.errorText);
        dialogOkBtn.setOnClickListener(this);
        dialogAnnullerBtn.setOnClickListener(this);
        getDialog().setTitle("Du fik " + MainActivity.game.getScore() + " point");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //Hvis brugeren trykker OK gemmes nickname i en fil i appen og en nye bruger indsætte i firebase
        if (v == dialogOkBtn) {
            String tempNickname = dialogNicknameTxt.getText().toString();

            if (!MainActivity.game.checkNicknames(tempNickname)) {
                MainActivity.game.writeToFile(getContext(), dialogNicknameTxt.getText().toString());
                MainActivity.game.indsætNyHighscore(dialogNicknameTxt.getText().toString());
                dismiss();
            } else {
                dialogErrorTxt.setText("Dette nickname er allerede taget. Vælg et andet nickname!");
                dialogErrorTxt.setVisibility(View.VISIBLE);

            }



        } else {
        //Hvis brugeren trykker annuller sættes en boolean variable til true så denne dialog ignoreres
        //så længe appen kører i baggrunden
            MainActivity.game.setIgnorerRegistrering(true);
            dismiss();
        }

    }
}
