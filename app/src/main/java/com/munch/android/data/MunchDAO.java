package com.munch.android.data;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.munch.android.model.User;

/**
 * Data access object singleton
 * Created by Alan on 7/30/2016.
 */
public class MunchDAO {
    private static MunchDAO ourInstance = new MunchDAO();

    public static MunchDAO getInstance() {
        return ourInstance;
    }

    private Firebase firebase = APIManager.getInstance().getFirebase();
    private Firebase users = firebase.child("users");
    private Firebase events = firebase.child("events");

    private MunchDAO() {
    }

    /**
     * Finds User by user ID.
     *
     * @param uid User ID
     */
    public void getUser(final String uid, final UserQueryCallback callback) {
        users.orderByKey().equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCompleted(dataSnapshot.child(uid).getValue(User.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onFailed();
            }
        });
    }

    /**
     * Upserts a user to Firebase
     *
     * @param user User
     */
    public void upsertUser(User user) {
        users.child(user.getId()).setValue(user);
    }
}
