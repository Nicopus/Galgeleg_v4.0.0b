package com.attosec.galgeleg_v400b.DAO;

import android.util.Log;

import com.attosec.galgeleg_v400b.DTO.BrugerDTO;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class BrugerDAO implements IBrugerDAO {

    private Firebase firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
    private ArrayList<BrugerDTO> scoreboardList = new ArrayList<>();
    private String highscore;
    private String nickname;

    @Override
    public BrugerDTO getHighscore(final String nickname) {
        Query queryRef = firebaseRef.orderByKey().equalTo(nickname);
        scoreboardList.clear();
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                highscore = String.valueOf(dataSnapshot.child(nickname).getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return new BrugerDTO(highscore, nickname);
    }

    @Override
    public void updateHighscore(String nickname, int point) {
        scoreboardList.clear();
        firebaseRef.child(nickname).setValue(point);
    }

    @Override
    public ArrayList<BrugerDTO> getScoreboard() {
        Query queryRef = firebaseRef.orderByValue().limitToFirst(30);
        queryRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                nickname = dataSnapshot.getKey();
                highscore = String.valueOf(dataSnapshot.getValue());
                //Log.v("scoreboar_frag", nickname + " : " + highscore);
                scoreboardList.add(new BrugerDTO(highscore, nickname));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Empty: Bruges ikke
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Empty: Bruges ikke
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //Empty: Bruges ikke
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //Empty: Bruges ikke
            }
        });

        return scoreboardList;
    }
}
