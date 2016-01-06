
package com.attosec.galgeleg_v400b;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
/*
public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFrag, fragment)  // tom container i layout
                    .commit();
        }
        if(Spil_Frag.game == null){
            Spil_Frag.game = new HangmanLogic();
        }
        DrAsync g = new DrAsync();
        g.execute();

        setTitle("Galgeleg");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // brugeren vil navigere op i hierakiet
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (Spil_Frag.game.getAllWords().size() == 8) {
                try {
                    Spil_Frag.game.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(Spil_Frag.game.getAllWords().size()==8){

            }
            return null;
        }

        protected void onPostExecute(Void result){
/*          findViewById(R.id.loadingText).setVisibility(View.INVISIBLE);
            findViewById(R.id.playButton).setVisibility(View.VISIBLE);
            findViewById(R.id.helpButton).setVisibility(View.VISIBLE);
            findViewById(R.id.aboutButton).setVisibility(View.VISIBLE);
            findViewById(R.id.wordButton).setVisibility(View.VISIBLE);
            findViewById(R.id.playImg).setVisibility(View.VISIBLE);
            findViewById(R.id.helpImg).setVisibility(View.VISIBLE);
            findViewById(R.id.aboutImg).setVisibility(View.VISIBLE);
            findViewById(R.id.wordImg).setVisibility(View.VISIBLE);
            prog.setVisibility(View.INVISIBLE);
            */
/*
        }
    }
}

*/
