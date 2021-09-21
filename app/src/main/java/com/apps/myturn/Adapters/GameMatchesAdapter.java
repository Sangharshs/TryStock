package com.apps.myturn.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.JoinMatchActivity;
import com.apps.myturn.MatchDetailsActivity;
import com.apps.myturn.Models.MatchModel;
import com.apps.myturn.R;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;
import java.util.List;

public class GameMatchesAdapter extends RecyclerView.Adapter<GameMatchesAdapter.MatchViewHolder> {
    List<MatchModel> matches_list = new ArrayList<>();
    int stock_Limit;
    String cid;

    public GameMatchesAdapter(List<MatchModel> matches_list,int stock_Limit,String cid) {
        this.matches_list = matches_list;
        this.stock_Limit = stock_Limit;
        this.cid = cid;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_matches_item
        ,parent,false);
        return new MatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        MatchModel matchModel = matches_list.get(position);

        holder.joined_users.setText(matchModel.getJoined_users());
        holder.entry_fee.setText(matchModel.getEntry_fee());
        holder.prize_pool.setText(matchModel.getPrize_pool());
        holder.time_remaining.setText(matchModel.getDate());
        holder.match_name.setText(matchModel.getMatch_title());
        holder.total_users.setText(" / "+matchModel.getTotal_users());

        try {
            int total_u = Integer.parseInt(holder.total_users.getText().toString());
            int joined_u = Integer.parseInt(holder.joined_users.getText().toString());
            if(total_u == joined_u){
                holder.joinMatchButton.setVisibility(View.INVISIBLE);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }



        holder.horizontalProgressBar.setProgress(Integer.parseInt(matchModel.getJoined_users()));

        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.joinMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), JoinMatchActivity.class);
                intent.putExtra("match_name",matchModel.getMatch_title());
                intent.putExtra("match_title",matchModel.getMatch_title());
                intent.putExtra("match_subtitle",matchModel.getMatch_subtitle());
                intent.putExtra("prize_pool",matchModel.getPrize_pool());
                intent.putExtra("time_remaining",matchModel.getDate());
                intent.putExtra("total_users",matchModel.getTotal_users());
                intent.putExtra("joined_users",matchModel.getJoined_users());
                intent.putExtra("entry_fee",matchModel.getEntry_fee());
                intent.putExtra("stock_limit",stock_Limit);
                intent.putExtra("id",matchModel.getId());
                intent.putExtra("cid",cid);
                v.getContext().startActivity(intent);
            }
        });
        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MatchDetailsActivity.class);
                intent.putExtra("id",matchModel.getId());
                intent.putExtra("prize_pool",matches_list.get(position).getPrize_pool());
                intent.putExtra("date",matches_list.get(position).getDate());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matches_list.size();
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView match_name,time_remaining,prize_pool,entry_fee,joined_users,total_users;
        RoundedHorizontalProgressBar horizontalProgressBar;
        Button detailsButton,joinMatchButton;
        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            horizontalProgressBar = itemView.findViewById(R.id.progress_bar_1_horizontal);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            joinMatchButton = itemView.findViewById(R.id.joinMatchButton);
            match_name = itemView.findViewById(R.id.match_name_TextView);
            time_remaining = itemView.findViewById(R.id.match_time_Textview);
            prize_pool = itemView.findViewById(R.id.prizePool);
            entry_fee = itemView.findViewById(R.id.entryFee);
            joined_users = itemView.findViewById(R.id.joined_user);
            total_users  = itemView.findViewById(R.id.total_user);
        }
    }
}
