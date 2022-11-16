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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CourseAdapterTeacher  extends RecyclerView.Adapter<CourseAdapterTeacher.MyCourseViewHolderTeacher>{
    Context context;
    List<CourseModel> list;
    private FirebaseFirestore firebaseFirestore;

    public CourseAdapterTeacher(Context context, List<CourseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CourseAdapterTeacher.MyCourseViewHolderTeacher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.list_teacher_course, parent, false);
        return new MyCourseViewHolderTeacher (view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapterTeacher.MyCourseViewHolderTeacher holder, @SuppressLint("RecyclerView") int position) {
        holder.cName.setText ("Course Name :  "+list.get (position).getCourseName ());
        holder.cVenue.setText ("Course Venue :  "+list.get (position).getVenue ());
        holder.cTime.setText ("Course Time :  "+list.get (position).getTime ());
        holder.cDepartment.setText ("Department : "+ list.get (position).getDeparment ());
        holder.signA.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, AttendanceAtivityTeacher.class);
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

    public class MyCourseViewHolderTeacher extends RecyclerView.ViewHolder {
        private TextView cName, cVenue, cTime, cDepartment;
        private Button signA;
        public MyCourseViewHolderTeacher(@NonNull View itemView) {
            super (itemView);
            cName = itemView.findViewById (R.id.classSet);
            cVenue =itemView.findViewById (R.id.classVenue);
            cTime = itemView.findViewById (R.id.Classtime);
            cDepartment = itemView.findViewById (R.id.ClassDepart);
            signA = itemView.findViewById (R.id.signIN);
        }
    }
}
