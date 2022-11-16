package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherLogin extends AppCompatActivity {

    boolean valid = true;
    EditText userNameTeacher, passwordTeacher;
    Button loginAdmin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_teacher_login);

        userNameTeacher = findViewById (R.id.UserNameTeacher);
        passwordTeacher = findViewById (R.id.passwordT);
        fAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();
        fUser = fAuth.getCurrentUser ();
        loginAdmin = findViewById (R.id.loginT);

        loginAdmin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                    checkField(userNameTeacher);
                    checkField(passwordTeacher);

                    if (valid){

                        fAuth.signInWithEmailAndPassword (userNameTeacher.getText ().toString (), passwordTeacher.getText ().toString () ).addOnSuccessListener (new OnSuccessListener<AuthResult> () {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                checkUserAccessLevel(authResult.getUser ().getUid ());

                                Toast.makeText (TeacherLogin.this, "Success", Toast.LENGTH_SHORT).show ();
                            }
                        }).addOnFailureListener (new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText (TeacherLogin.this, "Wrong", Toast.LENGTH_SHORT).show ();
                            }
                        });
                    }
                }
            });
        }

        private void checkUserAccessLevel(String uid) {
            DocumentReference df = fStore.collection ("Teacher").document (uid);
            df.get ().addOnSuccessListener (new OnSuccessListener<DocumentSnapshot> () {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if (documentSnapshot.getString ("category") != "studentt"){
                        startActivity (new Intent (getApplicationContext (), TeacherPanel.class));
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