package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ResultActivityAdmin extends AppCompatActivity {
    private RecyclerView resultRV;
    private List<CandidateModel> list;
    //    private  MyVotingAdapter adapter;
    private MyVotingResultAdapterAdmin adapter;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_result_admin);


        resultRV = findViewById (R.id.result_rv);
        firebaseFirestore = FirebaseFirestore.getInstance ();

        list = new ArrayList<> ();
        adapter = new MyVotingResultAdapterAdmin (ResultActivityAdmin.this, list);
        resultRV.setLayoutManager (new LinearLayoutManager (ResultActivityAdmin.this));
        resultRV.setAdapter (adapter);
        showData ();


    }
    public void showData(){
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
                                adapter.notifyDataSetChanged ();
                            }else
                            {
                                Toast.makeText (ResultActivityAdmin.this, "Not found", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }
        return;
    }
}