package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentReg extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    String studentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_student_reg);



        final EditText fullname = findViewById (R.id.fullname);
        final EditText category = findViewById (R.id.categorgy);
        final EditText matric = findViewById (R.id.matricNumber);
        final EditText email = findViewById (R.id.email);
        final EditText phone = findViewById (R.id.phone);
        final EditText password = findViewById (R.id.password);
        final EditText passwordC = findViewById (R.id.ConfirmPassword);
        final Button regButton = findViewById (R.id.registerButton);
        mAuth = FirebaseAuth.getInstance ();
        fStore =FirebaseFirestore.getInstance ();
        fUser = mAuth.getCurrentUser ();

        regButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name = fullname.getText ().toString ().trim ();
                String categors = category.getText ().toString ().trim ();
                String matricNum = matric.getText ().toString ().trim ();
                String Email = email.getText ().toString ().trim ();
                String phone1 = phone.getText ().toString ().trim ();
                String password1 = password.getText ().toString ().trim ();
                String password2 = passwordC.getText ().toString ().trim ();

                if (!TextUtils.isEmpty (name) && !TextUtils.isEmpty (categors) && !TextUtils.isEmpty (matricNum) && !TextUtils.isEmpty (Email) && Patterns.EMAIL_ADDRESS.matcher (Email).matches () &&!TextUtils.isEmpty (phone1) &&  !TextUtils.isEmpty (password1) && !TextUtils.isEmpty (password2) ){
//                    Toast.makeText (StudentReg.this, "No empty please", Toast.LENGTH_SHORT).show ();
                }else {
                    Toast.makeText (StudentReg.this, "Enter Details", Toast.LENGTH_SHORT).show ();
                }
                mAuth.createUserWithEmailAndPassword (Email, password1).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText (StudentReg.this, "User Created", Toast.LENGTH_SHORT).show ();
                            studentID = mAuth.getCurrentUser ().getUid ();
                            DocumentReference documentReference = fStore.collection ("student").document (studentID);
                            Map<String, Object> student = new HashMap<> ();
                            student.put ("fName", name);
                            student.put ("category", categors );
                            student.put ("matricNum", matricNum);
                            student.put ("Email", Email);
                            student.put ("phoneNum", phone1);
                            student.put ("password", password1);
                            documentReference.set (student).addOnSuccessListener (new OnSuccessListener<Void> () {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText (StudentReg.this, "Student details for "+ name +" Created", Toast.LENGTH_SHORT).show ();
                                }
                            });
//                            verifyEmail();
                        }
                        else {
                            Toast.makeText (StudentReg.this, "Wrong input Try again", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });
            }
        });
    }
//
//    private void createUser(String email, String password1) {
//        .addOnFailureListener (new OnFailureListener () {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText (StudentReg.this, "Something Went Wrong", Toast.LENGTH_SHORT).show ();
//            }
//        });
//    }

//    private void verifyEmail() {
//        FirebaseUser user = mAuth.getCurrentUser ();
//        if (user != null){
//            user.sendEmailVerification ().addOnCompleteListener (new OnCompleteListener<Void> () {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful ()){
//                        Toast.makeText (StudentReg.this, "Email sent", Toast.LENGTH_SHORT).show ();
//                        FirebaseAuth.getInstance ().signOut ();
//                        startActivity (new Intent (StudentReg.this, ManageStudent.class));
//                        finish ();
//                    }else
//                    {
//                        finish ();
//                    }
//                }
//            });
//        }
//    }
}