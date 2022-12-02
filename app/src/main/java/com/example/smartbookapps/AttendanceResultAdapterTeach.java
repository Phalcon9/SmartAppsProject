package com.example.smartbookapps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AttendanceResultAdapterTeach extends RecyclerView.Adapter<AttendanceResultAdapterTeach.MyAttendanceResutlTeachViewHolder>{
    Context context;
    List<TeacherUsers> list;

    private FirebaseFirestore firebaseFirestore;
    public AttendanceResultAdapterTeach(Context context, List<TeacherUsers> list) {
        this.context = context;
        this.list = list;
        firebaseFirestore = FirebaseFirestore.getInstance ();
    }

    @NonNull
    @Override
    public AttendanceResultAdapterTeach.MyAttendanceResutlTeachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.teacher_attendance_sheet, parent, false);
        return new MyAttendanceResutlTeachViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceResultAdapterTeach.MyAttendanceResutlTeachViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.studName.setText ("Teacher's phone:  "+list.get (position).getName ());
        holder.studMatric.setText ("Teacher's Email :"+list.get (position).getEmployeeNum ());
        holder.studPhone.setText ("Category  :" + list.get (position).getPhoneNum ());
        holder.studEmail.setText ("Teacher's Name :" + list.get (position).getEmail ());
        holder.studCate.setText ("Teacher's Employee Number : " + list.get (position).getCategory ());


        firebaseFirestore.collection ("student/"+list.get (position).getId ()+"/teacher's attendance")
                .addSnapshotListener (new EventListener<QuerySnapshot> () {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if (!documentSnapshots.isEmpty ()){
                            int count = documentSnapshots.size ();
                            TeacherUsers teacherUsers = list.get (position);
                            teacherUsers.setCount (count);
                            list.set (position, teacherUsers);
                            notifyDataSetChanged ();
                        }
                    }
                });
        holder.studAttended.setText ("Attendance :"+list.get (position).getCount ());

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class MyAttendanceResutlTeachViewHolder extends RecyclerView.ViewHolder {
        TextView studName, studMatric, studAttended, studEmail, studPhone, studCate;
        public MyAttendanceResutlTeachViewHolder(@NonNull View itemView) {
            super (itemView);
            studName = itemView.findViewById (R.id.student_name);
            studMatric = itemView.findViewById (R.id.student_matric);
            studAttended = itemView.findViewById (R.id.class_attended);
            studEmail = itemView.findViewById (R.id.student_email);
            studPhone = itemView.findViewById (R.id.student_phone);
            studCate =itemView.findViewById (R.id.student_cate);
        }
    }
}
