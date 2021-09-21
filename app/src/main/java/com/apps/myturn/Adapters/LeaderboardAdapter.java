package com.apps.myturn.Adapters;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.Models.Result;
import com.apps.myturn.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.viewholder> {
    List<Result> leader_board_list;


    public LeaderboardAdapter(List<Result> leader_board_list) {
        this.leader_board_list = leader_board_list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_board_item,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        SharedPreferences preferences = holder.itemView.getContext().getSharedPreferences("USER",MODE_PRIVATE);

       String user_name =       preferences.getString("user_name","");

       holder.textView_Rank.setText(leader_board_list.get(position).getRank());
       holder.textView_userName.setText(leader_board_list.get(position).user_name);
       holder.textView_Points.setText(leader_board_list.get(position).points);
    }

    @Override
    public int getItemCount() {
        return leader_board_list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView textView_userName,textView_Points,textView_Rank;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView_Points = itemView.findViewById(R.id.textView_points);
            textView_userName = itemView.findViewById(R.id.textView_user_name);
            textView_Rank = itemView.findViewById(R.id.textView_rank);
        }
    }
}
