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

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyAttendanceViewHolder> {
    Context context;
    List<StudentUsers> list;

    private FirebaseFirestore firebaseFirestore;

    public AttendanceAdapter(Context context,  List<StudentUsers> list) {
        this.context = context;
        this.list = list;
        firebaseFirestore = FirebaseFirestore.getInstance ();
    }

    @NonNull
    @Override
    public AttendanceAdapter.MyAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.student_attendance_shit, parent, false);
        return new MyAttendanceViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.MyAttendanceViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.studName.setText ("Students phone:  "+list.get (position).getfName ());
        holder.studMatric.setText ("Students Email :"+list.get (position).getMatricNum ());
        holder.studPhone.setText ("Category  :" + list.get (position).getPhoneNum ());
        holder.studEmail.setText ("Students Name :" + list.get (position).getEmail ());
        holder.studCate.setText ("Students matriculation : " + list.get (position).getCategory ());


        firebaseFirestore.collection ("student/"+list.get (position).getId ()+"/student's attendance")
                .addSnapshotListener (new EventListener<QuerySnapshot> () {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if (!documentSnapshots.isEmpty ()){
                            int count = documentSnapshots.size ();
                            StudentUsers studentUsers = list.get (position);
                            studentUsers.setCount (count);
                            list.set (position, studentUsers);
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

    public class MyAttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView studName, studMatric, studAttended, studEmail, studPhone, studCate;
        public MyAttendanceViewHolder(@NonNull View itemView) {
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
