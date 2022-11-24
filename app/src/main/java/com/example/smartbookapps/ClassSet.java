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

public class ClassSet extends AppCompatActivity {
    private RecyclerView attendanceRV;
    private List<StudentUsers> list;
    private AttendanceAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_class_set);
        attendanceRV = findViewById (R.id.attendance_rv);
        firebaseFirestore = FirebaseFirestore.getInstance ();

        list = new ArrayList<> ();
        adapter = new AttendanceAdapter (ClassSet.this, list);
        attendanceRV.setLayoutManager (new LinearLayoutManager (ClassSet.this));
        attendanceRV.setAdapter (adapter);

        if (FirebaseAuth.getInstance ().getCurrentUser () !=null){
            firebaseFirestore.collection ("student")
                    .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful ()){
                                for (DocumentSnapshot snapshot : task.getResult ()){
                                    list.add (new StudentUsers (
                                            snapshot.getString ("fName"),
                                            snapshot.getString ("matricNum"),
                                            snapshot.getString ("phoneNum"),
                                            snapshot.getString ("Email"),
//                                            snapshot.getString ("password"),
                                            snapshot.getString ("category"),
                                            snapshot.getId ()
                                    ));
                                }
                                adapter.notifyDataSetChanged ();
                            }else
                            {
                                Toast.makeText (ClassSet.this, "Not found", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }

    }
}