package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.smartbookapps.adapter.AttendanceResultAdapterTeach;
import com.example.smartbookapps.models.TeacherUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AttendTeachActivity extends AppCompatActivity {
    private RecyclerView attendanceRV;
    private List<TeacherUsers> list;
    private AttendanceResultAdapterTeach adapter;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_attend_teach);

        attendanceRV = findViewById (R.id.attendance_rv1);
        firebaseFirestore = FirebaseFirestore.getInstance ();

        list = new ArrayList<> ();
        adapter = new AttendanceResultAdapterTeach (AttendTeachActivity.this, list);
        attendanceRV.setLayoutManager (new LinearLayoutManager (AttendTeachActivity.this));
        attendanceRV.setAdapter (adapter);

        if (FirebaseAuth.getInstance ().getCurrentUser () !=null){
            firebaseFirestore.collection ("teacher")
                    .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful ()){
                                for (DocumentSnapshot snapshot : task.getResult ()){
                                    list.add (new TeacherUsers (
                                            snapshot.getString ("Name"),
                                            snapshot.getString ("employeeNum"),
                                            snapshot.getString ("phoneNum"),
                                            snapshot.getString ("Email"),
                                            snapshot.getString ("category"),
                                            snapshot.getId ()
                                    ));
                                }
                                adapter.notifyDataSetChanged ();
                            }else
                            {
                                Toast.makeText (AttendTeachActivity.this, "Not found", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
        }
    }
}