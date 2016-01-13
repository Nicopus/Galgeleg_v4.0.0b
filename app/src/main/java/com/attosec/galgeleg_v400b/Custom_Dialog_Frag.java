package com.attosec.galgeleg_v400b;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by nicolaihansen on 13/01/16.
 */
public class Custom_Dialog_Frag extends DialogFragment implements View.OnClickListener {

    private Button dialogOkBtn;
    private Button dialogAnnullerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_dialog_frag, container, false);
        dialogOkBtn = (Button) rootView.findViewById(R.id.okBtn);
        dialogAnnullerBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        dialogOkBtn.setOnClickListener(this);
        dialogAnnullerBtn.setOnClickListener(this);
        getDialog().setTitle("Highscore slået");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == dialogAnnullerBtn) {
            Log.v("Ok trykket", "vikrer");
        } else {
            Log.v("Annuller trykket", "vikrer");
        }

        dismiss();
    }
}
