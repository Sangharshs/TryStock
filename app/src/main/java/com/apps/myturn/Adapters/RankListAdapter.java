package com.apps.myturn.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.Models.MatchDetailsModel;
import com.apps.myturn.R;

import java.util.List;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.rankListViewHolder> {
    List<MatchDetailsModel> matchDetailsModelList;

    public RankListAdapter(List<MatchDetailsModel> matchDetailsModelList) {
        this.matchDetailsModelList = matchDetailsModelList;
    }

    @NonNull
    @Override
    public rankListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_detail_item,parent,false);
        return new rankListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull rankListViewHolder holder, int position) {
        holder.winning_amount_text.setText(matchDetailsModelList.get(position).getWining_amount());
        holder.rankText.setText(matchDetailsModelList.get(position).getRank_name());
    }

    @Override
    public int getItemCount() {
        return matchDetailsModelList.size();
    }

    public class rankListViewHolder extends RecyclerView.ViewHolder {
        TextView rankText,winning_amount_text;
        public rankListViewHolder(@NonNull View itemView) {
            super(itemView);
            rankText = itemView.findViewById(R.id.rankTExt);
            winning_amount_text = itemView.findViewById(R.id.winning_amount_text);
        }
    }
}
