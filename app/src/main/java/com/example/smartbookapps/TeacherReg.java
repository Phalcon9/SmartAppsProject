package com.example.smartbookapps;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeacherReg extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String teacherID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_teacher_reg);

        final EditText fullname = findViewById (R.id.fullname);
        final EditText category = findViewById (R.id.categorgy);
        final EditText employeeID = findViewById (R.id.teacherIden);
        final EditText email = findViewById (R.id.email);
        final EditText phone = findViewById (R.id.phone);
        final EditText password = findViewById (R.id.password);
        final EditText passwordC = findViewById (R.id.ConfirmPassword);
        final TextView isTeacher = findViewById (R.id.isTeach);
        final Button regButton = findViewById (R.id.registerButton);

        mAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();

        regButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name = fullname.getText ().toString ().trim ();
                String categors = category.getText ().toString ().trim ();
                String employeeNum = employeeID.getText ().toString ().trim ();
                String Email = email.getText ().toString ().trim ();
                String phone1 = phone.getText ().toString ().trim ();
                Boolean isTeacher1 = Boolean.valueOf (isTeacher.getText ().toString ().trim ());
                String password1 = password.getText ().toString ().trim ();
                String password2 = passwordC.getText ().toString ().trim ();

                if (!TextUtils.isEmpty (name) && !TextUtils.isEmpty (categors) && !TextUtils.isEmpty (employeeNum) && !TextUtils.isEmpty (Email) && Patterns.EMAIL_ADDRESS.matcher (Email).matches () &&!TextUtils.isEmpty (phone1) &&  !TextUtils.isEmpty (password1) && !TextUtils.isEmpty (password2) ){
//                    Toast.makeText (TeacherReg.this, "No empty please", Toast.LENGTH_SHORT).show ();
                }else {
                    Toast.makeText (TeacherReg.this, "Enter Details", Toast.LENGTH_SHORT).show ();
                }

                mAuth.createUserWithEmailAndPassword (Email, password1).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText (TeacherReg.this, "User Created", Toast.LENGTH_SHORT).show ();
                            teacherID = mAuth.getCurrentUser ().getUid ();
                            DocumentReference documentReference = fStore.collection ("teacher").document (teacherID);
                            Map<String, Object> teacher = new HashMap<> ();
                            teacher.put ("Name", name);
                            teacher.put ("category", categors );
                            teacher.put ("employeeNum", employeeNum);
                            teacher.put ("Email", Email);
                            teacher.put ("isTeacher", isTeacher1);
                            teacher.put ("phoneNum", phone1);
                            teacher.put ("password", password1);

                            documentReference.set (teacher).addOnSuccessListener (new OnSuccessListener<Void> () {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText (TeacherReg.this, "Teacher details for "+ name +" Created", Toast.LENGTH_SHORT).show ();
                                }
                            });
//                            verifyEmail();
                        }
                        else {
                            Toast.makeText (TeacherReg.this, "Wrong input Try again", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });
            }
        });


    }
}