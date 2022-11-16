package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateCandidate extends AppCompatActivity {

    private EditText candidateName, department, candidateId, about;
    private Button submit;
    private String [] candPosition = {"President", "Vice-President"};
    private Spinner canSpin;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_candidate);


        candidateName = findViewById (R.id.candidateName);
        department = findViewById (R.id.department);
        canSpin = findViewById (R.id.candidateSpinner);
        candidateId = findViewById (R.id.candidateNumber);
        submit =findViewById (R.id.registerCandidate);
        about = findViewById (R.id.aboutCandidate);
        ArrayAdapter<String> adapter = new ArrayAdapter<> (this, android.R.layout.simple_dropdown_item_1line, candPosition);
        canSpin.setAdapter (adapter);
        mAuth = FirebaseAuth.getInstance ();
        fStore =FirebaseFirestore.getInstance ();


        submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name = candidateName.getText ().toString ().trim ();
                String cID = candidateId.getText ().toString ().trim ();
                String candidateDep = department.getText ().toString ().trim ();
                String canidatePost = canSpin.getSelectedItem ().toString ();
                String aboutCandidate = about.getText ().toString ();

                if (!TextUtils.isEmpty (name) && !TextUtils.isEmpty (cID) &&!TextUtils.isEmpty (candidateDep) && !TextUtils.isEmpty (canidatePost ) && !TextUtils.isEmpty (aboutCandidate) ){
//                    Toast.makeText (CreateCandidate.this, " ", Toast.LENGTH_SHORT).show ();
                }else {
                    Toast.makeText (CreateCandidate.this, "Enter Details", Toast.LENGTH_SHORT).show ();
                }

                DocumentReference documentReference = fStore.collection ("VotingCandidate").document ();
                Map<String, Object> candidate = new HashMap<> ();
                candidate.put ("Name", name);
                candidate.put ("candidateID" , cID);
                candidate.put ("department", candidateDep );
                candidate.put ("post", canidatePost);
                candidate.put ("about", aboutCandidate);
                documentReference.set (candidate).addOnSuccessListener (new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText (CreateCandidate.this, "Teacher details for "+ name +" Created", Toast.LENGTH_SHORT).show ();

                    }
                });
            }
        });


    }
}