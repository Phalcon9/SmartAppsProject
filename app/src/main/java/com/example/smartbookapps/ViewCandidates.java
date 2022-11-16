package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidates extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView candidateRV;
    Button startBtn;

//    ArrayList<CandidateModel> candidateModelArrayList;
    private List<CandidateModel> list;

    MyVotingAdapter myVotingAdapter;

//    FirestoreRecyclerAdapter adapter;


    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_candidates);
        startBtn = findViewById (R.id.start);
        firebaseFirestore = FirebaseFirestore.getInstance ();
        candidateRV = findViewById (R.id.firestore_list11);
        list = new ArrayList<> ();
        myVotingAdapter = new MyVotingAdapter (this, list);
        candidateRV.setLayoutManager (new LinearLayoutManager (this));
        candidateRV.setAdapter (myVotingAdapter);

        if (FirebaseAuth.getInstance ().getCurrentUser () !=null){
            firebaseFirestore.collection ("VotingCandidate")
                    .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful ()){
                                for (DocumentSnapshot snapshot : task.getResult ()){
                                    list.add (new CandidateModel (
                                            snapshot.getString ("Name"),
                                            snapshot.getString ("candidateID"),
                                            snapshot.getString ("department"),
                                            snapshot.getString ("post"),
                                            snapshot.getString ("about"),
                                            snapshot.getId ()
                                    ));
                                }
                                myVotingAdapter.notifyDataSetChanged ();
                            }else
                            {
                                Toast.makeText (ViewCandidates.this, "Not found", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart ();
        String uid = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();

        FirebaseFirestore.getInstance ().collection ("student")
                .document (uid)
                .get ()
                .addOnCompleteListener (new OnCompleteListener<DocumentSnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String finish = task.getResult ().getString ("finish");

//                        assert finish != null;
                        if (finish !=null) {
                            if (finish.equals ("voted")) {
                                Toast.makeText (ViewCandidates.this, "Vote counted already", Toast.LENGTH_SHORT).show ();
                                startActivity (new Intent (ViewCandidates.this, ResultActivity.class));
                                finish ();
                            }
                        }
                    }
                });
    }
}