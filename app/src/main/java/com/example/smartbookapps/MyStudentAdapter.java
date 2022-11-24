package com.example.smartbookapps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyStudentAdapter extends RecyclerView.Adapter<MyStudentAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentUsers> studentUsersArrayList;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public void setStudentFilteredList(ArrayList<StudentUsers> studentFilteredList){
        this.studentUsersArrayList = studentFilteredList;
        notifyDataSetChanged ();
    }

    public MyStudentAdapter(Context context, ArrayList<StudentUsers> studentUsersArrayList) {
        this.context = context;
        this.studentUsersArrayList = studentUsersArrayList;
        firebaseFirestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
    }

    @NonNull
    @Override
    public MyStudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from (context).inflate (R.layout.item, parent ,false);
        return new MyViewHolder (v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyStudentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.firstName1.setText ("Matriculation Number  : " + studentUsersArrayList.get(position).fName);
        holder.matricNum1.setText ("Phone Number: "+ studentUsersArrayList.get (position).matricNum);
        holder.category1.setText (" Category : "+studentUsersArrayList.get (position).category);
        holder.phone.setText ("Category : "+studentUsersArrayList.get (position).phoneNum);
        holder.email1.setText (" Name: "+studentUsersArrayList.get (position).Email);

        holder.delete.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder (context)
                        .setTitle ("Delete Student Record")
                        .setMessage ("Are you sure you want to delete?")
                        .setIcon (R.drawable.ic_baseline_delete_24)
                        .setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               firebaseFirestore.collection ("student").document (studentUsersArrayList.get (position).getId () )
                                        .delete ()
                                       .addOnCompleteListener (new OnCompleteListener<Void> () {
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
        return studentUsersArrayList.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView matricNum1, firstName1, phone, category1, email1;
        ImageView  delete;

        public MyViewHolder(@NonNull View itemView) {
            super (itemView);
            firstName1 = itemView.findViewById (R.id.namess);
            matricNum1 = itemView.findViewById (R.id.matricNum);
            phone = itemView.findViewById (R.id.phoneNum);
            category1 = itemView.findViewById (R.id.cate);
            email1 = itemView.findViewById (R.id.email);
            delete = itemView.findViewById (R.id.delete);
        }
    }
}
