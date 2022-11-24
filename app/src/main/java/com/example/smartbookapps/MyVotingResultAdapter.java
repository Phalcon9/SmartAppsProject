package com.example.smartbookapps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class MyVotingResultAdapter extends RecyclerView.Adapter<MyVotingResultAdapter.MyCandidateViewHolder> {
     Context context;
     List<CandidateModel> list;
     private FirebaseFirestore firebaseFirestore;


    public void setFilteredCandidateListS(List<CandidateModel> filteredCandidateListS){
        this.list =filteredCandidateListS;
        notifyDataSetChanged ();
    }
    public MyVotingResultAdapter(Context context, List<CandidateModel> list) {
        this.context = context;
        this.list = list;
        firebaseFirestore =FirebaseFirestore.getInstance ();
    }

    @NonNull
    @Override
    public MyCandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.list_item_single_result, parent, false);
        return new MyCandidateViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCandidateViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.candidate_ID.setText ("Id :  "+list.get (position).getCandidateID ());
        holder.candidate_name.setText ("Candidate Name :  "+list.get (position).getName ());
        holder.candidate_department.setText ("Department :  "+list.get (position).getDepartment ());
        holder.candidate_Post.setText ("Position :  "+list.get (position).getPost ());
        holder.candidate_about.setText ("Id :  "+list.get (position).getAbout ());

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
        holder.result.setText ("Result :"+list.get (position).getCount ());


    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public static class MyCandidateViewHolder extends RecyclerView.ViewHolder{
        private TextView candidate_name, candidate_ID, candidate_department, candidate_Post, candidate_about, result;

//        private Button VoteBtn;
        public MyCandidateViewHolder(@NonNull View itemView) {
            super (itemView);
            candidate_ID = itemView.findViewById (R.id.textView2);
            candidate_name = itemView.findViewById (R.id.textView3);
            candidate_department = itemView.findViewById (R.id.textView4);
            candidate_Post = itemView.findViewById (R.id.textView5);
            candidate_about = itemView.findViewById (R.id.textView6);
            result = itemView.findViewById (R.id.candidateResult);


        }
    }

}
