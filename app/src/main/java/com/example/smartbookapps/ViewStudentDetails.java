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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<StudentUsers> studentUsersArrayList;
    MyStudentAdapter myStudentAdapter;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_student);

        recyclerView = findViewById (R.id.recylerView);
        searchView = findViewById (R.id.search_bar);
        searchView.clearFocus ();
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));

        db = FirebaseFirestore.getInstance ();
        studentUsersArrayList = new ArrayList<StudentUsers> ();
        myStudentAdapter = new MyStudentAdapter (ViewStudentDetails.this, studentUsersArrayList);
        firebaseAuth = FirebaseAuth.getInstance ();
        recyclerView.setAdapter (myStudentAdapter);
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
        ArrayList<StudentUsers> filteredStudentsList = new ArrayList<> ();
        for (StudentUsers studentUsers : studentUsersArrayList){
            if (studentUsers.getfName ().toLowerCase ().contains (text.toLowerCase ()) || studentUsers.getMatricNum ().contains (text.toLowerCase ())){
                filteredStudentsList.add (studentUsers);
            }
        }
        if (filteredStudentsList.isEmpty ()){
            Toast.makeText (this, "Student Not found", Toast.LENGTH_SHORT).show ();
        } else {
            myStudentAdapter.setStudentFilteredList (filteredStudentsList);
        }
    }

    private void EventChangeListener() {
        if (FirebaseAuth.getInstance ().getCurrentUser () !=null){
            db.collection ("student")
                    .get ().addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful ()){

                                for (DocumentSnapshot snapshot : task.getResult ()){
                                    studentUsersArrayList.add (new StudentUsers (
                                            snapshot.getString ("fName"),
                                            snapshot.getString ("Email"),
                                            snapshot.getString ("matricNum"),
                                            snapshot.getString ("password"),
                                            snapshot.getString ("phoneNum"),
                                            snapshot.getString ("category"),
                                            snapshot.getId ()
                                    ));
                                }
                                myStudentAdapter.notifyDataSetChanged ();
                            }else
                            {
                                Toast.makeText (ViewStudentDetails.this, "Not found", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });

        return;
    }

    }
}