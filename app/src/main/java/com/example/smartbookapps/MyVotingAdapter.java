package com.example.smartbookapps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyVotingAdapter extends RecyclerView.Adapter<MyVotingAdapter.MyCandidateViewHolder> {
     Context context;
     List<CandidateModel> list;

    public MyVotingAdapter(Context context, List<CandidateModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyCandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.list_item_single, parent, false);
        return new MyCandidateViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCandidateViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.candidate_ID.setText ("Id :  "+list.get (position).getCandidateID ());
        holder.candidate_name.setText ("Candidate Name :  "+list.get (position).getName ());
        holder.candidate_department.setText ("Department : "+list.get (position).getDepartment ());
        holder.candidate_Post.setText ("Position :  "+list.get (position).getPost ());
        holder.candidate_about.setText ("About candidate :  "+list.get (position).getAbout ());

        holder.card.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (context, Voting_Activity.class);
                intent.putExtra ("candidateID", list.get(position).getCandidateID ());
                intent.putExtra ("Name", list.get(position).getName ());
                intent.putExtra ("department", list.get(position).getDepartment ());
                intent.putExtra ("post", list.get(position).getPost ());
                intent.putExtra ("about", list.get(position).getAbout ());
                intent.putExtra ("id", list.get(position).getId ());

                context.startActivity (intent);
//                Activity activity = (Activity) context;
//                activity.finish ();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public static class MyCandidateViewHolder extends RecyclerView.ViewHolder{
        private TextView candidate_name, candidate_ID, candidate_department, candidate_Post, candidate_about;
        private CardView card;
//        private Button VoteBtn;
        public MyCandidateViewHolder(@NonNull View itemView) {
            super (itemView);
            candidate_ID = itemView.findViewById (R.id.textView2);
            candidate_name = itemView.findViewById (R.id.textView3);
            candidate_department = itemView.findViewById (R.id.textView4);
            candidate_Post = itemView.findViewById (R.id.textView5);
            candidate_about = itemView.findViewById (R.id.textView6);
            card =itemView.findViewById (R.id.card);

        }
    }

}
