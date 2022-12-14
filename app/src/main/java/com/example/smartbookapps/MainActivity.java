package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    boolean valid = true;
    private EditText username, password;


    private Button login;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


        fAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();
        fUser = fAuth.getCurrentUser ();

        username = findViewById (R.id.UserName);
        password = findViewById (R.id.password);
        login = findViewById (R.id.login);


        login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                checkField(username);
                checkField(password);

                if (valid){
                    fAuth.signInWithEmailAndPassword (username.getText ().toString (), password.getText ().toString () ).addOnSuccessListener (new OnSuccessListener<AuthResult> () {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            checkUserAccessLevel(authResult.getUser ().getUid ());

                            Toast.makeText (MainActivity.this, "Success", Toast.LENGTH_SHORT).show ();
                        }
                    }).addOnFailureListener (new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText (MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show ();
                        }
                    });
                }

            }
        });


    }


        private void checkUserAccessLevel(String uid) {
            DocumentReference df = fStore.collection ("Student").document (uid);
            df.get ().addOnSuccessListener (new OnSuccessListener<DocumentSnapshot> () {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if (documentSnapshot.getString ("category") != "teacher"){
                        startActivity (new Intent (getApplicationContext (), StudentPanel.class));
                        finish ();
                    }
//                }
            });
                    }


    private boolean checkField(EditText textField) {

        if (textField.getText ().toString ().isEmpty ()){
            textField.setError ("Error");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

}