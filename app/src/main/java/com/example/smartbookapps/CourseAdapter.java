package com.example.smartbookapps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyCourseViewHolder>{

    Context context;
    List<CourseModel> list;
    private FirebaseFirestore firebaseFirestore;

    public CourseAdapter(Context context, List<CourseModel> list) {
        this.context = context;
        this.list = list;
        firebaseFirestore =FirebaseFirestore.getInstance ();
    }

    @NonNull
    @Override
    public CourseAdapter.MyCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.class_list, parent, false);
        return new MyCourseViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.MyCourseViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cName.setText ("Course Name :  "+list.get (position).getCourseName ());
        holder.cVenue.setText ("Course Venue :  "+list.get (position).getVenue ());
        holder.cTime.setText ("Course Time :  "+list.get (position).getTime ());
        holder.cDepartment.setText ("Department : "+ list.get (position).getDeparment ());
//        firebaseFirestore.collection ("Classes")
//                .addSnapshotListener (new EventListener<QuerySnapshot> () {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
//                        if (!documentSnapshots.isEmpty ()){
//
////                            int count = documentSnapshots.size ();
//                            CourseModel courseModel = list.get (position);
////                            candidateModel1.setCount (count);
//                            list.set (position,courseModel);
//                            notifyDataSetChanged ();
//                        }
//                    }
//                });
        holder.signA.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, AttendanceActivity.class);
                intent.putExtra ("CourseName", list.get (position).getCourseName ());
                intent.putExtra ("Time", list.get (position).getTime ());
                intent.putExtra ("Venue", list.get (position).getVenue ());
                intent.putExtra ("Department", list.get (position).getDeparment ());
                intent.putExtra ("id", list.get (position).getId ());
                context.startActivity (intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size ();
    }

    public class MyCourseViewHolder extends RecyclerView.ViewHolder {
        private TextView cName, cVenue, cTime, cDepartment;
        private Button signA;
        public MyCourseViewHolder(@NonNull View itemView) {
            super (itemView);
                cName = itemView.findViewById (R.id.classSet);
                cVenue =itemView.findViewById (R.id.classVenue);
                cTime = itemView.findViewById (R.id.Classtime);
                cDepartment = itemView.findViewById (R.id.ClassDepartment);
                signA = itemView.findViewById (R.id.signIN);

        }
    }
}
