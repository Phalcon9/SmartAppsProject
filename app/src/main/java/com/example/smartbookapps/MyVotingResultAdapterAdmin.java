package com.example.smartbookapps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MyVotingResultAdapterAdmin  extends RecyclerView.Adapter<MyVotingResultAdapterAdmin.MyCandidateViewHolderR>{
    Context context;
    List<CandidateModel> list;
    private FirebaseFirestore firebaseFirestore;

    public void setFilteredCandidateList(List<CandidateModel> filteredCandidateList){
        this.list =filteredCandidateList;
        notifyDataSetChanged ();
    }

    public MyVotingResultAdapterAdmin(Context context, List<CandidateModel> list) {
        this.context = context;
        this.list = list;
        firebaseFirestore =FirebaseFirestore.getInstance ();
    }

    @NonNull
    @Override
    public MyCandidateViewHolderR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.list_item_single_result_admin, parent, false);
        return new MyCandidateViewHolderR (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVotingResultAdapterAdmin.MyCandidateViewHolderR holder, @SuppressLint("RecyclerView") int position) {
        holder.candidate_ID.setText ("Id :  "+list.get (position).getCandidateID ());
        holder.candidate_name.setText ("Candidate Name :  "+list.get (position).getName ());
        holder.candidate_department.setText ("Department :  "+list.get (position).getDepartment ());
        holder.candidate_Post.setText ("Position :  "+list.get (position).getPost ());
        holder.candidate_about.setText ("About :  "+list.get (position).getAbout ());

        firebaseFirestore.collection ("VotingCandidate/"+list.get (position).getId ()+"/Vote")
                .addSnapshotListener (new EventListener<QuerySnapshot> () {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if (!documentSnapshots.isEmpty ()){
                            int count = documentSnapshots.size ();
                            CandidateModel candidateModel1 = list.get (position);
                            candidateModel1.setCount (count);
                            list.set (position,candidateModel1);
                            notifyDataSetChanged ();
                        }
                    }
                });
        holder.deleteBtn.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder (context)
                        .setTitle ("Delete Candidate Record")
                        .setMessage ("Are you sure you want to delete?")
                        .setIcon (R.drawable.ic_baseline_delete_24)
                        .setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseFirestore.collection ("VotingCandidate").document (list.get (position).getId ())
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

        holder.result.setText ("Result :"+list.get (position).getCount ());

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public static class MyCandidateViewHolderR extends RecyclerView.ViewHolder{
        private TextView candidate_name, candidate_ID, candidate_department, candidate_Post, candidate_about, result;
        private Button deleteBtn;
        private CardView card;

        public MyCandidateViewHolderR(@NonNull View itemView) {
            super (itemView);
            candidate_ID = itemView.findViewById (R.id.textView2);
            candidate_name = itemView.findViewById (R.id.textView3);
            candidate_department = itemView.findViewById (R.id.textView4);
            candidate_Post = itemView.findViewById (R.id.textView5);
            candidate_about = itemView.findViewById (R.id.textView6);
            result = itemView.findViewById (R.id.candidateResult4);
            deleteBtn = itemView.findViewById (R.id.deleteS);
            card = itemView.findViewById (R.id.cardds);


        }
    }

}
