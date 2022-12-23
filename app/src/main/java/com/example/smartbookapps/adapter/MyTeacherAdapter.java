package com.example.smartbookapps.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbookapps.R;
import com.example.smartbookapps.models.TeacherUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MyTeacherAdapter extends RecyclerView.Adapter<MyTeacherAdapter.MyTeacherViewHolder> {

    Context context;
    ArrayList<TeacherUsers> teacherUsersArrayList;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public void setTeacherFilteredList(ArrayList<TeacherUsers> teacherFilteredList){
        this.teacherUsersArrayList = teacherFilteredList;
        notifyDataSetChanged ();
    }
    public  MyTeacherAdapter(Context context, ArrayList<TeacherUsers> teacherUsersArrayList){
        this.context = context;
        this.teacherUsersArrayList = teacherUsersArrayList;
        firebaseFirestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
    }

    @NonNull
    @Override
    public MyTeacherAdapter.MyTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from (context).inflate (R.layout.teachitem, parent, false);
        return new MyTeacherViewHolder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTeacherAdapter.MyTeacherViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TeacherUsers teacherUsers = teacherUsersArrayList.get (position);
        holder.fullName.setText ("Employee Number : "+teacherUsers.getName ());
        holder.employeeId.setText ("Phone Number : "+teacherUsers.getEmployeeNum ());
        holder.email.setText ("FullName  : " + teacherUsers.getEmail ());
        holder.phone.setText ("Category : "+teacherUsers.getPhoneNum ());
        holder.category.setText (" Email: "+teacherUsers.getCategory ());

        holder.delete.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder (context)
                        .setTitle ("Delete Teacher Record")
                        .setMessage ("Are you sure you want to delete?")
                        .setIcon (R.drawable.ic_baseline_delete_24)
                        .setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String uid = Objects.requireNonNull (firebaseAuth.getCurrentUser ()).getUid ();
                                firebaseFirestore.collection ("teacher/"+uid+"/teacher's attendance"  ).get ()
                                        .addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot snapshot : task.getResult ()){

                                                    firebaseFirestore.collection ("teacher/"+uid+"/teacher's attendance"  ).document (snapshot.getId ()).delete ();
                                                }
                                            }
                                        });
                                firebaseFirestore.collection ("teacher").document (teacherUsersArrayList.get (position).getId () )
                                        .delete ().addOnCompleteListener (new OnCompleteListener<Void> () {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful ()){
                                                    notifyDataSetChanged ();
                                                    notifyItemRemoved (position);
                                                    Toast.makeText (context, "Deleted...", Toast.LENGTH_SHORT).show ();
                                                }
                                                else {
                                                    Toast.makeText (context, "Error" + task.getException ().getMessage (), Toast.LENGTH_SHORT).show ();
                                                }
                                            }
                                        });
                            }
                        })
                        .setNegativeButton ("No", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show ();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherUsersArrayList.size ();
    }

    public static class MyTeacherViewHolder extends RecyclerView.ViewHolder{
        TextView fullName, employeeId, phone, category, email;
        ImageView delete;
        public MyTeacherViewHolder(@NonNull View itemView) {
            super (itemView);
            fullName = itemView.findViewById (R.id.namess1);
            employeeId = itemView.findViewById (R.id.employeeNum1);
            phone = itemView.findViewById (R.id.phoneNum);
            category = itemView.findViewById (R.id.cate);
            email = itemView.findViewById (R.id.email);
            delete = itemView.findViewById (R.id.deleteBtn);
        }
    }
}
