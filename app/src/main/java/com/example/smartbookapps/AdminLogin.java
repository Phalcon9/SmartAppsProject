package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class AdminLogin extends AppCompatActivity {
    boolean valid = true;
    EditText userNameAdmin, passwordAdmin;
    Button loginAdmin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_login);


        userNameAdmin = findViewById (R.id.userNameAd);
        passwordAdmin = findViewById (R.id.passwordAd);
        fAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();
        fUser = fAuth.getCurrentUser ();
        loginAdmin = findViewById (R.id.loginAd);

        loginAdmin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                checkField(userNameAdmin);
                checkField(passwordAdmin);

                if (valid){

                    fAuth.signInWithEmailAndPassword (userNameAdmin.getText ().toString (), passwordAdmin.getText ().toString () ).addOnSuccessListener (new OnSuccessListener<AuthResult> () {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            checkUserAccessLevel(authResult.getUser ().getUid ());

                            Toast.makeText (AdminLogin.this, "Success", Toast.LENGTH_SHORT).show ();
                        }
                    }).addOnFailureListener (new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText (AdminLogin.this, "Wrong", Toast.LENGTH_SHORT).show ();
                        }
                    });
                }

            }

        });
        }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection ("Admin").document (uid);
        df.get ().addOnSuccessListener (new OnSuccessListener<DocumentSnapshot> () {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//               if (documentSnapshot.getString ("category") != "studentt"){
                   startActivity (new Intent (getApplicationContext (), AdminActivity.class));
                   finish ();
               }
//            }
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