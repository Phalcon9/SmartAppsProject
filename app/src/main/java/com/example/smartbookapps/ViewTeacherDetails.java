package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ViewTeacherDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TeacherUsers> teacherUsersArrayList;
    MyTeacherAdapter myTeacherAdapter;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_teacher);

        recyclerView = findViewById (R.id.recylerViewT);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));

        db = FirebaseFirestore.getInstance ();
        teacherUsersArrayList = new ArrayList<TeacherUsers> ();
        myTeacherAdapter = new MyTeacherAdapter (ViewTeacherDetails.this, teacherUsersArrayList);

        recyclerView.setAdapter (myTeacherAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {

        db.collection ("teacher")
                .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful ()){

                            for (DocumentSnapshot snapshot : task.getResult ()){
                                teacherUsersArrayList.add (new TeacherUsers (
                                        snapshot.getString ("Name"),
                                        snapshot.getString ("Email"),
                                        snapshot.getString ("password"),
                                        snapshot.getString ("phoneNum"),
                                        snapshot.getString ("category"),
                                        snapshot.getId ()
                                ));
                            }
                            myTeacherAdapter.notifyDataSetChanged ();
                        }else
                        {
                            Toast.makeText (ViewTeacherDetails.this, "Not found", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });
//        db.collection ("teacher").orderBy ("employeeNum", Query.Direction.ASCENDING)
//                .addSnapshotListener (new EventListener<QuerySnapshot> () {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error != null){
//                            Log.e("Firestore error", error.getMessage ());
//                            return;
//                        }
//                        for (DocumentChange dc : value.getDocumentChanges ()){
//                            if (dc.getType ()== DocumentChange.Type.ADDED){
//                                teacherUsersArrayList.add (dc.getDocument ().toObject (TeacherUsers.class));
//                            }
//                            else if (dc.getType ()== DocumentChange.Type.REMOVED){
//                                teacherUsersArrayList.remove (dc.getDocument ().toObject (TeacherUsers.class));
//                            }
//                            myTeacherAdapter.notifyDataSetChanged ();
//                        }
//
//                    }
//                });
    }
}