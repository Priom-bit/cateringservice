package com.example.cateringservice;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class Services {
    private final String TAG = Services.class.getSimpleName();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private Services() {
        Log.v(TAG, "Services constructor created");
    }
    private static final Services instance = new Services();
    public static Services getInstance() {
        return instance;
    }

    public interface FireStoreCompletionListener {
        void onGetSuccess(QuerySnapshot querySnapshots);
        void onPostSuccess();
        void onFailure(String error);
    }

    public void getRequest(String collectionName, Map<String, String> param1, Map<String, String> param2, int limit, FireStoreCompletionListener listener) {
        String key1 = "", value1 = "", key2 = "", value2 = "";
        for (Map.Entry<String, String> entry : param1.entrySet()) {
            key1 = entry.getKey();
            value1 = entry.getValue();
        }

        if (param2 == null) {
            database.collection(collectionName)
                    .whereEqualTo(key1, value1)
                    .limit(limit)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        listener.onGetSuccess(queryDocumentSnapshots);
                    }).addOnFailureListener(e -> {
                listener.onFailure(e.getLocalizedMessage());
            });
        }
        else {
            for (Map.Entry<String, String> entry : param2.entrySet()) {
                key2 = entry.getKey();
                value2 = entry.getValue();
            }
            database.collection(collectionName)
                    .whereEqualTo(key1, value1)
                    .whereEqualTo(key2, value2)
                    .limit(limit)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        listener.onGetSuccess(queryDocumentSnapshots);
                    }).addOnFailureListener(e -> {
                listener.onFailure(e.getLocalizedMessage());
            });
        }
    }

    public void getRequestGreaterThanOrEqual(String collectionName, String fieldName, int value, int limit, FireStoreCompletionListener listener) {
        database.collection(collectionName)
                .whereGreaterThanOrEqualTo(fieldName, value)
                .limit(limit)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        listener.onGetSuccess(querySnapshot);
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure(e.getLocalizedMessage());
            }
        });
    }

    public void getRequestGreaterLess(String collectionName, String fieldName, Integer value1, Integer value2, int limit, FireStoreCompletionListener listener) {
        database.collection(collectionName)
                .whereGreaterThanOrEqualTo(fieldName, value1)
                .whereLessThanOrEqualTo(fieldName, value2)
                .limit(limit)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        listener.onGetSuccess(querySnapshot);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure(e.getLocalizedMessage());
                    }
                });
    }

    public void getRequest(String collectionName, int limit, FireStoreCompletionListener listener) {
        database.collection(collectionName)
                .limit(limit)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listener.onGetSuccess(queryDocumentSnapshots);
                }).addOnFailureListener(e -> {
            listener.onFailure(e.getLocalizedMessage());
        });
    }

    public void getRequest(String collectionName, String fieldName, List<Integer> values, int limit, FireStoreCompletionListener listener) {
        Log.v(TAG, "Nirob test values: " + values.toString());
        database.collection(collectionName)
                .whereIn(fieldName, values)
                .limit(limit)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listener.onGetSuccess(queryDocumentSnapshots);
                }).addOnFailureListener(e -> {
            listener.onFailure(e.getLocalizedMessage());
        });
    }

    public void postRequest(String collectionName, Map<String, Object> user, FireStoreCompletionListener listener) {
        database.collection(collectionName)
        .add(user)
        .addOnSuccessListener(queryDocumentSnapshots -> {
            listener.onPostSuccess();
        }).addOnFailureListener(e -> {
            listener.onFailure(e.getLocalizedMessage());
        });
    }
}
