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

    private ListView wordList2;
    private ArrayList<String> ordliste2;
    private Button addWordButton2;
    private Button addButton2;
    private Button cancelButton2;
    private Button yesButton2;
    private Button noButton2;
    private EditText ordBox2;
    private EditText ordBox3;
    private TextView regler2;
    private TextView removeText2;
    private LinearLayout removeView2;
    private RelativeLayout wordlistView2;
    private RelativeLayout addwordView2;
    private ArrayAdapter<String> arrayAdapter2;
    private InputMethodManager inputManager2;
    private String o2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Ordliste");
        View rod = inflater.inflate(R.layout.ordliste_frag_list, container, false);
        inputManager2 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);



        if(MainActivity.game == null){
            MainActivity.game = new HangmanLogic();
        }
        ordliste2 = MainActivity.game.getAllWords();
        Collections.sort(ordliste2);
        wordList2 = (ListView) rod.findViewById(R.id.wordList2);
        arrayAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ordliste2);
        wordList2.setAdapter(arrayAdapter2);
        addWordButton2 = (Button) rod.findViewById(R.id.addWordButton2);
        addButton2 = (Button) rod.findViewById(R.id.addButton2);
        cancelButton2 = (Button) rod.findViewById(R.id.cancelButton2);
        yesButton2 = (Button) rod.findViewById(R.id.yesButton2);
        noButton2 = (Button) rod.findViewById(R.id.noButton2);
        ordBox2 = (EditText) rod.findViewById(R.id.ordBox2);
        ordBox3 = (EditText) rod.findViewById(R.id.ordBox3);
        regler2 = (TextView) rod.findViewById(R.id.regler2);
        removeView2 = (LinearLayout) rod.findViewById(R.id.removeView2);
        wordlistView2 = (RelativeLayout) rod.findViewById(R.id.wordlistView2);
        addwordView2 = (RelativeLayout) rod.findViewById(R.id.addwordView2);
        removeText2 = (TextView) rod.findViewById(R.id.removeText2);
        ordBox2.setHint("Skriv ord");
        addWordButton2.setOnClickListener(this);
        addButton2.setOnClickListener(this);
        cancelButton2.setOnClickListener(this);
        yesButton2.setOnClickListener(this);
        noButton2.setOnClickListener(this);
        wordList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                o2 = wordList2.getItemAtPosition(position).toString();
                wordlistView2.setVisibility(View.INVISIBLE);
                addwordView2.setVisibility(View.INVISIBLE);
                removeText2.setText("Vil du fjerne ordet \"" + o2 + "\" fra ordlisten?");
                removeView2.setVisibility(View.VISIBLE);

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
        if(v==addWordButton2){
            wordlistView2.setVisibility(View.INVISIBLE);
            addwordView2.setVisibility(View.VISIBLE);
            ordBox2.requestFocus();
            inputManager2.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        if(v==cancelButton2){
            wordlistView2.setVisibility(View.VISIBLE);
            addwordView2.setVisibility(View.INVISIBLE);
            //gemmer keyboard
            inputManager2.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ordBox2.clearFocus();
            ordBox2.setText("");
            wordList2.setAdapter(arrayAdapter2);
            errorToast("Annulleret");
        }
        if(v==addButton2){
            boolean ordbox2Check = !ordBox2.getText().toString().toLowerCase().isEmpty()
                    && ordBox2.getText().toString().toLowerCase().length()>2
                    && !ordBox2.getText().toString().toLowerCase().matches(".*\\d+.*")
                    && !ordBox2.getText().toString().contains(" ")
                    && !MainActivity.game.getAllWords().contains(ordBox2.getText().toString().toLowerCase());
            boolean ordbox3Check = !ordBox2.getText().toString().toLowerCase().isEmpty()
                    && ordBox2.getText().toString().toLowerCase().length() == 1
                    && !ordBox2.getText().toString().toLowerCase().matches(".*\\d+.*")
                    && !ordBox2.getText().toString().contains(" ")
                    && !MainActivity.game.getAllWords().contains(ordBox2.getText().toString().toLowerCase());

            if(ordbox2Check && ordbox3Check){
                MainActivity.game.addWord(ordBox2.getText().toString().toLowerCase(), ordBox3.getText().toString().toLowerCase());
                errorToast("Ordet \"" + ordBox2.getText().toString().toLowerCase() + "\" blev tilføjet til ordlisten");
            }
            else{
                errorToast("Ord ikke tilføjet. Regler ej overholdt, eller ord findes allerede på listen");
            }
            wordlistView2.setVisibility(View.VISIBLE);
            addwordView2.setVisibility(View.INVISIBLE);
            //Gemmer keyboard
            inputManager2.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ordBox2.clearFocus();
            ordBox2.setText("");
            Collections.sort(ordliste2);
            wordList2.setAdapter(arrayAdapter2);
        }
        if(v==yesButton2 && MainActivity.game.getAllWords().size()>9){
            MainActivity.game.removeWord(String.valueOf(o2));
            wordlistView2.setVisibility(View.VISIBLE);
            removeView2.setVisibility(View.INVISIBLE);
            wordList2.setAdapter(arrayAdapter2);
            errorToast("Ordet \"" + o2 + "\" blev fjernet fra listen");
        }
        if(v==yesButton2 && MainActivity.game.getAllWords().size()<=9){
            wordlistView2.setVisibility(View.VISIBLE);
            removeView2.setVisibility(View.INVISIBLE);
            wordList2.setAdapter(arrayAdapter2);
            errorToast("Der skal være mindst 9 ord i ordlisten");
        }
        if(v==noButton2){
            wordlistView2.setVisibility(View.VISIBLE);
            removeView2.setVisibility(View.INVISIBLE);
            wordList2.setAdapter(arrayAdapter2);
            errorToast("Annulleret");
        }
    }

}
