package com.attosec.galgeleg_v400b;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nicolaihansen on 13/01/16.
 */
public class Custom_Dialog_Frag extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_dialog_frag, container, false);
        getDialog().setTitle("Highscore slået");
        return rootView;
    }
}
