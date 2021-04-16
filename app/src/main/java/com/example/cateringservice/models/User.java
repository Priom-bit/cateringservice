package com.example.cateringservice.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class User {
    public String documentId;
    public String firstName;
    public String lastName;
    public String email;
    public String gender;

    public static User getUserFrom(DocumentSnapshot documentSnapshot) {
        User user = new  User();
        user.documentId = documentSnapshot.getId();
        user.firstName = documentSnapshot.getString("firstName");
        user.lastName = documentSnapshot.getString("lastName");
        user.email = documentSnapshot.getString("email");
        user.gender = documentSnapshot.getString("gender");

        return user;
    }
}
