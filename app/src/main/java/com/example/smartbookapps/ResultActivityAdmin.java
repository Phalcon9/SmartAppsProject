package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private MyVotingResultAdapterAdmin adapter;
    FirebaseFirestore firebaseFirestore;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_result_admin);

        resultRV = findViewById (R.id.result_rv);
        resultRV.setLayoutManager (new LinearLayoutManager (ResultActivityAdmin.this));
        resultRV.setAdapter (adapter);
        adapter = new MyVotingResultAdapterAdmin (ResultActivityAdmin.this, list);
        firebaseFirestore = FirebaseFirestore.getInstance ();

        list = new ArrayList<> ();

        showData ();
        
        searchView = findViewById (R.id.search_barV);
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        List<CandidateModel> filterCanditateList = new ArrayList<> ();
        for (CandidateModel candidateModel: list){
            if (candidateModel.getName ().toLowerCase ().contains (text.toLowerCase ())){
                filterCanditateList.add (candidateModel);
            }        
        }
        if (filterCanditateList.isEmpty ()){
            Toast.makeText (this, "Candidate not found", Toast.LENGTH_SHORT).show ();
        }
        else {
            adapter.setFilteredCandidateList (filterCanditateList);
        }
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