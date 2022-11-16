package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DeleteVoterActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView candidate_name, candidate_ID, candidate_department, candidate_Post, candidate_about;
    private Button DBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_delete_voter);
        candidate_ID = findViewById (R.id.textView2);
        candidate_name = findViewById (R.id.textView3);
        candidate_department = findViewById (R.id.textViewD);
        candidate_Post = findViewById (R.id.textView5);
        candidate_about =findViewById (R.id.textView6);
        DBtn = findViewById (R.id.deleteS);
        firebaseFirestore = FirebaseFirestore.getInstance ();
        firebaseAuth =FirebaseAuth.getInstance ();
        Intent intent =getIntent ();
        String cid = intent.getStringExtra ("candidateID");
        String nme = intent.getStringExtra ("Name");
        String dpm = intent.getStringExtra ("department");
        String pst = intent.getStringExtra ("post");
        String abt = intent.getStringExtra ("about");
        String id = intent.getStringExtra ("id");

        candidate_ID.setText (cid);
        candidate_name.setText (nme);
        candidate_department.setText (dpm);
        candidate_Post.setText (pst);
        candidate_about.setText (abt);

        String uid = Objects.requireNonNull (firebaseAuth.getCurrentUser ()).getUid ();

        DBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection ("student/"+uid+"/student's votes"  )
                        .document (id)
                        .delete ()
                        .isSuccessful ();

                firebaseFirestore.collection ("VotingCandidate/"+id+"/Vote" )
                        .document (uid)
                        .delete ().isSuccessful ();
            }
        });


    }
}