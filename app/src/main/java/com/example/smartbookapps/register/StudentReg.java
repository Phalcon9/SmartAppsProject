package com.example.smartbookapps.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbookapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class StudentReg extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    String studentID;
    EditText editText;
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
        final TextView isStudent = findViewById (R.id.isStudent);
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
                Boolean isStudent1 = Boolean.valueOf (isStudent.getText ().toString ().trim ());
                String password2 = passwordC.getText ().toString ().trim ();

                if (!TextUtils.isEmpty (name) && !TextUtils.isEmpty (categors) && !TextUtils.isEmpty (matricNum) && !TextUtils.isEmpty (Email) && Patterns.EMAIL_ADDRESS.matcher (Email).matches () &&!TextUtils.isEmpty (phone1) &&  !TextUtils.isEmpty (password1) && !TextUtils.isEmpty (password2) ){
//                    Toast.makeText (StudentReg.this, "No empty please", Toast.LENGTH_SHORT).show ();
                }else {
                    Toast.makeText (StudentReg.this, "Enter Details", Toast.LENGTH_SHORT).show ();
                }

                mAuth.fetchSignInMethodsForEmail (Email).addOnCompleteListener (new OnCompleteListener<SignInMethodQueryResult> () {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean check = !task.getResult ().getSignInMethods ().isEmpty ();

                        if (!check){
                            fStore.collection ("student").whereEqualTo ("matricNum", matricNum).get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    boolean check2 = !task.getResult ().getDocuments ().isEmpty ();
                                    if (!check2){
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
                                                    student.put ("isStudent", isStudent1);
                                                    student.put ("password", password1);
                                                    documentReference.set (student).addOnSuccessListener (new OnSuccessListener<Void> () {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            fullname.setText (" ");
                                                            category.setText (" ");
                                                            matric.setText (" ");
                                                            email.setText (" ");
                                                            phone.setText (" ");
                                                            password.setText (" ");
                                                            passwordC.setText (" ");
                                                            Toast.makeText (StudentReg.this, "Student details for "+ name +" Created", Toast.LENGTH_SHORT).show ();
                                                        }
                                                    });

                                                    fStore.collection ("student").document (studentID).get ().addOnCompleteListener (new OnCompleteListener<DocumentSnapshot> () {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.getResult ().exists ()){
                                                                Toast.makeText (StudentReg.this, "Record stored", Toast.LENGTH_SHORT).show ();
                                                            }
                                                        }
                                                    });
                                                }
                                                else {
                                                    Toast.makeText (StudentReg.this, "Wrong input Try again", Toast.LENGTH_SHORT).show ();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText (StudentReg.this, "Matric Number Already exists", Toast.LENGTH_SHORT).show ();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText (StudentReg.this, "User details already Exist", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });


            }
        });
    }
}