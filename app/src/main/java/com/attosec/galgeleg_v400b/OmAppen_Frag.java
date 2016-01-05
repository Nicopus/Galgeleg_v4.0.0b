package com.attosec.galgeleg_v400b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OmAppen_Frag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Om Appen");
        return inflater.inflate(R.layout.om_appen_frag, container, false);
    }


}
