package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Voting_Activity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView candidate_name, candidate_ID, candidate_department, candidate_Post, candidate_about;
    private Button VoteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_voting2);

        candidate_ID = findViewById (R.id.textView2);
        candidate_name = findViewById (R.id.textView3);
        candidate_department = findViewById (R.id.textView4);
        candidate_Post = findViewById (R.id.textView5);
        candidate_about =findViewById (R.id.textView6);
        VoteBtn = findViewById (R.id.button2);
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

            VoteBtn.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    String finish = "voted";
                    Map<String, Object> userMap = new HashMap<> ();
                    userMap.put ("finish", finish);
                    userMap.put ("Candidate Name", nme);

                    firebaseFirestore.collection ("student/"+uid+"/student's votes"  )
                            .document (id)
                            .set (userMap)
                            .isSuccessful ();

                    Map<String, Object> candidateMap = new HashMap<> ();

                    candidateMap.put ("timestamp", FieldValue.serverTimestamp ());

                    candidateMap.put ("VoterEmail", firebaseAuth.getCurrentUser ().getEmail ());

                    firebaseFirestore.collection ("VotingCandidate/"+id+"/Vote" )
                            .document (uid)
                            .set (candidateMap)
                            .addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful ()) {
                                        startActivity (new Intent (Voting_Activity.this, ResultActivity.class));
                                        finish ();
                                    }else {
                                        Toast.makeText (Voting_Activity.this, "Voted successfully", Toast.LENGTH_SHORT).show ();
                                    }
                                }
                            });
                }
            });

    }

}