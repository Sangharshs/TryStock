package com.apps.myturn.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.GameMatchesActivity;
import com.apps.myturn.Models.GameModel;
import com.apps.myturn.R;

import java.util.ArrayList;
import java.util.List;

public class Game_List_Adapter extends RecyclerView.Adapter<Game_List_Adapter.GameViewHolder> {

    List<GameModel> game_model_list = new ArrayList<>();

    public Game_List_Adapter(List<GameModel> game_model_list) {
        this.game_model_list = game_model_list;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item,parent,false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
           Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),
                   android.R.anim.fade_in);

            GameModel gameModel = game_model_list.get(position);

            holder.gameType.setText(gameModel.getGame_tag());
            holder.gameTime.setText(gameModel.getGame_date_time());
            holder.gameSubTitle.setText(gameModel.getGame_subtitle());
            holder.gameTitle.setText(gameModel.getGame_title());

           String stock_limit = gameModel.getGame_title().replaceAll("[^0-9]", "");
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     Intent intent = new Intent(v.getContext(), GameMatchesActivity.class);
                     intent.putExtra("cid",gameModel.getCategory_id());
                     intent.putExtra("stock_limit",stock_limit);
                     v.getContext().startActivity(intent);
                }
            });

            if(position ==0){
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardbg_0));
            }else if(position ==1){
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardbg_2));
            }else if(position ==2){
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardbg_1));
            }

        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return game_model_list.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        Button playButton;
        TextView gameTitle,gameSubTitle,gameTime,gameType;
        CardView cardView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            playButton = itemView.findViewById(R.id.Playbutton);
            gameTitle = itemView.findViewById(R.id.game_name);
            gameSubTitle = itemView.findViewById(R.id.gamesubtitle);
            gameTime   = itemView.findViewById(R.id.date_time);
            gameType   = itemView.findViewById(R.id.gameType);
            cardView   = itemView.findViewById(R.id.cardVIEW);
        }
    }
}
