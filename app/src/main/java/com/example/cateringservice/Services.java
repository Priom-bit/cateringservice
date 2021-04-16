package com.example.cateringservice;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Services {
    private final String TAG = Services.class.getSimpleName();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
//    FireStoreCompletionListener listener;

    private Services() {
        //listener = null;
        Log.v(TAG, "Nirob test constructor created");
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

//    public void setListener(FireStoreCompletionListener _listener) {
//        listener = _listener;
//    }

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
