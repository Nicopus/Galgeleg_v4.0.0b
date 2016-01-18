package com.attosec.galgeleg_v400b.DAO;

import android.util.Log;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;


/**
 * Created by nicolaihansen on 08/01/16.
 */
public class BrugerDAO implements IBrugerDAO {

    private Query queryRef;
    private Firebase firebaseRef;
    private ChildEventListener childEventListener;
    private String nickname;
    private String highscore;

    public BrugerDAO(String name){

    firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
    queryRef = firebaseRef.orderByKey().equalTo(name);
    childEventListener = queryRef.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            if (dataSnapshot.exists()) {
                nickname = dataSnapshot.getKey();
                highscore = String.valueOf(dataSnapshot.getValue());
            } else {
                nickname = "";
            }

            Log.v("firebase name", dataSnapshot.getKey() + highscore);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            highscore = String.valueOf(dataSnapshot.getValue());
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
    public String getHighscore() {
       return highscore;
    }

    @Override
    public String getNickname(){
        return nickname;
    }

    @Override
    public void updateHighscore(String nickname, int score) {
        firebaseRef.child(nickname).setValue(score);
    }

}
