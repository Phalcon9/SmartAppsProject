package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class ClassTeacherActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<CourseModel> courseModelArrayList;
    CourseAdapterTeacher courseAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_class_teacher);

        courseModelArrayList =new ArrayList<> ();
        recyclerView = findViewById (R.id.recylerViewT);
        db = FirebaseFirestore.getInstance ();
        courseAdapter = new CourseAdapterTeacher (this, courseModelArrayList);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        recyclerView.setAdapter (courseAdapter);

        if (FirebaseAuth.getInstance ().getCurrentUser () !=null){
            db.collection ("Classes")
                    .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful ()){
                                for (DocumentSnapshot snapshot : task.getResult ()){
                                    courseModelArrayList.add (new CourseModel (
                                            snapshot.getString ("CourseName"),
                                            snapshot.getString ("Venue"),
                                            snapshot.getString ("Time"),
                                            snapshot.getString ("Department"),
                                            snapshot.getId ()

                                    ));
                                }
                                courseAdapter.notifyDataSetChanged ();
                            }else
                            {
                                Toast.makeText (ClassTeacherActivity.this, "Not found", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }


    }
}