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
public class ScoreboardDAO {

    private ArrayList<String> nicknameList = new ArrayList<>();
    private ArrayList<String> highscoreList = new ArrayList<>();
    private Query queryRef;
    private Firebase firebaseRef;
    private ChildEventListener childEventListener;



    public ScoreboardDAO() {
        firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
        queryRef = firebaseRef.orderByValue().limitToLast(10);
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
                String nickname = dataSnapshot.getKey();
                String highscore = String.valueOf(dataSnapshot.getValue());
                int index = nicknameList.indexOf(nickname);
                nicknameList.set(index, nickname);
                highscoreList.set(index, highscore);
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

    public ArrayList<String> getTop30Nicknames() {
        return nicknameList;
    }
    public ArrayList<String> getTop30Highscores() {
        return highscoreList;
    }
}
