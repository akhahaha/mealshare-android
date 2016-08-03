package com.munch.android.data;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.munch.android.model.Meal;
import com.munch.android.model.RSVP;
import com.munch.android.model.User;
import com.munch.android.util.MealList;
import com.munch.android.util.UserList;

import java.util.Calendar;

/**
 * Munch data access object
 * Created by Alan on 7/30/2016.
 */
public class MunchDAO {
    private static Firebase firebase = APIManager.getInstance().getFirebase();
    private static Firebase users = firebase.child("users");
    private static Firebase meals = firebase.child("meals");
    private static Firebase rsvps = firebase.child("rsvps");

    /**
     * Creates a user.
     *
     * @param user     User object
     * @param callback Result callback
     */
    public static void createUser(final User user, final Callback<Boolean> callback) {
        // Verify user does not exist
        getUser(user.getId(), new Callback<User>() {
            @Override
            public void onResponse(User currUser) {
                if (currUser == null && user.getId() != null) {
                    users.child(user.getId()).setValue(user, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (callback != null) {
                                callback.onResponse(true);
                            }
                        }
                    });
                } else if (callback != null) {
                    callback.onFailed(new Exception()); // TODO
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) {
                    callback.onFailed(e);
                }
            }
        });
    }

    /**
     * Creates a user.
     *
     * @param user User object
     */
    public static void createUser(User user) {
        createUser(user, null);
    }

    /**
     * Updates a user.
     *
     * @param user     User object
     * @param callback Result callback
     */
    public static void updateUser(final User user, final Callback<Boolean> callback) {
        // Verify user exists
        getUser(user.getId(), new Callback<User>() {
            @Override
            public void onResponse(User currUser) {
                if (currUser != null) {
                    users.child(user.getId()).setValue(user, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (callback != null) {
                                callback.onResponse(true);
                            }
                        }
                    });
                } else if (callback != null) {
                    callback.onFailed(new Exception()); // TODO
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) {
                    callback.onFailed(e);
                }
            }
        });
    }

    /**
     * Updates a user.
     *
     * @param user User object
     */
    public static void updateUser(User user) {
        updateUser(user, null);
    }

    /**
     * Retrieves a specified user.
     *
     * @param userID   User ID
     * @param callback User callback
     */
    public static void getUser(final String userID, final Callback<User> callback) {
        users.orderByKey().equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(userID).getValue(User.class);
                if (user != null) {
                    callback.onResponse(user);
                } else {
                    callback.onResponse(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onFailed(new Exception()); // TODO
            }
        });
    }

    /**
     * Creates a meal.
     *
     * @param meal     Meal object
     * @param callback Result callback
     */
    public static void pushMeal(final Meal meal, final Callback<String> callback) {
        // Verify host user exists
        getUser(meal.getHostID(), new Callback<User>() {
            @Override
            public void onResponse(User user) {
                // TODO: Verify valid time
                // TODO: Verify restaurant exists
                meal.setId(meals.push().getKey());
                meals.child(meal.getId()).setValue(meal, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (callback != null) {
                            callback.onResponse(meal.getId());
                        }
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) {
                    callback.onFailed(e);
                }
            }
        });
    }

    /**
     * Creates a meal.
     *
     * @param meal Meal object
     */
    public static void pushMeal(final Meal meal) {
        pushMeal(meal, null);
    }

    /**
     * Updates a meal.
     *
     * @param meal     Meal object
     * @param callback Result callback
     */
    public static void updateMeal(final Meal meal, final Callback<Boolean> callback) {
        // Verify meal exists
        getMeal(meal.getId(), new Callback<Meal>() {
            @Override
            public void onResponse(Meal currMeal) {
                if (currMeal != null) {
                    meals.child(meal.getId()).setValue(meal, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (callback != null) {
                                callback.onResponse(true);
                            }
                        }
                    });
                } else if (callback != null) {
                    callback.onFailed(new Exception()); // TODO
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) {
                    callback.onFailed(e);
                }
            }
        });
    }

    /**
     * Updates a meal.
     *
     * @param meal Meal object
     */
    public static void updateMeal(final Meal meal) {
        updateMeal(meal, null);
    }

    /**
     * Removes a meal.
     *
     * @param meal     Meal object
     * @param callback Result callback
     */
    public static void removeMeal(Meal meal, Callback<Boolean> callback) {
        meals.child(meal.getId()).removeValue();
        if (callback != null) {
            callback.onResponse(true);
        }
    }

    /**
     * Removes a meal.
     *
     * @param meal Meal object
     */
    public static void removeMeal(Meal meal) {
        removeMeal(meal, null);
    }

    /**
     * Retrieves a specified meal.
     *
     * @param mealID   Meal ID
     * @param callback Result callback
     */
    public static void getMeal(final String mealID, final Callback<Meal> callback) {
        meals.orderByKey().equalTo(mealID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meal meal = dataSnapshot.child(mealID).getValue(Meal.class);
                if (meal != null) {
                    callback.onResponse(dataSnapshot.child(mealID).getValue(Meal.class));
                } else {
                    callback.onResponse(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onFailed(new Exception()); // TODO
            }
        });
    }

    /**
     * Retrieves all meals hosted by a specified user.
     *
     * @param userID   User ID
     * @param callback Result callback
     */
    public static void getUserHostedMeals(final String userID, final Callback<MealList> callback) {
        meals.orderByChild("hostID").equalTo(userID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MealList meals = new MealList();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            meals.add(child.getValue(Meal.class));
                        }
                        callback.onResponse(meals);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        callback.onFailed(new Exception()); // TODO
                    }
                });
    }

    /**
     * Adds a RSVP to a meal.
     *
     * @param rsvp     RSVP object
     * @param callback Result callback
     */
    public static void addRSVP(final RSVP rsvp, final Callback<Boolean> callback) {
        // Verify meal exists
        getMeal(rsvp.getMealID(), new Callback<Meal>() {
            @Override
            public void onResponse(final Meal meal) {
                // Verify user exists
                getUser(rsvp.getUserID(), new Callback<User>() {
                    @Override
                    public void onResponse(User user) {
                        // Verify user is not host
                        if (!meal.getHostID().equals(rsvp.getUserID())) {
                            getMealRSVPs(rsvp.getMealID(), new Callback<UserList>() {
                                @Override
                                public void onResponse(UserList users) {
                                    // Verify party is not at capacity
                                    if (meal.getPartyMax() == null || meal.getPartyMax() == 0 ||
                                            users.size() < meal.getPartyMax()) {
                                        rsvps.child(String.valueOf(rsvp.hashCode())).setValue(rsvp,
                                                new Firebase.CompletionListener() {
                                                    @Override
                                                    public void onComplete(
                                                            FirebaseError firebaseError,
                                                            Firebase firebase) {
                                                        if (callback != null) {
                                                            callback.onResponse(true);
                                                        }
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onFailed(Exception e) {
                                    if (callback != null) {
                                        callback.onFailed(e);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailed(Exception e) {
                        if (callback != null) {
                            callback.onFailed(e);
                        }
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) {
                    callback.onFailed(e);
                }
            }
        });
    }

    /**
     * Adds a RSVP.
     *
     * @param rsvp RSVP object
     */
    public static void addRSVP(RSVP rsvp) {
        addRSVP(rsvp, null);
    }

    /**
     * Removes a RSVP.
     *
     * @param rsvp     RSVP object
     * @param callback Result callback
     */
    public static void removeRSVP(RSVP rsvp, Callback<Boolean> callback) {
        rsvps.child(String.valueOf(rsvp.hashCode())).removeValue();
        if (callback != null) {
            callback.onResponse(true);
        }
    }

    /**
     * Removes a RSVP.
     *
     * @param rsvp RSVP object
     */
    public static void removeRSVP(RSVP rsvp) {
        removeRSVP(rsvp, null);
    }

    /**
     * Retrieves users RSVPed to a specified meal.
     *
     * @param mealID   Meal ID
     * @param callback Result callback
     */
    public static void getMealRSVPs(final String mealID, final Callback<UserList> callback) {
        rsvps.orderByChild("mealID").equalTo(mealID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        final UserList users = new UserList();
                        if (dataSnapshot.getChildrenCount() == 0) {
                            callback.onResponse(users);
                        }
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            getUser(child.getValue(RSVP.class).getUserID(),
                                    new Callback<User>() {
                                        @Override
                                        public void onResponse(User user) {
                                            users.add(user);
                                            if (users.size() == dataSnapshot.getChildrenCount()) {
                                                callback.onResponse(users);
                                            }
                                        }

                                        @Override
                                        public void onFailed(Exception e) {
                                            callback.onFailed(e);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        callback.onFailed(new Exception()); // TODO
                    }
                }
        );
    }

    /**
     * Retrieves all meals RSVPed to by a specified user.
     *
     * @param userID   User ID
     * @param callback Result callback
     */
    public static void getUserRSVPs(final String userID, final Callback<MealList> callback) {
        rsvps.orderByChild("userID").equalTo(userID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        final MealList meals = new MealList();
                        if (dataSnapshot.getChildrenCount() == 0) {
                            callback.onResponse(meals);
                        }
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            getMeal(child.getValue(RSVP.class).getMealID(),
                                    new Callback<Meal>() {
                                        @Override
                                        public void onResponse(Meal meal) {
                                            meals.add(meal);
                                            if (meals.size() == dataSnapshot.getChildrenCount()) {
                                                callback.onResponse(meals);
                                            }
                                        }

                                        @Override
                                        public void onFailed(Exception e) {
                                            callback.onFailed(e);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        callback.onFailed(new Exception()); // TODO
                    }
                }
        );
    }

    /**
     * Retrieves all meals a specified user is part of.
     *
     * @param userID   User ID
     * @param callback Result callback
     */
    public static void getUserMeals(final String userID, final Callback<MealList> callback) {
        getUserHostedMeals(userID, new Callback<MealList>() {
            @Override
            public void onResponse(final MealList hostedMeals) {
                getUserRSVPs(userID, new Callback<MealList>() {
                    @Override
                    public void onResponse(final MealList rsvpMeals) {
                        MealList meals = new MealList();
                        meals.addAll(hostedMeals);
                        meals.addAll(rsvpMeals);
                        callback.onResponse(meals);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        callback.onFailed(e);
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }

    /**
     * Retrieves all upcoming meals a user is part of.
     *
     * @param userID   User ID
     * @param callback Result callback
     */
    public static void getUserUpcomingMeals(String userID, final Callback<MealList> callback) {
        getUserMeals(userID, new Callback<MealList>() {
            @Override
            public void onResponse(MealList meals) {
                // Filter and sort meals
                meals.filterTimeBegin(Calendar.getInstance().getTime());
                meals.sortByTime();

                callback.onResponse(meals);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(new Exception()); // TODO
            }
        });
    }

    public static void searchUpcomingMeals(final Callback<MealList> callback) {
        meals.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MealList meals = new MealList();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    meals.add(child.getValue(Meal.class));
                }

                // Filter and sort meals
                meals.filterTimeBegin(Calendar.getInstance().getTime());
                meals.sortByTime();

                callback.onResponse(meals);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onFailed(new Exception()); // TODO
            }
        });
    }
}
