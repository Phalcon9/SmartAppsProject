package com.example.smartbookapps.viewdatails;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbookapps.R;
import com.example.smartbookapps.adapter.MyStudentAdapter;
import com.example.smartbookapps.models.StudentUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
        studentUsersArrayList = new ArrayList<> ();
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
            if (studentUsers.getEmail ().toLowerCase ().contains (text.toLowerCase ()) ){
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
                    .get ().addOnCompleteListener (task -> {
                        if (task.isSuccessful ()){
                            for (DocumentSnapshot snapshot : task.getResult ()){
                                studentUsersArrayList.add (new StudentUsers (
                                        snapshot.getString ("fName"),
                                        snapshot.getString ("Email"),
                                        snapshot.getString ("matricNum"),
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
                    });

        return;
    }

    }
}