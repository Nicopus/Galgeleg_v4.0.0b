package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class Ordliste_Frag extends Fragment implements View.OnClickListener {
    private ListView wordList;
    private ArrayList<String> ordliste;
    private Button addWordButton;
    private Button addButton;
    private Button cancelButton;
    private Button yesButton;
    private Button noButton;
    private EditText ordBox;
    private TextView removeText;
    private LinearLayout removeView;
    private RelativeLayout wordlistView;
    private RelativeLayout addwordView;
    private ArrayAdapter<String> arrayAdapter;
    private InputMethodManager inputManager;
    private int deletePosition;
    private String o;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Ordliste");
        View rod = inflater.inflate(R.layout.ordliste_frag_list, container, false);
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(MainActivity.game == null){
            MainActivity.game = new HangmanLogic();
        }

        ordliste = MainActivity.game.getMuligeOrd();
        Collections.sort(ordliste);
        wordList = (ListView) rod.findViewById(R.id.wordList2);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ordliste);
        wordList.setAdapter(arrayAdapter);
        addWordButton = (Button) rod.findViewById(R.id.addWordButton2);
        addButton = (Button) rod.findViewById(R.id.addButton2);
        cancelButton = (Button) rod.findViewById(R.id.cancelButton2);
        yesButton = (Button) rod.findViewById(R.id.yesButton2);
        noButton = (Button) rod.findViewById(R.id.noButton2);
        removeView = (LinearLayout) rod.findViewById(R.id.removeView2);
        wordlistView = (RelativeLayout) rod.findViewById(R.id.wordlistView2);
        addwordView = (RelativeLayout) rod.findViewById(R.id.addwordView2);
        removeText = (TextView) rod.findViewById(R.id.removeText2);
        ordBox = (EditText) rod.findViewById(R.id.ordBox2);
        ordBox.setHint("Skriv ord");
        addWordButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);

        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                o = wordList.getItemAtPosition(position).toString();
                wordlistView.setVisibility(View.INVISIBLE);
                addwordView.setVisibility(View.INVISIBLE);
                removeText.setText("Vil du fjerne ordet \"" + o + "\" fra ordlisten?");
                removeView.setVisibility(View.VISIBLE);
                deletePosition = position;
            }
        });
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Ordliste");

        return rod;
    }

    public void errorToast(String str){
        Context context = getActivity().getApplicationContext();
        CharSequence text =str;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==addWordButton){
            wordlistView.setVisibility(View.INVISIBLE);
            addwordView.setVisibility(View.VISIBLE);
            ordBox.requestFocus();
            inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        if(v==cancelButton){
            wordlistView.setVisibility(View.VISIBLE);
            addwordView.setVisibility(View.INVISIBLE);
            //gemmer keyboard
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ordBox.clearFocus();
            ordBox.setText("");
            wordList.setAdapter(arrayAdapter);
            errorToast("Annulleret");
        }

        if(v==addButton){
            boolean ordbox2Check = !ordBox.getText().toString().toLowerCase().isEmpty()
                    && ordBox.getText().toString().toLowerCase().length()>2
                    && !ordBox.getText().toString().toLowerCase().matches(".*\\d+.*")
                    && !ordBox.getText().toString().contains(" ")
                    && !MainActivity.game.getMuligeOrd().contains(ordBox.getText().toString().toLowerCase());

            if(ordbox2Check){
                MainActivity.game.tilføjOrd(ordBox.getText().toString().toLowerCase());
                errorToast("Ordet \"" + ordBox.getText().toString().toLowerCase() + "\" blev tilføjet til ordlisten");
            }
            else{
                errorToast("Ord ikke tilføjet. Regler ej overholdt, eller ord findes allerede på listen");
            }
            wordlistView.setVisibility(View.VISIBLE);
            addwordView.setVisibility(View.INVISIBLE);
            //Gemmer keyboard
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ordBox.clearFocus();
            ordBox.setText("");
            Collections.sort(ordliste);
            arrayAdapter.notifyDataSetChanged();

            //wordList.setAdapter(arrayAdapter);
        }

        if(v==yesButton && MainActivity.game.getMuligeOrd().size()>9){
            MainActivity.game.fjernOrd(deletePosition);
            wordlistView.setVisibility(View.VISIBLE);
            removeView.setVisibility(View.INVISIBLE);
            wordList.setAdapter(arrayAdapter);
            errorToast("Ordet \"" + o + "\" blev fjernet fra listen");
        }

        if(v==yesButton && MainActivity.game.getMuligeOrd().size()<=9){
            wordlistView.setVisibility(View.VISIBLE);
            removeView.setVisibility(View.INVISIBLE);
            wordList.setAdapter(arrayAdapter);
            errorToast("Der skal være mindst 9 ord i ordlisten");
        }

        if(v==noButton){
            wordlistView.setVisibility(View.VISIBLE);
            removeView.setVisibility(View.INVISIBLE);
            wordList.setAdapter(arrayAdapter);
            errorToast("Annulleret");
        }
    }
}
