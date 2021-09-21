package com.apps.myturn.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.MatchDetailsActivity;
import com.apps.myturn.Models.JoinedMatchModel;
import com.apps.myturn.R;
import com.apps.myturn.ResultActivity;
import com.apps.myturn.UpdateTeamActivity;

import java.util.List;

public class CompleteMatchesAdapter extends RecyclerView.Adapter<CompleteMatchesAdapter.viewHolder>{

    List<JoinedMatchModel> joinedMatchModels;

    public  CompleteMatchesAdapter(List<JoinedMatchModel> joinedMatchModels) {
        this.joinedMatchModels = joinedMatchModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_matches_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.match_name.setText(joinedMatchModels.get(position).match_name);
        holder.participated.setText(joinedMatchModels.get(position).joined_user);
        holder.entry_fee.setText(joinedMatchModels.get(position).entry_fee);
        holder.date.setText(joinedMatchModels.get(position).date);
        holder.prize_pool.setText(joinedMatchModels.get(position).prize_pool);
        holder.edit_joinedButton.setText("Result");

        holder.edit_joinedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ResultActivity.class);
                intent.putExtra("STOCK_DATA",joinedMatchModels.get(position).stock_data);
                intent.putExtra("match_id",joinedMatchModels.get(position).match_id);
                intent.putExtra("user_id",joinedMatchModels.get(position).user_id);
                intent.putExtra("id",joinedMatchModels.get(position).id);
                intent.putExtra("match_name",joinedMatchModels.get(position).match_name);
                intent.putExtra("date",joinedMatchModels.get(position).date);
                intent.putExtra("prize_pool",joinedMatchModels.get(position).prize_pool);
                Log.e("MATCH_ID",joinedMatchModels.get(position).match_id);
                Log.e("STOCK_DATA",joinedMatchModels.get(position).stock_data);
                view.getContext().startActivity(intent);
            }
        });
        holder.detailsJoinedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MatchDetailsActivity.class);
                intent.putExtra("id",joinedMatchModels.get(position).match_id);
                intent.putExtra("prize_pool",joinedMatchModels.get(position).prize_pool);
                intent.putExtra("date",joinedMatchModels.get(position).date);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return joinedMatchModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView date,prize_pool,entry_fee,participated,match_name;
        Button edit_joinedButton,detailsJoinedButton;
        @SuppressLint("SetTextI18n")
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateText);
            prize_pool = itemView.findViewById(R.id.prizePOOLAMOUT);
            entry_fee = itemView.findViewById(R.id.entryFEETEXT);
            participated = itemView.findViewById(R.id.participatedNUMBEr);
            match_name = itemView.findViewById(R.id.matchNAMETEXT);
            edit_joinedButton = itemView.findViewById(R.id.editButtonJ);
            detailsJoinedButton = itemView.findViewById(R.id.detailsButtonJ);

            edit_joinedButton.setText("Result");


        }
    }
}
