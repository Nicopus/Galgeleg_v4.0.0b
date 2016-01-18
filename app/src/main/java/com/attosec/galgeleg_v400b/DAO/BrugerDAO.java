package com.attosec.galgeleg_v400b.DAO;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by nicolaihansen on 15/01/16.
 */
public class BrugerDAO implements IBrugerDAO {

    private ArrayList<String> nicknameList = new ArrayList<>();
    private ArrayList<String> highscoreList = new ArrayList<>();
    private Query queryRef;
    private Firebase firebaseRef;
    private ChildEventListener childEventListener;



    public BrugerDAO() {
        firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
        queryRef = firebaseRef.orderByKey();
        childEventListener = queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String nickname = dataSnapshot.getKey();
                String highscore = String.valueOf(dataSnapshot.getValue());
                if (previousChildName == null) {
                    nicknameList.add(nickname);
                    highscoreList.add(highscore);
                } else {

                    int previousIndex = nicknameList.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == nicknameList.size()) {
                        nicknameList.add(nickname);
                        highscoreList.add(highscore);
                    } else {
                        nicknameList.add(nextIndex, nickname);
                        highscoreList.add(nextIndex, highscore);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public ArrayList<String> getAlleHighscore() {
        return highscoreList;
    }

    @Override
    public ArrayList<String> getAlleNicknames() {
        return nicknameList;
    }

    @Override
    public void updateHighscore(String nickname, int score) {
        firebaseRef.child(nickname).setValue(score);
    }
}
