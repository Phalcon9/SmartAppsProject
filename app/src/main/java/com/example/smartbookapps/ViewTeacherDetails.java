package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_teacher);

        recyclerView = findViewById (R.id.recylerViewT);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));

        searchView = findViewById (R.id.search_barT);
        searchView.clearFocus ();
        db = FirebaseFirestore.getInstance ();
        teacherUsersArrayList = new ArrayList<TeacherUsers> ();
        myTeacherAdapter = new MyTeacherAdapter (ViewTeacherDetails.this, teacherUsersArrayList);

        recyclerView.setAdapter (myTeacherAdapter);
        EventChangeListener();

        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<TeacherUsers> filteredTeacherList = new ArrayList<> ();
        for (TeacherUsers teacherUsers : teacherUsersArrayList){
            if (teacherUsers.getEmail ().toLowerCase ().contains (text.toLowerCase ()) ){
                filteredTeacherList.add (teacherUsers);
            }
        }
        if (filteredTeacherList.isEmpty ()){
            Toast.makeText (this, "Teacher Not found", Toast.LENGTH_SHORT).show ();
        } else {
            myTeacherAdapter.setTeacherFilteredList (filteredTeacherList);
        }
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
                                        snapshot.getString ("employeeNum"),
                                        snapshot.getString ("phoneNum"),
                                        snapshot.getString ("category"),
                                        snapshot.getId ()
                                ));
                            }
                            myTeacherAdapter.notifyDataSetChanged ();
                        }
                        else
                        {
                            Toast.makeText (ViewTeacherDetails.this, "Not found", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });


    }
}